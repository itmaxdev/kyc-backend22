package com.app.kyc.model;

import java.util.Objects;

public class ExceedingConsumers {
    private String identificationType;
    private String identificationNumber;
    private String serviceProviderName;

    public String getIdentificationType() {
        return identificationType;
    }

    public void setIdentificationType(String identificationType) {
        this.identificationType = identificationType;
    }

    public String getIdentificationNumber() {
        return identificationNumber;
    }

    public void setIdentificationNumber(String identificationNumber) {
        this.identificationNumber = identificationNumber;
    }

    public String getServiceProviderName() {
        return serviceProviderName;
    }

    public void setServiceProviderName(String serviceProviderName) {
        this.serviceProviderName = serviceProviderName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExceedingConsumers that = (ExceedingConsumers) o;
        return identificationType.equals(that.identificationType) && identificationNumber.equals(that.identificationNumber) && serviceProviderName.equals(that.serviceProviderName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identificationType, identificationNumber, serviceProviderName);
    }
}
