package dao;

import database.DatabaseConnection;
import model.Compte;
import model.Client;
import java.sql.*;

public class CompteDAO {

   
    // On utilise l'objet Compte complet en paramètre
    public boolean save(Compte compte) {
        String sql = "INSERT INTO Compte (numero, solde, client_id) VALUES (?, ?, ?)";
        
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            
            pstmt.setString(1, compte.getNumero());
            pstmt.setDouble(2, compte.getSolde());
            pstmt.setInt(3, compte.getClient().getId());
            
            return pstmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Erreur sauvegarde compte : " + e.getMessage());
            return false;
        }
    }

    // Synchronisé avec CompteService.consulterCompte et OperationService
   
    public Compte findByNumero(String numeroCompte) {
        String sql = "SELECT c.*, cl.nom, cl.prenom, cl.telephone, cl.adresse " +
                     "FROM Compte c " +
                     "JOIN Client cl ON c.client_id = cl.id " +
                     "WHERE c.numero = ?";
        
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            
            pstmt.setString(1, numeroCompte);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    // On recrée l'objet Client d'abord
                    Client client = new Client(
                        rs.getInt("client_id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("telephone"),
                        rs.getString("adresse")
                    );
                    
                    // Puis on crée l'objet Compte avec son Client
                    Compte compte = new Compte();
                    compte.setNumero(rs.getString("numero"));
                    compte.setSolde(rs.getDouble("solde"));
                    compte.setClient(client);
                    return compte;
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur findByNumero : " + e.getMessage());
        }
        return null;
    }

    // Synchronisé avec OperationService (Depot, Retrait, Transfert)
    public boolean mettreAJourSolde(String numeroCompte, double nouveauSolde) {
        String sql = "UPDATE Compte SET solde = ? WHERE numero = ?";
        
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            
            pstmt.setDouble(1, nouveauSolde);
            pstmt.setString(2, numeroCompte);
            
            return pstmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Erreur mise a jour solde : " + e.getMessage());
            return false;
        }
    }
}