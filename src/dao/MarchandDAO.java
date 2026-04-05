package dao;

import database.DatabaseConnection;
import model.Marchand;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MarchandDAO {

    public boolean ajouterMarchand(Marchand m) {
        String sql = "INSERT INTO Marchand (nom_commerce, code_marchand, compte_id) VALUES (?, ?, ?)";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            
            pstmt.setString(1, m.getNomCommerce());
            pstmt.setString(2, m.getCodeMarchand());
            pstmt.setInt(3, m.getCompteId());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erreur ajout marchand : " + e.getMessage());
            return false;
        }
    }

    public Marchand trouverParCode(String code) {
        String sql = "SELECT * FROM Marchand WHERE code_marchand = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            
            pstmt.setString(1, code);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Marchand(rs.getInt("id"), rs.getString("nom_commerce"), rs.getString("code_marchand"), rs.getInt("compte_id"));
            }
        } catch (SQLException e) {
            System.err.println("Erreur recherche marchand : " + e.getMessage());
        }
        return null;
    }
}