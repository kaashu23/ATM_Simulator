package atm_simulator;

import java.sql.*;

public class DBConnection {

    private static final String url = "jdbc:mysql://localhost:3306/ATM";
    private static final String user = "root";
    private static final String pass = "123456";

    static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, user, pass);
            createTables(conn);
            return conn;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void createTables(Connection conn) {
        try (Statement stmt = conn.createStatement()) {
            String usersTable = "CREATE TABLE IF NOT EXISTS users ("
                    + "id INT AUTO_INCREMENT PRIMARY KEY, "
                    + "name VARCHAR(100), "
                    + "card_number VARCHAR(20), "
                    + "pin VARCHAR(10), "
                    + "mobile VARCHAR(15), " 
                    + "balance DECIMAL(10,2)"
                    + ")";  
            stmt.executeUpdate(usersTable);

            String transactionsTable = "CREATE TABLE IF NOT EXISTS transactions ("
                    + "id INT AUTO_INCREMENT PRIMARY KEY, "
                    + "card_number VARCHAR(20), "
                    + "type VARCHAR(20), "
                    + "amount DOUBLE, "
                    + "timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP"
                    + ")";
            stmt.executeUpdate(transactionsTable);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
