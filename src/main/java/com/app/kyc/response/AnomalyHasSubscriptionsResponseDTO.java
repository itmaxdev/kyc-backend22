package com.app.kyc.response;

import com.app.kyc.model.AnomlyDto;

public class AnomalyHasSubscriptionsResponseDTO {
    AnomlyDto anomlyDto;
    boolean hasSubscription;

    public AnomalyHasSubscriptionsResponseDTO(AnomlyDto anomlyDto, boolean hasSubscription)
    {
        this.anomlyDto = anomlyDto;
        this.hasSubscription = hasSubscription;
    }

    public AnomlyDto getAnomlyDto() {
        return anomlyDto;
    }

    public void setAnomlyDto(AnomlyDto anomlyDto) {
        this.anomlyDto = anomlyDto;
    }

    public boolean isHasSubscription() {
        return hasSubscription;
    }

    public void setHasSubscription(boolean hasSubscription) {
        this.hasSubscription = hasSubscription;
    }
}
