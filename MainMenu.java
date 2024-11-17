import java.awt.*;
import java.io.*;
import java.util.*;
import javax.swing.*;

public class MainMenu {
    private ServiceQueue serviceQueue;
    private final LinkedList<CustInfo> custList;
    private StackManagement stackManagement;

    public MainMenu() {
        custList = new LinkedList<>();
        serviceQueue = new ServiceQueue();
        stackManagement = new StackManagement();

        new WelcomePage(); // Calls WelcomeDialog class for welcome message

        // Read customers from file into custList
        loadCustomersFromFile("C:\\Users\\user\\Coding Folder\\JAVA\\Project baru\\SWC3344_PROJECT\\CustomersLists.txt", custList);

        // Create the main frame
        JFrame frame = new JFrame("Vehicle Service Center");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

        // Turn the main panel to vertical BoxLayout
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

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
        JButton queueButton = new JButton(" 4 - Display customer by lane ");
        JButton exitButton = new JButton(" 5 - Exit "); 

        // Action listener for Exit button
        exitButton.addActionListener(e -> { 
            // Show a message with a custom "Thank You" button
            JOptionPane.showOptionDialog(frame, 
                "Thank you for choosing our booking service center.", 
                "Goodbye", 
                JOptionPane.DEFAULT_OPTION, 
                JOptionPane.INFORMATION_MESSAGE, 
                null, 
                new Object[]{"Thank You"}, 
                "Thank You");
            
            // Exit the application after the message is closed
            System.exit(0); 
        });



        // Action listener for Add button
        addButton.addActionListener(e -> openAddCustomerDialog());

        // Action listener for Display button
        displayButton.addActionListener(e -> {
                    // Get the file content
                    String fileContent = serviceQueue.readFileContent("CustomersLists.txt");

                    // Create a text area and set its properties
                    JTextArea textArea = new JTextArea(20, 50);
                    textArea.setText(fileContent);
                    textArea.setEditable(false); // Make it read-only

                    // Add the text area to a scroll pane
                    JScrollPane scrollPane = new JScrollPane(textArea);
                    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
                    scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

                    // Display the scrollable pane in a dialog
                    JOptionPane.showMessageDialog(
                        frame,
                        scrollPane,
                        "File Content",
                        JOptionPane.INFORMATION_MESSAGE
                    );
            });

        queueButton.addActionListener(e -> {
                    serviceQueue.loadCustomersFromFile("C:\\Users\\user\\Coding Folder\\JAVA\\Project baru\\SWC3344_PROJECT\\CustomersLists.txt");

                    // Fetch data from ServiceQueue
                    Queue<CustInfo> lane1 = serviceQueue.getLane1Queue();
                    Queue<CustInfo> lane2 = serviceQueue.getLane2Queue();
                    Queue<CustInfo> lane3 = serviceQueue.getLane3Queue();

                    // Build a string with data for all lanes
                    StringBuilder displayText = new StringBuilder();

                    // Append Lane 1 data
                    displayText.append("Lane 1:\n");
                    for (CustInfo cust : lane1)
                    {
                        displayText.append(cust.toString()).append("\n");
                    }

                    // Append Lane 2 data
                    displayText.append("\nLane 2:\n");
                    for (CustInfo cust : lane2)
                    {
                        displayText.append(cust.toString()).append("\n");
                    }

                    // Append Lane 1 data
                    displayText.append("\nLane 3:\n");
                    for (CustInfo cust : lane3)
                    {
                        displayText.append(cust.toString()).append("\n");
                    }

                    // Create a JTextArea and add the text
                    JTextArea textArea = new JTextArea(20, 50);
                    textArea.setText(displayText.toString());
                    textArea.setEditable(false);

                    // Add the text area to a scroll pane
                    JScrollPane scrollPane = new JScrollPane(textArea);
                    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
                    scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

                    // Display the scrollable pane in a dialog
                    JOptionPane.showMessageDialog(
                        frame,
                        scrollPane,
                        "Customers by lane",
                        JOptionPane.INFORMATION_MESSAGE
                    );
            });

        // Action listener for Remove button
        removeButton.addActionListener(e -> openRemoveCustomerDialog());

        // Add the other buttons to the button panel
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(displayButton);
        buttonPanel.add(queueButton);
        buttonPanel.add(exitButton);

        panel.add(buttonPanel);

        // Add panel to frame
        frame.add(panel, BorderLayout.CENTER);
        frame.setVisible(true);

        // Center the frame on the screen
        frame.setLocationRelativeTo(null);

    }

