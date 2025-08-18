package com.app.kyc.response;

import java.util.Date;

import com.app.kyc.model.AnomalyEntityType;
import com.app.kyc.model.AnomalyStatus;

public interface FlaggedConsumersListDTO
{
   public Long getId();

   public String getName();

   public String getServiceName();

   public Long getServiceProviderId();

   public String getServiceProviderName();

   public Date getFlaggedDate();

   public AnomalyEntityType getAnomalyEntityType();

   public AnomalyStatus getAnomalyStatus();

   public String getNote();

   public Long getAnomalyId();

   public String getReporterName();

}
