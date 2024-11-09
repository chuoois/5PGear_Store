package dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBContext {

    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=SWP391G5";
    private static final String USER = "sa";
    private static final String PASS = "123";
    private static final Logger logger = Logger.getLogger(DBContext.class.getName());

    public static Connection getConnection() {
        Connection connection = null;
        String url = DBContext.URL;
        String user = DBContext.USER;
        String pass = DBContext.PASS;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(url, user, pass);
            logger.log(Level.INFO, "Connect success!!!");
        } catch (ClassNotFoundException e) {
            logger.log(Level.SEVERE, "Database driver not found: {0}", e.getMessage());
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Connection failed: {0}", e.getMessage());
        }

        return connection;
    }

    public static void main(String[] args) {
        Connection connection = DBContext.getConnection();
        if (connection != null) {
            logger.log(Level.INFO, "Connection successful!");
            try {
                connection.close();
                logger.log(Level.INFO, "Connection closed.");
            } catch (SQLException ex) {
                logger.log(Level.SEVERE, "Failed to close connection: {0}", ex.getMessage());
            }
        } else {
            logger.log(Level.WARNING, "Failed to connect to the database.");
        }
    }
}