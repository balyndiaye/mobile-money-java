package dao;

import database.DatabaseConnection;
import model.Operation;
import java.sql.*;
import java.util.List;

public class OperationDAO {

    // Méthode pour trouver l'ID interne à partir du numéro (ex: "SN001" -> 1)
    private int recupererIdCompte(String numero, Connection con) throws SQLException {
        String sql = "SELECT id FROM Compte WHERE numero = ?"; 
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, numero);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) return rs.getInt("id");
            }
        }
        return -1;
    }

    public boolean enregistrerOperation(Operation op) {
        // id, type_op, montant, date_op, compte_id, compte_dest_id
        String sql = "INSERT INTO Operation (type_op, montant, date_op, compte_id, compte_dest_id) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection con = DatabaseConnection.getConnection()) {
            
            
            int idSource = recupererIdCompte(op.getCompteSource(), con);
            
            try (PreparedStatement pstmt = con.prepareStatement(sql)) {
                pstmt.setString(1, op.getType());
                pstmt.setDouble(2, op.getMontant());
                pstmt.setTimestamp(3, op.getDateOp());
                pstmt.setInt(4, idSource);
                
                // Si c'est un transfert, on cherche l'ID destination
                if (op.getCompteDest() != null && !op.getCompteDest().isEmpty()) {
                    int idDest = recupererIdCompte(op.getCompteDest(), con);
                    pstmt.setInt(5, idDest);
                } else {
                   
                    pstmt.setNull(5, Types.INTEGER);
                }
                
                return pstmt.executeUpdate() > 0;
            }
            
        } catch (SQLException e) {
            System.err.println("Erreur SQL : " + e.getMessage());
            return false;
        }
    }

    public List<Operation> findByCompteId(int compteId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByCompteId'");
    }
}