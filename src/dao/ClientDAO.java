package dao;

import database.DatabaseConnection;
import model.Client;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientDAO {

    
    public boolean save(Client client) {
        String sql = "INSERT INTO Client (nom, prenom, telephone, adresse) VALUES (?, ?, ?, ?)";
        
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            
            pstmt.setString(1, client.getNom());
            pstmt.setString(2, client.getPrenom());
            pstmt.setString(3, client.getTelephone());
            pstmt.setString(4, client.getAdresse());
            
            return pstmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout : " + e.getMessage());
            return false;
        }
    }

    
    public List<Client> findAll() {
        List<Client> clients = new ArrayList<>();
        String sql = "SELECT * FROM Client";

        try (Connection con = DatabaseConnection.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                // Utilisation du constructeur avec ID
                clients.add(new Client(
                    rs.getInt("id"),
                    rs.getString("nom"),
                    rs.getString("prenom"),
                    rs.getString("telephone"),
                    rs.getString("adresse")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Erreur de lecture : " + e.getMessage());
        }
        return clients;
    }

  
    public Client findByNomOrTel(String critere) {
        String sql = "SELECT * FROM Client WHERE nom = ? OR telephone = ? LIMIT 1";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            
            pstmt.setString(1, critere);
            pstmt.setString(2, critere);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Client(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("telephone"),
                        rs.getString("adresse")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur recherche : " + e.getMessage());
        }
        return null; // Retourne null si rien n'est trouvé
    }

    
    public Client findById(int id) {
        String sql = "SELECT * FROM Client WHERE id = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Client(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("telephone"),
                        rs.getString("adresse")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur findById : " + e.getMessage());
        }
        return null;
    }

    //  existeDeja (pour éviter les doublons de téléphone)
    public boolean existeDeja(String tel) {
        String sql = "SELECT COUNT(*) FROM Client WHERE telephone = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            
            pstmt.setString(1, tel);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.err.println("Erreur check existence : " + e.getMessage());
        }
        return false;
    }
}