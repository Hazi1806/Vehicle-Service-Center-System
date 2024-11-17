public class ServiceInfo {
    private final String serviceID;
    private final String serviceName;
    private final String serviceType;
    private final double serviceCost;
    private final String serviceDate;
    private final String estimatedCompletionTime;

    // Constructor to handle optional fields
    public ServiceInfo(String serviceID, String serviceName, String serviceType, double serviceCost, 
                       String serviceDate, String estimatedCompletionTime) {
        this.serviceID = serviceID != null ? serviceID : "N/A";  // Default value
        this.serviceName = serviceName != null ? serviceName : "Unknown Service";
        this.serviceType = serviceType != null ? serviceType : "General";
        this.serviceCost = serviceCost >= 0 ? serviceCost : 0.0; // Default value
        this.serviceDate = serviceDate != null ? serviceDate : "Unknown Date";
        this.estimatedCompletionTime = estimatedCompletionTime != null ? estimatedCompletionTime : "TBD";
    }

    // Getters
    public String getServiceID() { return serviceID; }
    public String getServiceName() { return serviceName; }
    public String getServiceType() { return serviceType; }
    public double getServiceCost() { return serviceCost; }
    public String getServiceDate() { return serviceDate; }
    public String getEstimatedCompletionTime() { return estimatedCompletionTime; }

    @Override
    public String toString() {
        return "Service ID: " + serviceID +
               ", Name: " + serviceName +
               ", Type: " + serviceType +
               ", Cost: RM" + serviceCost +
               ", Date: " + serviceDate +
               ", Estimated Completion: " + estimatedCompletionTime;
    }
}
