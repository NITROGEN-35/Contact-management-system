package ContactManagementSystem;

import javax.swing.*;
import java.awt.*;

import static ContactManagementSystem.DatabaseConnector.initializeDatabase;

public class ContactManagementHomePage {
    public static void main(String[] args) {
        // Initialize database
        initializeDatabase();

        // Create the main frame
        JFrame frame = new JFrame("CONTACT MANAGEMENT");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(false);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

        // Set 8-bit style background color
        frame.getContentPane().setBackground(new Color(24, 20, 37)); // Darker pixelated look

        // Load a pixelated font
        Font pixelFont = new Font("Monospaced", Font.BOLD, 30); // Font size as before

        // Title panel with search icon
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5)); // Reduced vertical gap (5)
        titlePanel.setBackground(new Color(24, 20, 37));

        JLabel titleLabel = new JLabel("CONTACT MANAGEMENT");
        titleLabel.setFont(pixelFont);
        titleLabel.setForeground(Color.WHITE);

        // Search icon button
        JButton searchIconButton = createPixelButton("🔍");
        searchIconButton.setPreferredSize(new Dimension(50, 30));

        titlePanel.add(titleLabel);
        titlePanel.add(searchIconButton);

        // Set the preferred size of the titlePanel to reduce its height
        titlePanel.setPreferredSize(new Dimension(600, 50)); // Set the height to a smaller value

        frame.add(titlePanel, BorderLayout.NORTH);

        // Create a pixel-themed panel
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(4, 1, 10, 10));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(500, 100, 50, 100));
        centerPanel.setBackground(new Color(46, 34, 59)); // Dark purple

        // Create pixel-style buttons
        JButton addContactButton = createPixelButton("ADD CONTACT");
        JButton viewContactsButton = createPixelButton("VIEW CONTACTS");
        JButton deleteContactButton = createPixelButton("DELETE CONTACT");
        JButton exitButton = createPixelButton("EXIT");

        // Add buttons to panel
        centerPanel.add(addContactButton);
        centerPanel.add(viewContactsButton);
        centerPanel.add(deleteContactButton);
        centerPanel.add(exitButton);
        frame.add(centerPanel, BorderLayout.SOUTH);

        // Action Listeners
        addContactButton.addActionListener(e -> new AddContactPage());
        viewContactsButton.addActionListener(e -> new ViewContactsPage());
        deleteContactButton.addActionListener(e -> new DeleteContactPage());
        exitButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(frame, "Are you sure you want to exit?", "Exit Confirmation", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                frame.dispose();
            }
        });

        // Action listener for search icon to open search window
        searchIconButton.addActionListener(e -> openSearchWindow(frame));

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // Function to create pixelated buttons
    private static JButton createPixelButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Monospaced", Font.BOLD, 16)); // Pixel-like font
        button.setForeground(Color.BLACK);
        button.setBackground(new Color(214, 210, 196)); // Retro beige
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3)); // Thick border for pixel look
        button.setFocusPainted(false);
        return button;
    }

    // Opens a new window for search functionality
    private static void openSearchWindow(JFrame parentFrame) {
        // Create the search window
        JFrame searchFrame = new JFrame("Search Contact");
        searchFrame.setSize(400, 300);
        searchFrame.setLayout(new BorderLayout());
        searchFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Set pixelated theme for the search window
        searchFrame.getContentPane().setBackground(new Color(24, 20, 37)); // Darker pixelated look

        // Create a search panel
        JPanel searchPanel = new JPanel(new FlowLayout());
        searchPanel.setBackground(new Color(24, 20, 37));

        // Create search field and button
        JTextField searchField = new JTextField(15);
        searchField.setFont(new Font("Monospaced", Font.BOLD, 14));
        searchField.setForeground(Color.BLACK);
        searchField.setBackground(new Color(214, 210, 196)); // Retro beige
        searchField.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3)); // Thick pixel border

        JButton searchButton = createPixelButton("SEARCH");
        searchButton.addActionListener(e -> searchContact(searchField.getText(), searchFrame));

        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        // Add the search panel to the search window
        searchFrame.add(searchPanel, BorderLayout.CENTER);

        // Show the search window
        searchFrame.setLocationRelativeTo(parentFrame);
        searchFrame.setVisible(true);
    }

    // Search function to display results in a new window
    private static void searchContact(String query, JFrame parentFrame) {
        if (query.trim().isEmpty()) {
            JOptionPane.showMessageDialog(parentFrame, "Please enter a name or number.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Get search result from the database
        String result = DatabaseConnector.searchContactInDatabase(query);

        // Create a new window to display the result
        JFrame resultFrame = new JFrame("Search Results");
        resultFrame.setSize(400, 300);
        resultFrame.setLayout(new BorderLayout());
        resultFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Set 8-bit style background color for the result window
        resultFrame.getContentPane().setBackground(new Color(24, 20, 37)); // Darker pixelated look

        // Display the result in a JTextArea
        JTextArea resultTextArea = new JTextArea();
        resultTextArea.setText(result);
        resultTextArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        resultTextArea.setForeground(Color.WHITE);
        resultTextArea.setBackground(new Color(46, 34, 59)); // Dark purple background
        resultTextArea.setEditable(false);

        // Add the result to a scroll pane
        JScrollPane scrollPane = new JScrollPane(resultTextArea);
        resultFrame.add(scrollPane, BorderLayout.CENTER);

        // Show the result frame
        resultFrame.setLocationRelativeTo(parentFrame);
        resultFrame.setVisible(true);
}
}
