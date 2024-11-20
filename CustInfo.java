import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CustInfo {
    // Attributes
    private final String custID;          // Customer ID
    private final String custName;        // Customer name
    private final String vehiclePlateNum; // Vehicle plate number
    private final List<ServiceInfo> serviceReq; // List to store service requests for the customer

    // Constructor
    public CustInfo(String id, String name, String plateNum) {
        this.custID = id != null ? id : "Unknown ID";     // Ensure non-null ID
        this.custName = name != null ? name : "Unknown Name";   // Ensure non-null name
        this.vehiclePlateNum = plateNum != null ? plateNum : "Unknown Plate"; // Ensure non-null plate number
        this.serviceReq = new ArrayList<>();      // Initialize the service request list
    }

    // Accessor (Getter) methods
    // Retrieves the customer ID
    public String getID() {
        return custID;
    }
    //Retrieves the customer name
    public String getName() {
        return custName;
    }
    //Retrieves the vehicle plate number
    public String getPlateNum() {
        return vehiclePlateNum;
    }
    //Retrieves unmodifiable list of service requests for the customer
    public List<ServiceInfo> getServiceList() {
        return Collections.unmodifiableList(serviceReq); // Return unmodifiable list for safety
    }

    // Add this method for backward compatibility
    public List<ServiceInfo> getServices() {
        return getServiceList();
    }

    // Mutator methods
    //Add a service request to the customer's service list.
    public void addService(ServiceInfo service) {
        if (service != null) {
            serviceReq.add(service); // Add a single service to the service list
        } else {
            throw new IllegalArgumentException("Service cannot be null.");
        }
    }

    // String representation of the customer information including service details
    @Override
    public String toString() {
        StringBuilder customerInfo = new StringBuilder();
        customerInfo.append("Customer ID: ").append(custID).append("\n");
        customerInfo.append("Customer Name: ").append(custName).append("\n");
        customerInfo.append("Vehicle Plate Number: ").append(vehiclePlateNum).append("\n");

        if (serviceReq.isEmpty()) {
            customerInfo.append("No services requested.\n");
        } else {
            customerInfo.append("Requested Services:\n");
            for (ServiceInfo service : serviceReq) {
                customerInfo.append("\t").append(service.toString()).append("\n");
            }
        }

        return customerInfo.toString(); // Return the complete customer information as a string
    }
}
