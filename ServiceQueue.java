import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ServiceQueue {
    // Three service lanes (each has a queue for customers)
    private final Queue<CustInfo> lane1Queue; // Queue for customers with 3 or fewer services (Lane 1)
    private final Queue<CustInfo> lane2Queue; // Queue for customers with 3 or fewer services (Lane 2)
    private final Queue<CustInfo> lane3Queue; // Queue for customers with more than 3 services (Lane 3)

    // Counter to alternate between Lane 1 and Lane 2 for customers with 3 or fewer services
    private int lane1And2Counter;

    // Constructor to initialize the three queues
    public ServiceQueue() {
        lane1Queue = new LinkedList<>(); // Initialize Lane 1 queue
        lane2Queue = new LinkedList<>(); // Initialize Lane 2 queue
        lane3Queue = new LinkedList<>(); // Initialize Lane 3 queue
        lane1And2Counter = 0; // Start with Lane 1 for the first customer
    }

    // Adds a customer to the appropriate lane based on their requested services
    public void enqueue(CustInfo cust) {
        assignCustomerToLane(cust); // Decide which lane to place the customer in based on their service count
    }

    // Reads customer data from a file and assigns them to lanes
    public void loadCustomersFromFile(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;

           // Process each line in the file
            while ((line = br.readLine()) != null) {
                // Split the line into parts: ID, name, and services
                String[] customerData = line.split(",");

                // Skip lines with incomplete or invalid data
                if (customerData.length < 2) {
                    System.out.println("Skipping invalid line: " + line);
                    continue; // Move to the next line if data is insufficient
                }

                // Get the customer's ID and name
                String customerID = customerData[0]; // First element is the customer ID
                String customerName = customerData[1]; // Second element is the customer name
                List<ServiceInfo> requestedServices = new ArrayList<>(); // List to hold requested services

                // Read services from the remaining parts of the line
                int serviceStartIndex = 2; // Start reading services from index 2
                while (serviceStartIndex < customerData.length) {
                    String serviceName = customerData[serviceStartIndex]; // Service name at current index
                    String serviceDate = (serviceStartIndex + 1) < customerData.length ? customerData[serviceStartIndex + 1] : null;

                    // Create a service object and add it to the list of requested services
                    ServiceInfo service = new ServiceInfo( serviceName, 0.0, serviceDate, null);
                    requestedServices.add(service); // Add created service to the list
                    serviceStartIndex += 2; // Move to the next service pair (name and date)
                }

                // Create a customer object and assign their services to it
                CustInfo customer = new CustInfo(customerID, customerName, "Unknown Plate");
                for (ServiceInfo service : requestedServices) {
                    customer.addService(service); // Add each service to the customer's list of services
                }

                // Assign the customer to the correct lane based on their number of services
                assignCustomerToLane(customer);
            }
        } catch (IOException e) {
            // Print an error message if file reading fails
            System.out.println("Error reading the file: " + e.getMessage());
        }
    }

    // Decides which lane a customer should go to, based on the number of services they have requested
    private void assignCustomerToLane(CustInfo cust) {
        int numberOfServices = cust.getServiceList().size(); // Get the number of requested services

        if (numberOfServices > 3) {
            lane3Queue.add(cust); // If more than 3 services, add to Lane 3 queue
        } else {
            if (lane1And2Counter % 2 == 0) {
                lane1Queue.add(cust); // Alternate between Lane 1 and Lane 2 for customers with 3 or fewer services
            } else {
                lane2Queue.add(cust); 
            }
            lane1And2Counter++; // Increment counter after adding a customer to alternate lanes correctly
        }
    }

    // Shows all customers in each lane with their details and requested services in a formatted string
    public String displayQueueDetailsByID() {
        StringBuilder queueDetails = new StringBuilder(); // StringBuilder for efficient string concatenation

        // Add details of customers in Lane 1
        queueDetails.append("Lane 1 Queue:\n");
        for (CustInfo customer : lane1Queue) {
            queueDetails.append(displayCustomerWithServices(customer)); // Append formatted details of each customer in Lane 1
        }

        // Add details of customers in Lane 2
        queueDetails.append("\nLane 2 Queue:\n");
        for (CustInfo customer : lane2Queue) {
            queueDetails.append(displayCustomerWithServices(customer)); // Append formatted details of each customer in Lane 2
        }

        // Add details of customers in Lane 3
        queueDetails.append("\nLane 3 Queue:\n");
        for (CustInfo customer : lane3Queue) {
            queueDetails.append(displayCustomerWithServices(customer)); // Append formatted details of each customer in Lane 3
        }

        return queueDetails.toString(); // Return all queue details as a single string
    }

    // Formats a customer's details and their requested services into a readable string format
    private String displayCustomerWithServices(CustInfo customer) {
        StringBuilder customerDetails = new StringBuilder(); 

        // Show basic information about the customer: ID, Name, Plate Number
        customerDetails.append("Customer ID: ").append(customer.getID())
                       .append(", Name: ").append(customer.getName())
                       .append(", Plate Number: ").append(customer.getPlateNum()).append("\n");

        // Show requested services or indicate if none were requested 
        if (customer.getServiceList().isEmpty()) {
            customerDetails.append("  No services requested.\n"); 
        } else {
            customerDetails.append("  Services:\n"); 
            for (ServiceInfo service : customer.getServiceList()) {
                customerDetails.append("    - ").append(service.toString()).append("\n"); 
            }
        }

        return customerDetails.toString(); 
    }

    // Removes a specified customer from their respective lane using their ID 
    public void removeCustomerFromQueue(CustInfo cust) {
        // Try removing the customer from each lane by checking their ID against those in each queue 
        if (lane1Queue.removeIf(c -> c.getID().equals(cust.getID()))) {
            System.out.println("Customer with ID " + cust.getID() + " removed from Lane 1.");
        } else if (lane2Queue.removeIf(c -> c.getID().equals(cust.getID()))) {
            System.out.println("Customer with ID " + cust.getID() + " removed from Lane 2.");
        } else if (lane3Queue.removeIf(c -> c.getID().equals(cust.getID()))) {
            System.out.println("Customer with ID " + cust.getID() + " removed from Lane 3.");
        } else {
            System.out.println("Customer with ID " + cust.getID() + " not found in any lane."); 
            // If not found in any lanes, notify that removal was unsuccessful 
        }
    }

    // Reads and returns the entire content of a specified file as plain text 
    public String readFileContent(String filename) {
        StringBuilder fileContent = new StringBuilder(); 
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) { 
            String line;
            while ((line = br.readLine()) != null) { 
                fileContent.append(line).append("\n"); 
                // Append each line read from the file to fileContent 
            }
        } catch (IOException e) { 
            return "Error reading file: " + e.getMessage(); 
            // If an error occurs while reading, return an error message 
        }
        return fileContent.toString(); 
    }

    // Getters for accessing each queue externally, if needed by other classes or methods 
    public Queue<CustInfo> getLane1Queue() { 
        return lane1Queue; 
    }

    public Queue<CustInfo> getLane2Queue() { 
        return lane2Queue; 
    }

    public Queue<CustInfo> getLane3Queue() { 
        return lane3Queue; 
    }
}
