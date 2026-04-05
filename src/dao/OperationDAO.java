package dao;

import database.DatabaseConnection;
import model.Operation;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class OperationDAO {

    public boolean enregistrerOperation(Operation op) {
        String sql = "INSERT INTO Operation (type_op, montant, date_op, compte_id, compte_dest_id) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            
            pstmt.setString(1, op.getTypeOp());
            pstmt.setDouble(2, op.getMontant());
            pstmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
            pstmt.setInt(4, op.getCompteId());
            
            if (op.getCompteDestId() != null) {
                pstmt.setInt(5, op.getCompteDestId());
            } else {
                pstmt.setNull(5, java.sql.Types.INTEGER);
            }
            
            return pstmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Erreur enregistrement operation : " + e.getMessage());
            return false;
        }
    }

    public List<Operation> listerHistorique(int compteId) {
        List<Operation> historique = new ArrayList<>();
        String sql = "SELECT * FROM Operation WHERE compte_id = ? OR compte_dest_id = ? ORDER BY date_op DESC";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            
            pstmt.setInt(1, compteId);
            pstmt.setInt(2, compteId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Operation op = new Operation();
                op.setId(rs.getInt("id"));
                op.setTypeOp(rs.getString("type_op"));
                op.setMontant(rs.getDouble("montant"));
                op.setDateOp(rs.getTimestamp("date_op"));
                op.setCompteId(rs.getInt("compte_id"));
                op.setCompteDestId(rs.getInt("compte_dest_id"));
                historique.add(op);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lecture historique : " + e.getMessage());
        }
        return historique;
    }
}