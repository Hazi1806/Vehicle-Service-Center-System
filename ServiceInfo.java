public class ServiceInfo {
    private final String serviceID;
    private final String serviceName;
    private final String serviceType;
    private final double serviceCost; 
    private final String serviceDate;
    private final String estimatedCompletionTime;

    // Constructor
    public ServiceInfo(String serviceID, String serviceName, String serviceType, double serviceCost, String serviceDate, String estimatedCompletionTime) {
        this.serviceID = serviceID;
        this.serviceName = serviceName;
        this.serviceType = serviceType;
        this.serviceCost = serviceCost; 
        this.serviceDate = serviceDate;
        this.estimatedCompletionTime = estimatedCompletionTime;
    }

    // Getters for service details
    public String getServiceID() {
        return serviceID;
    }

    public String getServiceType() {
        return serviceType;
    }

    public double getServiceCost() {
        return serviceCost; 
    }

    public String getServiceDate() {
        return serviceDate;
    }

    public String getEstimatedCompletionTime() {
        return estimatedCompletionTime;
    }

    @Override
    public String toString() {
        return "Service ID: " + serviceID +
               ", Type: " + serviceType +
               ", Cost: RM" + serviceCost +  
               ", Date of service: " + serviceDate +
               ", Estimated Completion: " + estimatedCompletionTime;
    }
}
