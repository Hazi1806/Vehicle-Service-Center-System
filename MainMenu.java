import java.awt.*;
import java.io.*;
import java.util.*;
import javax.swing.*;

public class MainMenu {
    private ServiceQueue serviceQueue;
    private final LinkedList<CustInfo> custList;
    private CompleteStack completeStack;

    public MainMenu() {
        custList = new LinkedList<>();
        serviceQueue = new ServiceQueue();
        completeStack = new CompleteStack();

        new WelcomePage(); // Calls WelcomeDialog class for welcome message

        // Read customers from file into custList
        loadCustomersFromFile("C:\\Users\\user\\Coding Folder\\JAVA\\Project baru\\SWC3344_PROJECT\\CustomersLists.txt", custList);

        // Load completed transactions
        completeStack.loadFromFile("CompletedTransactions.txt");

        // Create the main frame
        JFrame frame = new JFrame(" WiseWheel Vehicle Service Center");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

        // Turn the main panel to vertical BoxLayout
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        // Add a title label
        JLabel titleLabel = new JLabel("WiseWheel Service Center ", SwingConstants.CENTER);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(15));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 1, 0, 10));
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Declare buttons
        JButton addButton = new JButton(" 1 - Add a new customer/service ");
        JButton removeButton = new JButton(" 2 - Remove customer/service ");
        JButton displayManageButton = new JButton(" 3 - Display and Manage ");
        JButton exitButton = new JButton(" 4 - Exit "); 

        // Action listener for Exit button
        exitButton.addActionListener(e -> {
                    JOptionPane.showMessageDialog(frame, "Thank you for choosing our booking service center", "Goodbye", JOptionPane.INFORMATION_MESSAGE);
                    System.exit(0); // Exit the application
            });

        // Action listener for Add button
        addButton.addActionListener(e -> openAddCustomerDialog());

        // Action listener for the new combined button
        displayManageButton.addActionListener(e -> {

        // Create a dialog with tabs
        JDialog dialog = new JDialog();
        dialog.setTitle("Display & Manage Services");
        dialog.setSize(600, 400);
        dialog.setModal(true);

        JTabbedPane tabbedPane = new JTabbedPane();

        // Tab 1: Customer List
            JPanel customerListPanel = new JPanel(new BorderLayout());
            JTextArea customerTextArea = new JTextArea(20, 50);

            String filePath = "C:\\Users\\user\\Coding Folder\\JAVA\\Project baru\\SWC3344_PROJECT\\CustomersLists.txt";
            String fileContent = serviceQueue.readFileContent(filePath);

            if (fileContent == null) {
                 customerTextArea.setText("Error reading file: " + filePath + "\n\nThe file could not be found or read.\nPlease check the file path and try again.");
            } else if (fileContent.trim().isEmpty()) {
                customerTextArea.setText("Error: The file is empty.\nPlease ensure the file contains valid data.");
            } else {

        // Splitting the file content into lines
            String[] lines = fileContent.split("\n");
            StringBuilder formattedContent = new StringBuilder();

            int customerCount = 0; // Counter to track how many customers are processed

        // Debug: Checking the number of lines read
            System.out.println("Total lines read: " + lines.length);

                for (String line : lines) {
                    // Skip empty lines
                    if (line.trim().isEmpty()) {
                        continue;
                    }

        // Assuming each line has comma-separated values for customer details
            String[] details = line.split(", ");
                            
        // Debug: Print the details to check the split data
                System.out.println("Processing line: " + line);
                System.out.println("Details length: " + details.length);
                            
                if (details.length >= 7) {  // Ensure that the line has at least 7 fields (Customer ID, Name, etc.)

        // Basic customer information
                formattedContent.append("Customer ID: ").append(details[0]).append("\n")
                    .append("Name: ").append(details[1]).append("\n")
                    .append("Plate Number: ").append(details[2]).append("\n");

        // Process multiple services
                int serviceIndex = 3;
                int serviceCount = 1;
                while (serviceIndex < details.length - 1) {
                    formattedContent.append("Service ").append(serviceCount).append(": ").append(details[serviceIndex]).append("\n")
                        .append("Estimated Time: ").append(details[serviceIndex + 1]).append("\n")
                        .append("Price: ").append(details[serviceIndex + 2]).append("\n\n"); // Add a blank line for separation
                            serviceIndex += 3;  // Move to the next service block (service name, time, price)
                            serviceCount++;
            }
                                
        // Appointment date
            formattedContent.append("Service Date: ").append(details[details.length - 1]).append("\n")
                            .append("\n----------------------------------------\n");

                            customerCount++;
                            } else {

        // Debugging case for lines that don't have the expected format
                                System.out.println("Skipping invalid line: " + line);
                            }
                        }
                        
                        if (formattedContent.length() == 0) {
                            customerTextArea.setText("No valid customer data found.");
                        } else {
                            // Show the number of customers processed
                            customerTextArea.setText("Total customers processed: " + customerCount + "\n\n" + formattedContent.toString());
                        }
                    }

                    customerTextArea.setEditable(false);
                    customerListPanel.add(new JScrollPane(customerTextArea), BorderLayout.CENTER);
                    tabbedPane.addTab("Customer List", customerListPanel);

                    // loads data for all lanes
                    serviceQueue.loadCustomersFromFile("C:\\Users\\user\\Coding Folder\\JAVA\\Project baru\\SWC3344_PROJECT\\CustomersLists.txt");

                    // Tab 1: Display Lane 1 (Customers with 3 or Fewer Services - Lane 1)
                    JPanel customersByLanePanel = new JPanel(new BorderLayout());
                    JTextArea lane1TextArea = new JTextArea(20, 50);

                    // get Lane 1 data
                    Queue<CustInfo> lane1 = serviceQueue.getLane1Queue();
                    StringBuilder lane1DisplayText = new StringBuilder();
                    int customerCountLane1 = 0;

                    // Format lane 1 data (Customers with 3 or fewer services assigned to Lane 1)
                    lane1DisplayText.append("Lane 1 (Customers with 3 or Fewer Services):\n");
                    for (CustInfo cust : lane1) {
                        // Process each customer for Lane 1
                        lane1DisplayText.append("Customer ID: ").append(cust.getID()).append("\n");
                        lane1DisplayText.append("Name: ").append(cust.getName()).append("\n");
                        lane1DisplayText.append("Plate Number: ").append(cust.getPlateNum()).append("\n");

                        // Process the customer's services
                        for (ServiceInfo service : cust.getServiceList()) {
                            lane1DisplayText.append("Service Name: ").append(service.getServiceName()).append("\n");
                            lane1DisplayText.append("Estimated Time: ").append(service.getEstimatedCompletionTime()).append("\n");
                            lane1DisplayText.append("Cost: RM").append(service.getServiceCost()).append("\n");
                            lane1DisplayText.append("Service Date: ").append(service.getServiceDate()).append("\n");
                            lane1DisplayText.append("\n"); // Add space between services
                        }

                        lane1DisplayText.append("----------------------------------------\n");
                        customerCountLane1++;
                    }

                    if (customerCountLane1 == 0) {
                        lane1DisplayText.append("No customers for Lane 1.\n");
                    }

                    lane1DisplayText.insert(0, "Total customers processed in Lane 1: " + customerCountLane1 + "\n\n");

                    // Set the text into the JTextArea and make it cannot be edited 
                    lane1TextArea.setText(lane1DisplayText.toString());
                    lane1TextArea.setEditable(false);

                    // Add the JTextArea into a JScrollPane for scrolling
                    customersByLanePanel.add(new JScrollPane(lane1TextArea), BorderLayout.CENTER);

                    // Add this panel to the tabbed pane
                    tabbedPane.addTab("Display Lane 1", customersByLanePanel);

                    // Tab 2: Display Lane 2 (Customers with 3 or Fewer Services - Lane 2)
                    JPanel displayLane2Panel = new JPanel(new BorderLayout());
                    JTextArea lane2TextArea = new JTextArea(20, 50);

                    // catch Lane 2 data
                    Queue<CustInfo> lane2 = serviceQueue.getLane2Queue();
                    StringBuilder lane2DisplayText = new StringBuilder();
                    int customerCountLane2 = 0;

                    // Format lane 2 data (Customers with 3 or fewer services assigned to Lane 2)
                    lane2DisplayText.append("Lane 2 (Customers with 3 or Fewer Services):\n");
                    for (CustInfo cust : lane2) {
                        // Process each customer for Lane 2
                        lane2DisplayText.append("Customer ID: ").append(cust.getID()).append("\n");
                        lane2DisplayText.append("Name: ").append(cust.getName()).append("\n");
                        lane2DisplayText.append("Plate Number: ").append(cust.getPlateNum()).append("\n");

                        // Process the customer's services
                        for (ServiceInfo service : cust.getServiceList()) {
                            lane2DisplayText.append("Service Name: ").append(service.getServiceName()).append("\n");
                            lane2DisplayText.append("Estimated Time: ").append(service.getEstimatedCompletionTime()).append("\n");
                            lane2DisplayText.append("Cost: RM").append(service.getServiceCost()).append("\n");
                            lane2DisplayText.append("Service Date: ").append(service.getServiceDate()).append("\n");
                            lane2DisplayText.append("\n"); // Add space between services
                        }

                        lane2DisplayText.append("----------------------------------------\n");
                        customerCountLane2++;
                    }

                    if (customerCountLane2 == 0) {
                        lane2DisplayText.append("No customers for Lane 2.\n");
                    }

                    lane2DisplayText.insert(0, "Total customers processed in Lane 2: " + customerCountLane2 + "\n\n");

                    // Set the text into the JTextArea and make it cannot be edited 
                    lane2TextArea.setText(lane2DisplayText.toString());
                    lane2TextArea.setEditable(false);

                    // Add the JTextArea into a JScrollPane for scrolling
                    displayLane2Panel.add(new JScrollPane(lane2TextArea), BorderLayout.CENTER);

                    // Add this panel to the tabbed pane
                    tabbedPane.addTab("Display Lane 2", displayLane2Panel);

                    // Tab 3: Display Lane 3 (Customers with More Than 3 Services, Limit to 4 Service Types per Customer)
                    JPanel displayLane3Panel = new JPanel(new BorderLayout());
                    JTextArea lane3TextArea = new JTextArea(20, 50);

                    // catch Lane 3 data
                    Queue<CustInfo> lane3 = serviceQueue.getLane3Queue();
                    StringBuilder lane3DisplayText = new StringBuilder();
                    int customerCountLane3 = 0;

                    // Format lane 3 data (Customers with more than 3 services)
                    lane3DisplayText.append("Lane 3 (Customers with More Than 3 Services, Limited to 4 Services):\n");
                    for (CustInfo cust : lane3) {
                        // Only process customers with more than 3 services
                        if (cust.getServiceList() != null && cust.getServiceList().size() > 3) {
                            lane3DisplayText.append("Customer ID: ").append(cust.getID()).append("\n");
                            lane3DisplayText.append("Name: ").append(cust.getName()).append("\n");
                            lane3DisplayText.append("Plate Number: ").append(cust.getPlateNum() != null ? cust.getPlateNum() : "Unknown Plate").append("\n");

                            // Process only the first 4 services for this customer
                            int serviceLimit = Math.min(cust.getServiceList().size(), 4); // Limit to 4 services

                            for (int i = 0; i < serviceLimit; i++) {
                                ServiceInfo service = cust.getServiceList().get(i);
                                lane3DisplayText.append("--------------------------------------------------------\n");
                                lane3DisplayText.append("Service Name: ").append(service.getServiceName()).append("\n");

                                // Ensure service details are displayed correctly
                                String estimatedTime = service.getEstimatedCompletionTime() != null ? service.getEstimatedCompletionTime() : "TBD";
                                double cost = service.getServiceCost();
                                String serviceDate = service.getServiceDate() != null ? service.getServiceDate() : "TBD";

                                // Display the service attributes
                                lane3DisplayText.append("Estimated Time: ").append(estimatedTime).append("\n");
                                lane3DisplayText.append("Cost: RM").append(cost > 0 ? cost : "0.0").append("\n");
                                lane3DisplayText.append("Service Date: ").append(serviceDate).append("\n");
                            }

                            lane3DisplayText.append("----------------------------------------\n");
                            customerCountLane3++;
                        }
                    }

                    if (customerCountLane3 == 0) {
                        lane3DisplayText.append("No customers for Lane 3.\n");
                    }

                    lane3DisplayText.insert(0, "Total customers processed in Lane 3: " + customerCountLane3 + "\n\n");

                    // Set the text into the JTextArea and make it cannot be edited 
                    lane3TextArea.setText(lane3DisplayText.toString());
                    lane3TextArea.setEditable(false);//false

                    // Add the JTextArea into a JScrollPane for scrolling
                    displayLane3Panel.add(new JScrollPane(lane3TextArea), BorderLayout.CENTER);

                    // Add this panel to the tabbed pane
                    tabbedPane.addTab("Display Lane 3", displayLane3Panel);
                    

                    // Tab 3: Completed Transactions
                    JPanel completedTransactionsPanel = new JPanel(new BorderLayout());

                    // Top buttons for managing transactions
                    JPanel transactionButtonsPanel = new JPanel(new FlowLayout());
                    JButton addTransactionButton = new JButton("Add Transaction");
                    JButton viewTransactionsButton = new JButton("View Transactions");
                    transactionButtonsPanel.add(addTransactionButton);
                    transactionButtonsPanel.add(viewTransactionsButton);

                    JTextArea transactionsTextArea = new JTextArea(20, 50);
                    transactionsTextArea.setEditable(false);

                    addTransactionButton.addActionListener(ev -> {
                                String customerName = JOptionPane.showInputDialog("Enter Customer Name:");
                                if (customerName == null) return;

                                String costInput = JOptionPane.showInputDialog("Enter Total Cost:");
                                if (costInput == null) return;

                                try {
                                    double totalCost = Double.parseDouble(costInput);
                                    completeStack.addTransaction(customerName, totalCost);
                                    JOptionPane.showMessageDialog(null, "Transaction added successfully!");
                                } catch (NumberFormatException ex) {
                                    JOptionPane.showMessageDialog(null, "Invalid cost. Please enter a numeric value.");
                                }
                        });

                    viewTransactionsButton.addActionListener(ev -> {
                                StringBuilder transactionData = new StringBuilder();
                                for (CompleteStack.Transaction transaction : completeStack.getTransactions()) {
                                    transactionData.append(transaction.toString()).append("\n");
                                }
                                transactionsTextArea.setText(transactionData.toString());
                        });

                    completedTransactionsPanel.add(transactionButtonsPanel, BorderLayout.NORTH);
                    completedTransactionsPanel.add(new JScrollPane(transactionsTextArea), BorderLayout.CENTER);
                    tabbedPane.addTab("Completed Transactions", completedTransactionsPanel);

                    // Add tabs to dialog
                    dialog.add(tabbedPane);

                    // Display the dialog
                    dialog.setLocationRelativeTo(null);
                    dialog.setVisible(true);
            });

        // Action listener for Remove button
        removeButton.addActionListener(e -> openRemoveCustomerDialog());

        // Add the other buttons to the button panel
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(displayManageButton);
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
    
                // Use regex to split by comma and trim whitespace
                String[] tokens = inData.split("\\s*,\\s*");
                if (tokens.length < 3) continue; // Skip lines with insufficient data
    
                // Extract customer details (ID, Name, Plate Number)
                String custID = tokens[0].trim();
                String custName = tokens[1].trim();
                String plateNum = tokens[2].trim();
                CustInfo cust = new CustInfo(custID, custName, plateNum);
    
                // Extract services (starting from index 3)
                for (int i = 3; i < tokens.length; i += 4) {
                    try {
                        // Ensure there are enough tokens for service details
                        if (i + 3 < tokens.length) {
                            String serviceName = tokens[i].trim();
                            double serviceCost = Double.parseDouble(tokens[i + 1].trim());
                            String serviceDate = tokens[i + 2].trim();
                            String estimatedCompletionTime = tokens[i + 3].trim();
    
                            // Create a new service and add it to the customer
                            ServiceInfo service = new ServiceInfo(serviceName, serviceCost, serviceDate, estimatedCompletionTime);
                            cust.addService(service);
                        } else {
                            System.err.println("Incomplete service data for customer ID " + custID);
                        }
                    } catch (NumberFormatException e) {
                        System.err.println("Invalid numeric format in service data for customer ID " + custID + ": " + e.getMessage());
                    } catch (IndexOutOfBoundsException e) {
                        System.err.println("Error processing services for customer ID " + custID + ": " + e.getMessage());
                    }
                }
    
                // Add the customer to the list
                custList.add(cust);
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
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
        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> {
                    String custID = custIDField.getText().trim();
                    String custName = custNameField.getText().trim();
                    String plateNum = plateNumField.getText().trim();

                    CustInfo newCustomer = new CustInfo(custID, custName, plateNum);

                    // Add service info
                    
                    String serviceName = "Oil Change"; // Example service
                    
                    double serviceCost = Double.parseDouble(serviceCostField1.getText().replace("RM", "").trim());
                    String serviceDate = "2024-11-16"; // Example date
                    String completionTime = completionTimeField1.getText();

                    ServiceInfo serviceInfo = new ServiceInfo(serviceName, serviceCost, serviceDate, completionTime);
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
    // Create a new dialog window for removing a customer
    JDialog dialog = new JDialog();
    dialog.setTitle("Remove Customer");  // Set dialog title
    dialog.setSize(400, 300);  // Set the dialog size

    // Create a panel to hold the components inside the dialog
    JPanel dialogPanel = new JPanel(new BorderLayout());

    // Create a list model and populate it with customer names and IDs from the custList
    DefaultListModel<String> customerModel = new DefaultListModel<>();
    for (CustInfo customer : custList) {
        customerModel.addElement(customer.getName() + " (" + customer.getID() + ")");  // Display name and ID
    }

    // Create a JList to display the customer names in the dialog
    JList<String> customerList = new JList<>(customerModel);
    customerList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);  // Allow only one selection at a time

    // Wrap the JList in a JScrollPane to enable scrolling if there are many customers
    JScrollPane scrollPane = new JScrollPane(customerList);
    dialogPanel.add(scrollPane, BorderLayout.CENTER);  // Add the scrollable list to the center of the panel

    // Create a "Remove" button and add an action listener
    JButton removeButton = new JButton("Remove");
    removeButton.addActionListener(e -> {
        int selectedIndex = customerList.getSelectedIndex();  // Get the selected customer index

        // If no customer is selected, show an error message
        if (selectedIndex == -1) {
            JOptionPane.showMessageDialog(dialog, "Please select a customer to remove.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            // If a customer is selected, remove them from the data structure and the UI list
            custList.remove(selectedIndex);  // Remove from the customer data list
            customerModel.remove(selectedIndex);  // Update the displayed list
            JOptionPane.showMessageDialog(dialog, "Customer removed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);  // Show success message
        }
    });

    // Add the remove button at the bottom of the dialog
    JPanel buttonPanel = new JPanel();
    buttonPanel.add(removeButton);
    dialogPanel.add(buttonPanel, BorderLayout.SOUTH);

    dialog.add(dialogPanel);
    dialog.setModal(true); // Set dialog as modal
    dialog.setLocationRelativeTo(null); // Center the dialog on the screen
    dialog.setVisible(true); // Show the dialog
}

    public void saveToFile(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (CompleteStack.Transaction transaction : completeStack.getTransactions()) { // Fully qualify the class
                writer.write(transaction.getCustomerName() + "," + transaction.getTotalCost());
                writer.newLine();
            }
            System.out.println("Completed transactions saved successfully.");
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        // Start the application
        SwingUtilities.invokeLater(MainMenu::new);
    }
}
