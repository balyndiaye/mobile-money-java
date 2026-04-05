package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    // Configuration spécifique à ton installation
    private static final String URL = "jdbc:mysql://localhost:3307/mobile_money_db";
    private static final String USER = "root";
    private static final String PASSWORD = ""; 

    private static Connection connection = null;

    /**
     * Méthode pour obtenir la connexion unique à la base de données.
     * C'est l'interrupteur que tes collègues vont appeler.
     */
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                // Chargement du Driver MySQL
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            } catch (ClassNotFoundException e) {
                throw new SQLException("Le driver MySQL est introuvable. Vérifie le dossier lib.", e);
            }
        }
        return connection;
    }

    /**
     * Méthode pour fermer proprement la connexion si besoin.
     */
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}