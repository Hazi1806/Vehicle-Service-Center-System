import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ServiceQueue {
    // Queues for the three service lanes
    private final Queue<CustInfo> lane1Queue;
    private final Queue<CustInfo> lane2Queue;
    private final Queue<CustInfo> lane3Queue;

    // Constructor to initialize the queues for the service lanes
    public ServiceQueue() {
        lane1Queue = new LinkedList<>();
        lane2Queue = new LinkedList<>();
        lane3Queue = new LinkedList<>();
    }

    // Custom enqueue method, assigns customer to a lane based on their requested services
    public void enqueue(CustInfo cust) {
        assignCustomerToLane(cust);
    }

    // Method to load customer data from a file and assign them to lanes
    public void loadCustomersFromFile(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] customerData = line.split(",\\s*"); // Split data by comma and optional space

                if (customerData.length < 3) {
                    System.out.println("Skipping invalid line: " + line);
                    continue; // Skip invalid lines
                }

                String customerID = customerData[0];
                String customerName = customerData[1];
                String carModel = customerData[2];
                List<ServiceInfo> requestedServices = new ArrayList<>();

                // Parse services requested by the customer
                int serviceStartIndex = 3; // Index of the first service
                while (serviceStartIndex < customerData.length) {
                    String serviceName = customerData[serviceStartIndex];
                    String serviceDate = (serviceStartIndex + 1) < customerData.length ? customerData[serviceStartIndex + 1] : null;

                    ServiceInfo service = new ServiceInfo(null, serviceName, null, 0.0, serviceDate, null);
                    requestedServices.add(service);
                    serviceStartIndex += 2; // Move to the next service pair
                }

                // Create customer object and add their services
                CustInfo customer = new CustInfo(customerID, customerName, carModel);
                for (ServiceInfo service : requestedServices) {
                    customer.addService(service);
                }

                // Assign customer to an appropriate service lane based on services requested
                assignCustomerToLane(customer);
            }
        } catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage());
        }
    }

    // Assigns a customer to a service lane based on the number of services they requested
    public void assignCustomerToLane(CustInfo cust) {
        int numberOfServices = cust.getServiceList().size(); // Number of services requested

        // Logic to assign customer to a lane
        if (numberOfServices == 3) {
            lane3Queue.add(cust); // Lane 3 for customers with 3 services
        } else if (numberOfServices % 2 == 0) {
            lane2Queue.add(cust); // Lane 2 for customers with an even number of services
        } else {
            lane1Queue.add(cust); // Lane 1 for customers with an odd number of services
        }
    }

    // Displays the details of the customers in each service lane
    public String displayQueueDetailsByID() {
        StringBuilder queueDetails = new StringBuilder();

        // Display details for Lane 1
        queueDetails.append("Lane 1 Queue:\n");
        for (CustInfo customer : lane1Queue) {
            queueDetails.append(displayCustomerWithServices(customer));
        }

        // Display details for Lane 2
        queueDetails.append("\nLane 2 Queue:\n");
        for (CustInfo customer : lane2Queue) {
            queueDetails.append(displayCustomerWithServices(customer));
        }

        // Display details for Lane 3
        queueDetails.append("\nLane 3 Queue:\n");
        for (CustInfo customer : lane3Queue) {
            queueDetails.append(displayCustomerWithServices(customer));
        }

        return queueDetails.toString();
    }

    // Helper method to display a customer's details and their requested services
    private String displayCustomerWithServices(CustInfo customer) {
        StringBuilder customerDetails = new StringBuilder();

        // Display customer details
        customerDetails.append("Customer ID: ").append(customer.getID())
            .append(", Name: ").append(customer.getName())
            .append(", Plate Number: ").append(customer.getPlateNum()).append("\n");

        // Display the services the customer requested
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

    // Removes a customer from any service lane based on their ID
    public void removeCustomerFromQueue(CustInfo cust) {
        // Try to remove customer from each lane and print a message
        if (lane1Queue.removeIf(c -> c.getID().equals(cust.getID()))) {
            System.out.println("Customer with ID " + cust.getID() + " removed from Lane 1.");
        } else if (lane2Queue.removeIf(c -> c.getID().equals(cust.getID()))) {
            System.out.println("Customer with ID " + cust.getID() + " removed from Lane 2.");
        } else if (lane3Queue.removeIf(c -> c.getID().equals(cust.getID()))) {
            System.out.println("Customer with ID " + cust.getID() + " removed from Lane 3.");
        } else {
            System.out.println("Customer with ID " + cust.getID() + " not found in any lane.");
        }
    }

    // Reads the content of a file and returns it as a string
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

    // Getter method for Lane 1 queue
    public Queue<CustInfo> getLane1Queue() {
        return lane1Queue;
    }

    // Getter method for Lane 2 queue
    public Queue<CustInfo> getLane2Queue() {
        return lane2Queue;
    }

    // Getter method for Lane 3 queue
    public Queue<CustInfo> getLane3Queue() {
        return lane3Queue;
    }
}
