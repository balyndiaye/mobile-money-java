package dao;

import database.DatabaseConnection;
import model.Operation;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OperationDAO {

    /**
     * Récupère l'ID numérique d'un compte à partir de son numéro (ex: MM-1741).
     */
    private int recupererIdCompte(String numero, Connection con) throws SQLException {
        String sql = "SELECT id FROM compte WHERE numero = ?"; 
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, numero);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id"); 
                }
            }
        }
        return -1; 
    }

    /**
     * Enregistre une opération (Dépôt, Retrait, Transfert) en base.
     */
    public boolean enregistrerOperation(Operation op) {
        String sql = "INSERT INTO operation (type_op, montant, date_op, compte_id, compte_dest_id) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection con = DatabaseConnection.getConnection()) {
            int idSource = recupererIdCompte(op.getCompteSource(), con);
            
            try (PreparedStatement pstmt = con.prepareStatement(sql)) {
                pstmt.setString(1, op.getType());
                pstmt.setDouble(2, op.getMontant());
                pstmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
                pstmt.setInt(4, idSource);
                
                if (op.getCompteDest() != null && !op.getCompteDest().isEmpty()) {
                    int idDest = recupererIdCompte(op.getCompteDest(), con);
                    pstmt.setInt(5, idDest);
                } else {
                    pstmt.setNull(5, Types.INTEGER);
                }
                
                int rows = pstmt.executeUpdate();
                return rows > 0;
            }
        } catch (SQLException e) {
            System.err.println("Erreur SQL lors de l'enregistrement : " + e.getMessage());
            return false;
        }
    }

    /**
     * Liste toutes les opérations pour un compte spécifique (Historique simple).
     */
    public List<Operation> findByCompteId(int compteId) {
        List<Operation> operations = new ArrayList<>();
        String sql = "SELECT * FROM operation WHERE compte_id = ? ORDER BY date_op DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, compteId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Operation op = new Operation();
                op.setId(rs.getInt("id"));
                op.setType(rs.getString("type_op")); 
                op.setMontant(rs.getDouble("montant"));
                op.setDateOp(rs.getTimestamp("date_op")); 
                
                int destId = rs.getInt("compte_dest_id"); 
                if (!rs.wasNull()) {
                    op.setCompteDest(String.valueOf(destId));
                }
                
                operations.add(op);
            }
        } catch (SQLException e) {
            System.err.println("Erreur SQL Historique : " + e.getMessage());
        }
        return operations;
    }

    /**
     * BONUS : Recherche des opérations entre deux dates.
     * Format attendu pour les dates : "YYYY-MM-DD"
     */
    public List<Operation> findBetweenDates(int compteId, String dateDebut, String dateFin) {
        List<Operation> operations = new ArrayList<>();
        String sql = "SELECT * FROM operation WHERE compte_id = ? " +
                     "AND date_op >= ? AND date_op <= ? " +
                     "ORDER BY date_op DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, compteId);
            pstmt.setString(2, dateDebut + " 00:00:00");
            pstmt.setString(3, dateFin + " 23:59:59");
            
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Operation op = new Operation();
                op.setId(rs.getInt("id"));
                op.setType(rs.getString("type_op"));
                op.setMontant(rs.getDouble("montant"));
                op.setDateOp(rs.getTimestamp("date_op"));
                
                operations.add(op);
            }
        } catch (SQLException e) {
            System.err.println("Erreur SQL Recherche Date : " + e.getMessage());
        }
        return operations;
    }

    public Map<String, Double> obtenirSommesParType() {
    Map<String, Double> stats = new HashMap<>();
    // On demande la somme des montants groupée par type (DEPOT, RETRAIT, etc.)
    String sql = "SELECT type_op, SUM(montant) as total FROM operation GROUP BY type_op";
    
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql);
         ResultSet rs = pstmt.executeQuery()) {
        
        while (rs.next()) {
            stats.put(rs.getString("type_op"), rs.getDouble("total"));
        }
    } catch (SQLException e) {
        System.out.println("Erreur stats : " + e.getMessage());
    }
    return stats;
}
}