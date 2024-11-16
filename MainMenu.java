import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;
import javax.swing.*;

public class MainMenu {
    // Store customer information using LinkedList
    private ServiceQueue serviceQueue;
    private LinkedList<CustInfo> custList;

    // Constructor for MainMenu
    public MainMenu() {
        custList = new LinkedList<>();
        serviceQueue = new ServiceQueue();

        // Show welcome message in a custom dialog when the app starts
        showWelcomeDialog();

        // Read customers from file into custList
        loadCustomersFromFile("CustomersLists.txt", custList);

        // Create the main frame
        JFrame frame = new JFrame("Vehicle Service Center");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout());

        // Turn the main panel to vertical BoxLayout
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Add a title label
        JLabel titleLabel = new JLabel(" Vehicle Service Center ", SwingConstants.CENTER);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(15));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 1, 0, 10));
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Declare buttons
        JButton addButton = new JButton(" 1 - Add a new customer/service ");
        JButton removeButton = new JButton(" 2 - Remove customer/service ");
        JButton displayButton = new JButton(" 3 - Display customer/service ");
        JButton exitButton = new JButton(" 4 - Exit "); 

        // Action listener for Exit button
        exitButton.addActionListener(new ActionListener() { 
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Thank you for choosing our booking service center", "Goodbye", JOptionPane.INFORMATION_MESSAGE);
                System.exit(0); // Exit the application
            }
        });

        // Action listener for Add button
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openAddCustomerDialog();
            }
        });

        // Action listener for Display button
        displayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                serviceQueue.displayQueueInfo();  // Display customer info in queues
            }
        });

        // Action listener for Remove button
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openRemoveCustomerDialog();
            }
        });

        // Add the other buttons to the button panel
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(displayButton);
        buttonPanel.add(exitButton);

        panel.add(buttonPanel);

        // Add panel to frame
        frame.add(panel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private void showWelcomeDialog() {
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
    
    // Method to read customers from txtFile into custList
    private static void loadCustomersFromFile(String filename, LinkedList<CustInfo> custList) {
        try (BufferedReader in = new BufferedReader(new FileReader(filename))) { 
            String inData;
            while ((inData = in.readLine()) != null) {
                StringTokenizer st = new StringTokenizer(inData, ",");
                String custID = st.nextToken();
                String custName = st.nextToken();
                String plateNum = st.nextToken();
                CustInfo cust = new CustInfo(custID, custName, plateNum);

                while (st.hasMoreTokens()) {
                    String serviceID = st.nextToken();
                    String serviceName = st.nextToken();
                    String serviceType = st.nextToken();
                    double serviceCost = Double.parseDouble(st.nextToken());  // Now parsing as double
                    String serviceDate = st.nextToken();
                    String estimatedCompletionTime = st.nextToken();

                    ServiceInfo service = new ServiceInfo(serviceID, serviceName, serviceType, serviceCost, serviceDate, estimatedCompletionTime);
                    cust.addService(service);
                }
                custList.add(cust);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Failed reading from file: " + e.getMessage());
        }
    }

    // Updated openAddCustomerDialog method
    private void openAddCustomerDialog() {
        JDialog dialog = new JDialog();
        dialog.setTitle("Add New Customer");
        dialog.setSize(400, 400);

        JPanel dialogPanel = new JPanel();
        dialogPanel.setLayout(new GridLayout(0, 2));

        // Input fields for customer information
        JTextField custIDField = new JTextField();
        JTextField custNameField = new JTextField();
        JTextField plateNumField = new JTextField();

        JComboBox<String> servicetypeField = new JComboBox<>(new String[] {
            "Oil Change", "Tire Change", "Brake Inspection", "Battery Replacement", 
            "Transmission Service", "Engine Tune-up", "Exhaust System Repair", "AC Service"
        });
        JTextField serviceCostField = new JTextField();
        JTextField serviceDateField = new JTextField();
        JTextField completionTimeField = new JTextField();

        dialogPanel.add(new JLabel("Customer ID:"));
        dialogPanel.add(custIDField);

        dialogPanel.add(new JLabel("Customer Name:"));
        dialogPanel.add(custNameField);

        dialogPanel.add(new JLabel("Plate Number:"));
        dialogPanel.add(plateNumField);

        dialogPanel.add(new JLabel("Service type "));
        dialogPanel.add(servicetypeField);

        dialogPanel.add(new JLabel("Service Cost"));
        dialogPanel.add(serviceCostField);

        dialogPanel.add(new JLabel("Service Date:"));
        dialogPanel.add(serviceDateField);

        dialogPanel.add(new JLabel("Estimation Completion Time:"));
        dialogPanel.add(completionTimeField);

        JButton saveButton = new JButton("Save");

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String custID = custIDField.getText();
                String custName = custNameField.getText();
                String plateNum = plateNumField.getText();

                // Create and add CustInfo object to the list
                CustInfo cust = new CustInfo(custID, custName, plateNum);

                String serviceType = (String) servicetypeField.getSelectedItem();

                // Adding service
                String serviceID = "S" + Math.random();
                String serviceName = "Standard" + serviceType;
                double serviceCost = Double.parseDouble(serviceCostField.getText()); // Parsing to double
                String serviceDate = serviceDateField.getText();
                String estimatedCompletionTime = completionTimeField.getText();

                ServiceInfo service = new ServiceInfo(serviceID, serviceName, serviceType, serviceCost, serviceDate, estimatedCompletionTime);

                cust.addService(service);  // Add service to customer

                // Add customer to list and assign to service lane
                custList.add(cust);
                serviceQueue.assignCustomerToLane(cust); // Assign customer to the appropriate lane

                // Show confirmation message
                JOptionPane.showMessageDialog(dialog, "Customer added successfully!");
                dialog.dispose(); // Close the dialog after saving
            }
        });

        dialogPanel.add(saveButton);
        dialog.getContentPane().add(dialogPanel);
        dialog.setVisible(true); // Show the dialog
    }

    // Open the dialog to remove a customer by customerID
    private void openRemoveCustomerDialog() {
        JDialog dialog = new JDialog();
        dialog.setTitle("Remove Customer");
        dialog.setSize(400, 200);

        JPanel dialogPanel = new JPanel();
        dialogPanel.setLayout(new GridLayout(2, 2));

        // Input field for customer ID
        JTextField custIDField = new JTextField();

        dialogPanel.add(new JLabel("Enter Customer ID:"));
        dialogPanel.add(custIDField);

        JButton removeButton = new JButton("Remove Customer");

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String custID = custIDField.getText().trim();

                // Search for the customer by ID and remove from the list and queue
                boolean removed = false;

                // Loop through the custList to find and remove the customer
                for (CustInfo cust : custList) {
                    if (cust.getID().equals(custID)) {
                        custList.remove(cust); // Remove customer from the list
                        serviceQueue.removeCustomerFromQueue(cust); // Remove customer from the appropriate lane
                        JOptionPane.showMessageDialog(dialog, "Customer removed successfully!");
                        removed = true;
                        break;
                    }
                }

                // If customer wasn't found, show a message
                if (!removed) {
                    JOptionPane.showMessageDialog(dialog, "Customer not found!");
                }

                dialog.dispose(); // Close the dialog after processing
            }
        });

        dialogPanel.add(removeButton);
        dialog.getContentPane().add(dialogPanel);
        dialog.setVisible(true); // Show the dialog
    }

    // Main method to run application
    public static void main(String[] args) {
        new MainMenu(); // Make sure this line calls the constructor
    }
}
