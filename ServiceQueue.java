import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ServiceQueue {
    private Queue<CustInfo> lane1Queue;
    private Queue<CustInfo> lane2Queue;
    private Queue<CustInfo> lane3Queue;

    // Constructor to initialize the queues for the three service lanes
    public ServiceQueue() {
        lane1Queue = new LinkedList<>();
        lane2Queue = new LinkedList<>();
        lane3Queue = new LinkedList<>();
    }
    public void loadCustomersFromFile(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] customerData = line.split(", "); // Assuming fields are separated by comma and space
    
                String customerID = customerData[0]; // Customer ID
                String customerName = customerData[1]; // Customer Name
                String carModel = customerData[2]; // Car Model
                List<ServiceInfo> requestedServices = new ArrayList<>(); // List to hold services
    
                // Parse the requested services
                int serviceIndex = 3;
                while (serviceIndex < customerData.length && !customerData[serviceIndex].matches("\\d+")) {
                    // Create ServiceInfo objects
                    String serviceID = customerData[serviceIndex];
                    String serviceName = customerData[serviceIndex + 1];
                    String serviceType = customerData[serviceIndex + 2];
                    
                    // Parse service cost as double instead of string
                    double serviceCost = Double.parseDouble(customerData[serviceIndex + 3]); // Convert String to double
                    
                    String serviceDate = customerData[serviceIndex + 4];
                    String estimatedCompletionTime = customerData[serviceIndex + 5];
                    
                    // Create the ServiceInfo object with the parsed cost
                    ServiceInfo service = new ServiceInfo(serviceID, serviceName, serviceType, serviceCost, serviceDate, estimatedCompletionTime);
                    requestedServices.add(service);
                    serviceIndex += 6; // Move to the next service data
                }
    
                // Get the phone number, service date, and estimated time
                String phoneNumber = customerData[serviceIndex++];
                String serviceDate = customerData[serviceIndex++];
                String serviceTime = customerData[serviceIndex];
    
                // Create the CustInfo object
                CustInfo customer = new CustInfo(customerID, customerName, carModel);
                
                // Add each service to the customer individually
                for (ServiceInfo service : requestedServices) {
                    customer.addService(service); // Add each service to the customer's service list
                }
    
                // Assign customer to an appropriate service lane
                assignCustomerToLane(customer);
            }
        } catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage());
        }
    }
    
    // Check if the file path is valid
    private boolean isFileValid(String filename) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            br.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    // Assigns a customer to an appropriate service lane
    public void assignCustomerToLane(CustInfo cust) {
        int numberOfServices = cust.getServiceList().size(); 
        boolean isCustomerOdd = Integer.parseInt(cust.getID()) % 2 != 0;

        if (numberOfServices > 3) {
            lane3Queue.add(cust); 
        } else {
            if (isCustomerOdd) {
                if (lane1Queue.size() <= lane2Queue.size()) {
                    lane1Queue.add(cust); 
                } else {
                    lane2Queue.add(cust); 
                }
            } else {
                if (lane2Queue.size() <= lane1Queue.size()) {
                    lane2Queue.add(cust); 
                } else {
                    lane1Queue.add(cust); 
                }
            }
        }
    }
    
    // Remove customer from the queue based on customer ID
    public void removeCustomerFromQueue(CustInfo cust) {
        // Try to remove from lane 1, lane 2, or lane 3
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

    // Displays the queue details of all the service lanes (Lane 1, Lane 2, and Lane 3)
    public void displayQueueInfo() {
        System.out.println("Lane 1 Queue:");
        displayQueueDetails(lane1Queue); // Display details of customers in lane 1

        System.out.println("\nLane 2 Queue:");
        displayQueueDetails(lane2Queue); // Display details of customers in lane 2

        System.out.println("\nLane 3 Queue:");
        displayQueueDetails(lane3Queue); // Display details of customers in lane 3
    }

    // Helper method to display detailed information about customers in a given queue
    private void displayQueueDetails(Queue<CustInfo> queue) {
        for (CustInfo customer : queue) {
            System.out.println("Customer ID: " + customer.getID() + " - " + customer.getName());
            System.out.println("Requested Services: ");
            
            // Display the services requested by the customer
            for (ServiceInfo service : customer.getServiceList()) {
                System.out.println("\t" + service); // Display each service
            }
    
            // Calculate the total cost of the services requested by the customer
            double totalCost = customer.getServiceList().stream().mapToDouble(ServiceInfo::getServiceCost).sum();
            System.out.println("Total Service Cost: RM" + totalCost); // Display the total cost
        }
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
