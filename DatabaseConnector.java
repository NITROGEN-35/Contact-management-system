package ContactManagementSystem;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseConnector {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/contact_management";
    private static final String DB_USER = "root"; // Replace with your MySQL username
    private static final String DB_PASSWORD = "NITROGEN35"; // Replace with your MySQL password

    // Get database connection
    public static Connection connect() {
        try {
            return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (SQLException e) {
            System.out.println("Connection failed: " + e.getMessage());
            return null;
        }
    }

    // Create the contacts table if it does not exist
    public static void initializeDatabase() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS contacts (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "name VARCHAR(100) NOT NULL, " +
                "phone VARCHAR(20) NOT NULL, " +
                "email VARCHAR(100) NOT NULL)";
        try (Connection conn = connect()) {
            assert conn != null;
            try (PreparedStatement stmt = conn.prepareStatement(createTableSQL)) {
                stmt.execute();
            }
        } catch (SQLException e) {
            System.out.println("Error initializing database: " + e.getMessage());
        }
    }

    // Search for a contact by name or phone
    public static String searchContactInDatabase(String query) {
        String searchQuery = "SELECT * FROM contacts WHERE name LIKE ? OR phone LIKE ?";
        try (Connection conn = connect()) {
            assert conn != null;
            try (PreparedStatement stmt = conn.prepareStatement(searchQuery)) {
                stmt.setString(1, "%" + query + "%"); // Search by name
                stmt.setString(2, "%" + query + "%"); // Search by phone number

                ResultSet resultSet = stmt.executeQuery();

                if (resultSet.next()) {
                    // Construct the result string with contact details
                    String result = "ID: " + resultSet.getInt("id") + "\n" +
                            "Name: " + resultSet.getString("name") + "\n" +
                            "Phone: " + resultSet.getString("phone") + "\n" +
                            "Email: " + resultSet.getString("email");
                    return result;
                } else {
                    return "No contact found.";
                }
            }
        } catch (SQLException e) {
            System.out.println("Error searching database: " + e.getMessage());
            return "Error searching database.";
 }
}
}