    private static void loadCustomersFromFile(String filename, LinkedList<CustInfo> custList) {
        try (BufferedReader in = new BufferedReader(new FileReader(filename))) {
            String inData;
            while ((inData = in.readLine()) != null) {
                if (inData.trim().isEmpty()) continue; // Skip empty lines

                String[] tokens = inData.split(",");
                if (tokens.length < 3) continue; // Skip lines with insufficient data

                // Extract customer details
                String custID = tokens[0].trim();
                String custName = tokens[1].trim();
                String plateNum = tokens[2].trim();
                CustInfo cust = new CustInfo(custID, custName, plateNum);

                // Extract services
                for (int i = 3; i < tokens.length; i += 6) { 
                    try {
                        String serviceID = (i < tokens.length) ? tokens[i].trim() : "Unknown";
                        String serviceName = (i + 1 < tokens.length) ? tokens[i + 1].trim() : "Unknown";
                        String serviceType = (i + 2 < tokens.length) ? tokens[i + 2].trim() : "General";
                        double serviceCost = (i + 3 < tokens.length && tokens[i + 3].matches("\\d+(\\.\\d+)?"))
                            ? Double.parseDouble(tokens[i + 3].trim())
                            : 0.0;
                        String serviceDate = (i + 4 < tokens.length) ? tokens[i + 4].trim() : "N/A";
                        String estimatedCompletionTime = (i + 5 < tokens.length) ? tokens[i + 5].trim() : "N/A";

                        ServiceInfo service = new ServiceInfo(serviceID, serviceName, serviceType, serviceCost, serviceDate, estimatedCompletionTime);
                        cust.addService(service);
                    } catch (IndexOutOfBoundsException e) {
                        System.err.println("Incomplete service data: " + e.getMessage());
                    } catch (NumberFormatException e) {
                        System.err.println("Invalid numeric format: " + e.getMessage());
                    }
                }

                // Add the customer to the list
                custList.add(cust);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error reading file: " + e.getMessage(), "File Read Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateTotalCost(JComboBox<String> servicetypeField1, JComboBox<String> servicetypeField2, JComboBox<String> servicetypeField3, JTextField serviceCostField) {
        // Service cost map
        Map<String, Double> serviceCosts = new HashMap<>();
        serviceCosts.put("None", 0.00);
        serviceCosts.put("Oil Change", 50.00);
        serviceCosts.put("Tire Change", 75.00);
        serviceCosts.put("Brake Inspection", 60.00);
        serviceCosts.put("Battery Replacement", 120.00);
        serviceCosts.put("Transmission Service", 150.00);
        serviceCosts.put("Engine Tune-up", 200.00);
        serviceCosts.put("Exhaust System Repair", 180.00);
        serviceCosts.put("AC Service", 100.00);

        // Get the cost of the selected services
        double totalCost = serviceCosts.get(servicetypeField1.getSelectedItem().toString()) +
            serviceCosts.get(servicetypeField2.getSelectedItem().toString()) +
            serviceCosts.get(servicetypeField3.getSelectedItem().toString());

        // Display the total cost in the service cost field
        serviceCostField.setText("RM " + String.format("%.2f", totalCost));
    }

    private void updateEstimatedCompletionTime(JComboBox<String> servicetypeField1, JComboBox<String> servicetypeField2, JComboBox<String> servicetypeField3, JTextField completionTimeField) {
        // Estimated completion time map in hours (and fractional hours if needed)
        Map<String, Double> serviceCompletionTimes = new HashMap<>();
        serviceCompletionTimes.put("None", 0.0);
        serviceCompletionTimes.put("Oil Change", 1.0);
        serviceCompletionTimes.put("Tire Change", 2.0);
        serviceCompletionTimes.put("Brake Inspection", 1.5);
        serviceCompletionTimes.put("Battery Replacement", 2.0);
        serviceCompletionTimes.put("Transmission Service", 3.0);
        serviceCompletionTimes.put("Engine Tune-up", 2.5);
        serviceCompletionTimes.put("Exhaust System Repair", 3.5);
        serviceCompletionTimes.put("AC Service", 2.0);

        // Get the estimated time for each selected service
        double time1 = serviceCompletionTimes.get(servicetypeField1.getSelectedItem().toString());
        double time2 = serviceCompletionTimes.get(servicetypeField2.getSelectedItem().toString());
        double time3 = serviceCompletionTimes.get(servicetypeField3.getSelectedItem().toString());

        // Calculate total time in hours
        double totalTimeInHours = time1 + time2 + time3;

        // Convert total hours into hours and minutes
        int hours = (int) totalTimeInHours;
        int minutes = (int) ((totalTimeInHours - hours) * 60);

        // Display the total time in the format "X hours Y minutes"
        String totalTimeFormatted = String.format("%d hours %d minutes", hours, minutes);

        // Set the total time in the completion time field
        completionTimeField.setText(totalTimeFormatted);
    }

    private void openAddCustomerDialog() {
        JDialog dialog = new JDialog();

        dialog.setTitle("Add New Customer");
        dialog.setSize(400, 380);  // Adjusted size to include the appointment date field

        JPanel dialogPanel = new JPanel();
        dialogPanel.setLayout(new GridLayout(0, 2));

        // Input fields for customer information
        JTextField custIDField = new JTextField();
        JTextField custNameField = new JTextField();
        JTextField plateNumField = new JTextField();
        JTextField appointmentDateField = new JTextField(); // New text field for Appointment Date

        JComboBox<String> servicetypeField1 = new JComboBox<>(new String[] {
                    "None", "Oil Change", "Tire Change", "Brake Inspection", "Battery Replacement",
                    "Transmission Service", "Engine Tune-up", "Exhaust System Repair", "AC Service"
                });
        JComboBox<String> servicetypeField2 = new JComboBox<>(new String[] {
                    "None", "Oil Change", "Tire Change", "Brake Inspection", "Battery Replacement",
                    "Transmission Service", "Engine Tune-up", "Exhaust System Repair", "AC Service"
                });
        JComboBox<String> servicetypeField3 = new JComboBox<>(new String[] {
                    "None", "Oil Change", "Tire Change", "Brake Inspection", "Battery Replacement",
                    "Transmission Service", "Engine Tune-up", "Exhaust System Repair", "AC Service"
                });

        JTextField serviceCostField1 = new JTextField();
        serviceCostField1.setEditable(false); // Disable editing the field

        JTextField completionTimeField1 = new JTextField(); // Only one completion time field

        dialogPanel.add(new JLabel("Customer ID:"));
        dialogPanel.add(custIDField);

        dialogPanel.add(new JLabel("Customer Name:"));
        dialogPanel.add(custNameField);

        dialogPanel.add(new JLabel("Plate Number:"));
        dialogPanel.add(plateNumField);

        dialogPanel.add(new JLabel("Appointment Date (YY-MM-DD):"));  // New label for Appointment Date
        dialogPanel.add(appointmentDateField);  // New text field for Appointment Date

        dialogPanel.add(new JLabel("Service 1:"));
        dialogPanel.add(servicetypeField1);

        dialogPanel.add(new JLabel("Service 2:"));
        dialogPanel.add(servicetypeField2);

        dialogPanel.add(new JLabel("Service 3:"));
        dialogPanel.add(servicetypeField3);

        dialogPanel.add(new JLabel("Service Cost:"));
        dialogPanel.add(serviceCostField1);

        dialogPanel.add(new JLabel("Completion Time:"));
        dialogPanel.add(completionTimeField1);

        dialog.add(dialogPanel);

        // Update cost and time when a selection is made
        servicetypeField1.addActionListener(e -> {
                    updateTotalCost(servicetypeField1, servicetypeField2, servicetypeField3, serviceCostField1);
                    updateEstimatedCompletionTime(servicetypeField1, servicetypeField2, servicetypeField3, completionTimeField1);
            });

        servicetypeField2.addActionListener(e -> {
                    updateTotalCost(servicetypeField1, servicetypeField2, servicetypeField3, serviceCostField1);
                    updateEstimatedCompletionTime(servicetypeField1, servicetypeField2, servicetypeField3, completionTimeField1);
            });

        servicetypeField3.addActionListener(e -> {
                    updateTotalCost(servicetypeField1, servicetypeField2, servicetypeField3, serviceCostField1);
                    updateEstimatedCompletionTime(servicetypeField1, servicetypeField2, servicetypeField3, completionTimeField1);
            });

        // Add OK button to confirm adding the customer
        JButton okButton = new JButton("Added");
        okButton.addActionListener(e -> {
                    String custID = custIDField.getText().trim();
                    String custName = custNameField.getText().trim();
                    String plateNum = plateNumField.getText().trim();
                    String appointmentDate = appointmentDateField.getText().trim(); // Get the Appointment Date from the field

                    CustInfo newCustomer = new CustInfo(custID, custName, plateNum);

                    // Add service info
                    String serviceID = "Service001"; // Placeholder for actual service ID
                    String serviceName = "Oil Change"; // Example service
                    String serviceType = "Oil Change";
                    double serviceCost = Double.parseDouble(serviceCostField1.getText().replace("RM", "").trim());
                    String serviceDate = "2024-11-16"; // Example date
                    String completionTime = completionTimeField1.getText();

                    ServiceInfo serviceInfo = new ServiceInfo(serviceID, serviceName, serviceType, serviceCost, serviceDate, completionTime);
                    newCustomer.addService(serviceInfo);

                    // Add to customer list
                    custList.add(newCustomer);

                    // Show confirmation message
                    JOptionPane.showMessageDialog(dialog, "New customer has been successfully added!", "Success", JOptionPane.INFORMATION_MESSAGE);

                    dialog.dispose();
            });

        // Add OK button at the bottom
        dialogPanel.add(new JPanel()); // Empty panel for layout purposes
        dialogPanel.add(okButton);

        dialog.setModal(true); // Set dialog as modal
        dialog.setLocationRelativeTo(null); // Center the dialog on the screen
        dialog.setVisible(true); // Show the dialog
    }

    private void openRemoveCustomerDialog() {
        JDialog dialog = new JDialog();
        dialog.setTitle("Remove Customer");
        dialog.setSize(400, 200);

        JPanel dialogPanel = new JPanel();
        dialogPanel.setLayout(new GridLayout(2, 2));

        JTextField custIDField = new JTextField();
        dialogPanel.add(new JLabel("Customer ID:"));
        dialogPanel.add(custIDField);

        JButton removeButton = new JButton("Remove");
        removeButton.addActionListener(e -> {
                    String custID = custIDField.getText().trim();

                    CustInfo customerToRemove = null;
                    for (CustInfo customer : custList) {
                        if (customer.getID().equals(custID)) {
                            customerToRemove = customer;
                            break;
                        }
                    }

                    if (customerToRemove != null) {
                        custList.remove(customerToRemove);
                        JOptionPane.showMessageDialog(dialog, "Customer removed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(dialog, "Customer ID not found!", "Error", JOptionPane.ERROR_MESSAGE);
                    }

                    dialog.dispose();
            });

        // Add OK button at the bottom
        dialogPanel.add(new JPanel()); // Empty panel for layout purposes
        dialogPanel.add(removeButton); // Add the remove button

        dialog.add(dialogPanel);

        dialog.setModal(true); // Set dialog as modal
        dialog.setLocationRelativeTo(null); // Center the dialog on the screen
        dialog.setVisible(true); // Show the dialog
    }   

    public static void main(String[] args) {
        // Start the application
        SwingUtilities.invokeLater(MainMenu::new);
    }
}
