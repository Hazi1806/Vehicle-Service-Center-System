import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ServiceQueue {
    // Three service lanes (each has a queue for customers)
    private final Queue<CustInfo> lane1Queue;
    private final Queue<CustInfo> lane2Queue;
    private final Queue<CustInfo> lane3Queue;

    // Counter to alternate between Lane 1 and Lane 2 for customers with 3 or fewer services
    private int lane1And2Counter;
    

    // Constructor to initialize the three queues
    public ServiceQueue() {
        lane1Queue = new LinkedList<>();
        lane2Queue = new LinkedList<>();
        lane3Queue = new LinkedList<>();
        lane1And2Counter = 0; // Start with Lane 1 for the first customer
    }

    // Adds a customer to the appropriate lane based on their requested services
    public void enqueue(CustInfo cust) {
        assignCustomerToLane(cust); // Decide which lane to place the customer in
    }

    // Reads customer data from a file and assigns them to lanes
    public void loadCustomersFromFile(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;

            // Process each line in the file
            while ((line = br.readLine()) != null) {
                // Split the line into parts: ID, name, and services
                String[] customerData = line.split(",\\s*");

            // Skip lines with incomplete or invalid data
            if (customerData.length < 2) {
                System.out.println("Skipping invalid line: " + line);
                continue;
        }

            // Get the customer's ID and name
            String customerID = customerData[0];
            String customerName = customerData[1];
            List<ServiceInfo> requestedServices = new ArrayList<>();

            // Read services from the remaining parts of the line
            int serviceStartIndex = 2;
            while (serviceStartIndex < customerData.length) {
                String serviceName = customerData[serviceStartIndex];
                String serviceDate = (serviceStartIndex + 1) < customerData.length ? customerData[serviceStartIndex + 1] : null;

            // Create a service object and add it to the list
            ServiceInfo service = new ServiceInfo(null, serviceName, null, 0.0, serviceDate, null);
            requestedServices.add(service);
            serviceStartIndex += 2; // Move to the next service pair
        }

            // Create a customer object and assign their services
            CustInfo customer = new CustInfo(customerID, customerName, "Unknown Plate");
            for (ServiceInfo service : requestedServices) {
                customer.addService(service);
        }

                // Assign the customer to the correct lane
                assignCustomerToLane(customer);
            }
        } catch (IOException e) {
            // Print an error message if file reading fails
            System.out.println("Error reading the file: " + e.getMessage());
        }
    }

    // Decides which lane a customer should go to, based on the number of services
    private void assignCustomerToLane(CustInfo cust) {
        int numberOfServices = cust.getServiceList().size();
    
        if (numberOfServices > 3) {
            lane3Queue.add(cust); // Lane 3 is for more than 3 services
        } else {
            if (lane1And2Counter % 2 == 0) {
                lane1Queue.add(cust); // Alternate between Lane 1 and Lane 2 for 3 or fewer services
            } else {
                lane2Queue.add(cust);
            }
            lane1And2Counter++; // Increment the counter for alternation
        }
    }
    

    // Shows all customers in each lane with their details and requested services
    public String displayQueueDetailsByID() {
        StringBuilder queueDetails = new StringBuilder();

        // Add Lane 1 details
        queueDetails.append("Lane 1 Queue:\n");
        for (CustInfo customer : lane1Queue) {
            queueDetails.append(displayCustomerWithServices(customer));
        }

        // Add Lane 2 details
        queueDetails.append("\nLane 2 Queue:\n");
        for (CustInfo customer : lane2Queue) {
            queueDetails.append(displayCustomerWithServices(customer));
        }

        // Add Lane 3 details
        queueDetails.append("\nLane 3 Queue:\n");
        for (CustInfo customer : lane3Queue) {
            queueDetails.append(displayCustomerWithServices(customer));
        }

        return queueDetails.toString();
    }

    // Formats a customer's details and their requested services
    private String displayCustomerWithServices(CustInfo customer) {
        StringBuilder customerDetails = new StringBuilder();

        // Show the basic customer info
        customerDetails.append("Customer ID: ").append(customer.getID())
                .append(", Name: ").append(customer.getName())
                .append(", Plate Number: ").append(customer.getPlateNum()).append("\n");

        // Show the services requested (if any)
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

    // Removes a customer from their lane using their ID
    public void removeCustomerFromQueue(CustInfo cust) {
        // Try removing the customer from each lane
        if (lane1Queue.removeIf(c -> c.getID().equals(cust.getID()))) {
            System.out.println("Customer with ID " + cust.getID() + " removed from Lane 1.");
        } else if (lane2Queue.removeIf(c -> c.getID().equals(cust.getID()))) {
            System.out.println("Customer with ID " + cust.getID() + " removed from Lane 2.");
        } else if (lane3Queue.removeIf(c -> c.getID().equals(cust.getID()))) {
            System.out.println("Customer with ID " + cust.getID() + " removed from Lane 3.");
        } else {
            // Customer not found in any lane
            System.out.println("Customer with ID " + cust.getID() + " not found in any lane.");
        }
    }

    // Reads and returns the content of a file as plain text
    public String readFileContent(String filename) {
        StringBuilder fileContent = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                fileContent.append(line).append("\n");
            }
        } catch (IOException e) {
            return "Error reading file: " + e.getMessage();
        }
        return fileContent.toString();
    }

    // Getters for the queues (for external access, if needed)
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
