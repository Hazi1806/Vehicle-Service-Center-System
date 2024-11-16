import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WelcomePage {

    public WelcomePage() {
        JDialog dialog = new JDialog();
        dialog.setTitle("Welcome");
        dialog.setSize(300, 150);
        dialog.setLayout(new BorderLayout());
    
        // Create label for the welcome message
        JLabel welcomeLabel = new JLabel("Welcome to the Vehicle Service Center!", SwingConstants.CENTER);
        dialog.add(welcomeLabel, BorderLayout.CENTER);
    
        // Create custom button with the text "Welcome"
        JButton welcomeButton = new JButton("Welcome");
    
        // Set the preferred size of the button to make it smaller
        welcomeButton.setPreferredSize(new Dimension(100, 30)); // Smaller button size
    
        // Add action listener to the button
        welcomeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose(); // Close the welcome dialog
            }
        });
    
        // Add the button to the dialog
        dialog.add(welcomeButton, BorderLayout.SOUTH);
        dialog.setModal(true);
        dialog.setLocationRelativeTo(null); // Center the dialog on the screen
        dialog.setVisible(true); // Show the dialog
    }

    public static void main(String[] args) {
        new WelcomePage(); // To display the welcome page
    }
}
