import java.util.*;

public class CustInfo {
    // Attributes
    private String custID;
    private String custName;
    private String vehiclePlateNum;
    private List<ServiceInfo> serviceReq; // List to store service requests for the customer
    
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
    
    // String representation of the customer information
    @Override
    public String toString() {
        return custID + "\t" + 
               custName + "\t" + 
               vehiclePlateNum + "\t";
    }
}
