import java.awt.*;
import javax.swing.*;

public class WelcomePage {

    public WelcomePage() {
        // Let's create a pop-up dialog to welcome our users.
        JDialog dialog = new JDialog();
        
        // Setting the title of the dialog to "Welcome"
        dialog.setTitle("Welcome");
        
        // the size of the dialog 
        dialog.setSize(300, 150);
        
        // We're using BorderLayout to make it easy to arrange things inside the dialog.
        dialog.setLayout(new BorderLayout());

        // This label will show welcome message and center it 
        JLabel welcomeLabel = new JLabel("Welcome to WiseWheel Service Center!", SwingConstants.CENTER);
        
        // Adding the label to the center of the dialog. This is where we want users to focus.
        dialog.add(welcomeLabel, BorderLayout.CENTER);

        //  button that says "Welcome". 
        JButton welcomeButton = new JButton("Welcome");
        
        // Setting a preferred size for the button so it fits well and looks good.
        welcomeButton.setPreferredSize(new Dimension(100, 30));
        
        // When the button is clicked, we'll close the dialog. 
        welcomeButton.addActionListener(e -> dialog.dispose());
        
        // Placing the button at the bottom of the dialog. 
        dialog.add(welcomeButton, BorderLayout.SOUTH);

        // Making the dialog modal so users have to deal with this pop-up before doing anything else.
        dialog.setModal(true);
        
        // Centering the dialog on the screen 
        dialog.setLocationRelativeTo(null);
        
        // show the dialog
        dialog.setVisible(true);
    }
}
