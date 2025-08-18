package com.app.kyc.response;

import java.util.Date;

import com.app.kyc.entity.ServiceType;
import com.app.kyc.entity.User;

public class ServiceUserResponseDTO
{
   Long id;

   String serviceName;

   String serviceProviderName;

   Date createdOn;

   ServiceType serviceType;

   User createdBy;

   User approvedBy;

   boolean hasConsumers;

   public ServiceUserResponseDTO(Long id, String serviceName, String serviceProviderName, Date createdOn, ServiceType serviceType, User createdBy, User approvedBy,
                                 boolean hasConsumers)
   {
      this.id = id;
      this.serviceName = serviceName;
      this.serviceProviderName = serviceProviderName;
      this.createdOn = createdOn;
      this.serviceType = serviceType;
      this.createdBy = createdBy;
      this.approvedBy = approvedBy;
      this.hasConsumers = hasConsumers;
   }

   public Long getId()
   {
      return id;
   }

   public void setId(Long id)
   {
      this.id = id;
   }

   public String getServiceName()
   {
      return serviceName;
   }

   public void setServiceName(String serviceName)
   {
      this.serviceName = serviceName;
   }

   public String getServiceProviderName()
   {
      return serviceProviderName;
   }

   public void setServiceProviderName(String serviceProviderName)
   {
      this.serviceProviderName = serviceProviderName;
   }

   public Date getCreatedOn()
   {
      return createdOn;
   }

   public void setCreatedOn(Date createdOn)
   {
      this.createdOn = createdOn;
   }

   public ServiceType getServiceType()
   {
      return serviceType;
   }

   public void setServiceType(ServiceType serviceType)
   {
      this.serviceType = serviceType;
   }

   public User getCreatedBy()
   {
      return createdBy;
   }

   public void setCreatedBy(User createdBy)
   {
      this.createdBy = createdBy;
   }

   public User getApprovedBy()
   {
      return approvedBy;
   }

   public void setApprovedBy(User approvedBy)
   {
      this.approvedBy = approvedBy;
   }

   public boolean isHasConsumers()
   {
      return hasConsumers;
   }

   public void setHasConsumers(boolean hasConsumers)
   {
      this.hasConsumers = hasConsumers;
   }

}
