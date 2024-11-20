public class ServiceInfo {
    private final String serviceName;
    private final double serviceCost;
    private final String serviceDate;
    private final String estimatedCompletionTime;

    // Constructor to handle optional fields
    // Initializes a new ServiceInfo object with provided details
    public ServiceInfo(String serviceName, double serviceCost, String serviceDate, String estimatedCompletionTime) {
        this.serviceName = serviceName != null ? serviceName : "Unknown Service";   // Default value for service name
        this.serviceCost = serviceCost >= 0 ? serviceCost : 0.0; // Make sure non negative for dervice cost
        this.serviceDate = serviceDate != null ? serviceDate : "Unknown Date";  // Default value for service date
        this.estimatedCompletionTime = estimatedCompletionTime != null ? estimatedCompletionTime : "TBD";   // Default value for completion time
    }

    // Getters
    // Retrieves the service ID
    
    // Retrieves the name of service
    public String getServiceName() { 
        return serviceName; 
    }
    
    // Retrieves the service cost
    public double getServiceCost() { 
        return serviceCost; 
    }
    // Retrieves the date of the service 
    public String getServiceDate() { 
        return serviceDate; 
    }
    // Retrieves the estimated time for completed service
    public String getEstimatedCompletionTime() { 
        return estimatedCompletionTime; 
    }
}
