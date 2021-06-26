package ir.maktab.homeservices.dto;

/**
 * @author Yeganeh Nobakht
 **/
public class ServiceSubService {
    private String serviceName;
    private String subServiceName;

    public ServiceSubService() {
    }

    public ServiceSubService(String serviceName, String subServiceName) {
        this.serviceName = serviceName;
        this.subServiceName = subServiceName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public ServiceSubService setServiceName(String serviceName) {
        this.serviceName = serviceName;
        return this;
    }

    public String getSubServiceName() {
        return subServiceName;
    }

    public ServiceSubService setSubServiceName(String subServiceName) {
        this.subServiceName = subServiceName;
        return this;
    }
}
