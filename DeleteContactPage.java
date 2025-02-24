package ContactManagementSystem;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class DeleteContactPage {
    public DeleteContactPage() {
        JFrame frame = new JFrame("Delete Contact");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 200);

        // Layout and components
        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel nameLabel = new JLabel("Name to Delete:");
        JTextField nameField = new JTextField();
        JButton deleteButton = new JButton("Delete");

        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(new JLabel()); // Empty placeholder
        panel.add(deleteButton);

        frame.add(panel);

        // Delete button action listener
        deleteButton.addActionListener(e -> {
            String name = nameField.getText().trim();

            if (!name.isEmpty()) {
                boolean deleted = deleteContactFromDatabase(name);
                if (deleted) {
                    JOptionPane.showMessageDialog(frame, "Contact Deleted Successfully!");
                } else {
                    JOptionPane.showMessageDialog(frame, "Error: Contact Not Found or Could Not Be Deleted!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Please enter a contact name to delete.", "Error", JOptionPane.WARNING_MESSAGE);
            }
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private boolean deleteContactFromDatabase(String name) {
        String deleteSQL = "DELETE FROM contacts WHERE name = ?";

        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement stmt = conn.prepareStatement(deleteSQL)) {

            stmt.setString(1, name);
            int affectedRows = stmt.executeUpdate(); // Execute deletion

            return affectedRows > 0; // Returns true if a row was deleted

        } catch (Exception e) {
            System.out.println("Error deleting contact: " + e.getMessage());
            return false;
        }
    }
}

