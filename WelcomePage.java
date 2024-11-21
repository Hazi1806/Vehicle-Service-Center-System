import java.awt.*; 
import javax.swing.*; 

public class WelcomePage {

    public WelcomePage() {
        // Create a pop-up dialog to welcome the user.
        JDialog dialog = new JDialog();
        dialog.setTitle("Welcome"); // Set the title of the dialog
        dialog.setSize(600, 450); // Set the size of the dialog window

        // Set layout and background image
        ImageIcon backgroundImage = new ImageIcon("C:\\Users\\user\\Coding Folder\\JAVA\\Project baru\\SWC3344_PROJECT\\WiseWheel.jpg");
        JLabel backgroundLabel = new JLabel(backgroundImage); 
        backgroundLabel.setLayout(new BorderLayout()); // Use BorderLayout for positioning components

        // Create a label for the welcome message
        JLabel welcomeLabel = new JLabel("Welcome to WiseWheel Service Center!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("serif", Font.BOLD, 25)); // Set font style and size for the welcome message
        backgroundLabel.add(welcomeLabel, BorderLayout.CENTER); // Add the welcome label to the center of the background label
        dialog.add(backgroundLabel, BorderLayout.CENTER); // Add the background label to the dialog

        // Create and add the "Welcome" button
        JButton welcomeButton = new JButton("Welcome"); // Create a button labeled "Welcome"
        welcomeButton.setPreferredSize(new Dimension(200, 30)); // Set preferred size for the button
        welcomeButton.addActionListener(e -> {
            dialog.dispose(); // Close the current dialog when button is clicked
            SwingUtilities.invokeLater(MainMenu::new); // Open the main menu after closing the dialog
        });

        // Add button to a panel and then to the dialog 
        JPanel buttonPanel = new JPanel(); // Create a panel to hold buttons
        buttonPanel.add(welcomeButton); // Add the welcome button to the panel
        dialog.add(buttonPanel, BorderLayout.SOUTH); // Add the panel to the bottom of the dialog

        // Make the dialog modal and center it on screen
        dialog.setModal(true); // Make sure user interacts with this dialog before returning to other windows
        dialog.setLocationRelativeTo(null); // Center the dialog on screen
        dialog.setVisible(true); // Make the dialog visible
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(WelcomePage::new);  // Run WelcomePage 
    }
}
