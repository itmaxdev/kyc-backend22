package com.app.kyc.model;

public interface DashboardAnomalyStatusInterface {
    Integer getAnomalyCount();
    AnomalyStatus getStatus();
    String getName();
    Long getServiceProviderId();
}
