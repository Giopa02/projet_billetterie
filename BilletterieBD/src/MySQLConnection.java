import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnection {
    public static Connection connect() throws SQLException {
        try {
            var jdbcUrl = DatabaseConfig.getDbUrl();
            var user = DatabaseConfig.getDbUsername();
            var password = DatabaseConfig.getDbPassword();
            
            // Uncomment if code above doesn't work
            // var jdbcUrl = "jdbc:mysql://localhost:8889/billetterie_db";
            // var user = "root";
            // var password = "root";

            return DriverManager.getConnection(jdbcUrl, user, password);

        } catch (SQLException e) {
            System.err.println("Erreur de connexion : " + e.getMessage());
            throw e;
        }
    }
}