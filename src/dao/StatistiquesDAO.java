package dao;

import database.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StatistiquesDAO {

    public double calculerVolumeTotal() {
        String sql = "SELECT SUM(montant) as total FROM Operation";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                return rs.getDouble("total");
            }
        } catch (SQLException e) {
            System.err.println("Erreur calcul volume : " + e.getMessage());
        }
        return 0.0;
    }

    public int compterTransactionsDuJour() {
        String sql = "SELECT COUNT(*) as nb FROM Operation WHERE DATE(date_op) = CURDATE()";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("nb");
            }
        } catch (SQLException e) {
            System.err.println("Erreur compte transactions : " + e.getMessage());
        }
        return 0;
    }
}