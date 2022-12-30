package org.jannsen.mcreverse.api.entity.restaurant;

public class Services {

    private String endTime, serviceName, startTime;
    private boolean isOpen;

    public String getEndTime() {
        return endTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getServiceName() {
        return serviceName;
    }

    public boolean isOpen() {
        return isOpen;
    }
}
