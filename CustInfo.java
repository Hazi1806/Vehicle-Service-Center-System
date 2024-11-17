import java.util.*;

public class CustInfo {
    // Attributes
    private String custID;
    private String custName;
    private String vehiclePlateNum;
    private final List<ServiceInfo> serviceReq; // List to store service requests for the customer
    
    // Constructor
    public CustInfo(String id, String name, String plateNum) {
        this.custID = id;
        this.custName = name;
        this.vehiclePlateNum = plateNum;
        this.serviceReq = new ArrayList<>(); // Using ArrayList for better performance
    }
    
    // Mutator methods
    public void setID(String id) {
        custID = id;
    }

    public void setName(String name) {
        custName = name;
    }

    public void setPlateNum(String plateNum) {
        vehiclePlateNum = plateNum;
    }
    
    // Accessor methods
    public String getID() {
        return custID;
    }

    public String getName() {
        return custName;        
    }

    public String getPlateNum() {
        return vehiclePlateNum;
    }

    public List<ServiceInfo> getServiceList() {
        return serviceReq;
    }

    // Method to add a service to the customer's list of services
    public void addService(ServiceInfo service) {
        serviceReq.add(service); // Add a single service to the service list
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

        return customerInfo.toString(); // Return full information about the customer and services
    }
}
