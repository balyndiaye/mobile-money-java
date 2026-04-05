package dao;

import database.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CompteDAO {

    public boolean creerCompte(int clientId, String numeroCompte) {
        String sql = "INSERT INTO Compte (numero_compte, solde, client_id) VALUES (?, 0, ?)";
        
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            
            pstmt.setString(1, numeroCompte);
            pstmt.setInt(2, clientId);
            
            int lignesAffectees = pstmt.executeUpdate();
            return lignesAffectees > 0;
            
        } catch (SQLException e) {
            System.err.println("Erreur creation compte : " + e.getMessage());
            return false;
        }
    }

    public double consulterSolde(int clientId) {
        String sql = "SELECT solde FROM Compte WHERE client_id = ?";
        
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            
            pstmt.setInt(1, clientId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getDouble("solde");
            }
        } catch (SQLException e) {
            System.err.println("Erreur lecture solde : " + e.getMessage());
        }
        return -1.0;
    }

    public boolean modifierSolde(int compteId, double nouveauSolde) {
        String sql = "UPDATE Compte SET solde = ? WHERE id = ?";
        
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            
            pstmt.setDouble(1, nouveauSolde);
            pstmt.setInt(2, compteId);
            
            return pstmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Erreur mise a jour solde : " + e.getMessage());
            return false;
        }
    }
}