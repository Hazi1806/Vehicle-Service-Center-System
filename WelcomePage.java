import java.awt.*;
import javax.swing.*;

public class WelcomePage {

    public WelcomePage() {
        // Create a dialog box to serve as our welcome message pop-up.
        JDialog dialog = new JDialog();
        dialog.setTitle("Welcome"); // Give the dialog a title that sets the tone.
        dialog.setSize(300, 150); // Set a fixed size—big enough to look good, but not overwhelming.
        dialog.setLayout(new BorderLayout()); // Use BorderLayout to organize components easily.

        // Add a label with a warm, friendly welcome message.
        JLabel welcomeLabel = new JLabel("Welcome to the Vehicle Service Center!", SwingConstants.CENTER);
        dialog.add(welcomeLabel, BorderLayout.CENTER); // Place the label in the center of the dialog.

        // Create a button labeled "Welcome" 
        JButton welcomeButton = new JButton("Welcome");

        // Adjust the button size to fit the dialog aesthetics—neither too big nor too small.
        welcomeButton.setPreferredSize(new Dimension(100, 30)); 

        // Add a click event to the button so the dialog closes gracefully when clicked.
        welcomeButton.addActionListener(e -> dialog.dispose());

        // Place the button neatly at the bottom of the dialog using BorderLayout.SOUTH.
        dialog.add(welcomeButton, BorderLayout.SOUTH);

        // Make the dialog modal to ensure it gets the user's full attention.
        dialog.setModal(true);

        // Center the dialog on the screen—it feels more professional and user-friendly.
        dialog.setLocationRelativeTo(null);

        // Finally, show the dialog to the user with our warm welcome!
        dialog.setVisible(true);
    }
}
