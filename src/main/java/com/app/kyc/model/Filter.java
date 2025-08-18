package com.app.kyc.model;

public class Filter {
    Boolean consistent;
    Long serviceProviderID;
    Boolean isResolved;

    public Boolean getConsistent() {
        return consistent;
    }

    public void setConsistent(Boolean consistent) {
        this.consistent = consistent;
    }


    public Long getServiceProviderID() {
        return serviceProviderID;
    }

    public void setServiceProviderID(Long serviceProviderID) {
        this.serviceProviderID = serviceProviderID;
    }


    public Boolean getIsResolved() { return isResolved; }

    public void setIsResolved(Boolean isResolved) { this.isResolved = isResolved; }
}
