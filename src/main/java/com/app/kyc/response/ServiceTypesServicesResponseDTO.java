package com.app.kyc.response;

import java.util.List;

import com.app.kyc.entity.Service;
import com.app.kyc.entity.ServiceType;

public class ServiceTypesServicesResponseDTO
{
   private ServiceType serviceType;

   private String createdBy;

   private List<Service> associatedServices;

   private boolean hasLinkedServices;

   public ServiceTypesServicesResponseDTO(ServiceType serviceType, List<Service> associatedServices, boolean hasLinkedServices, String createdBy)
   {
      super();
      this.serviceType = serviceType;
      this.associatedServices = associatedServices;
      this.hasLinkedServices = hasLinkedServices;
      this.createdBy = createdBy;
   }

   public ServiceType getServiceType()
   {
      return serviceType;
   }

   public void setServiceType(ServiceType serviceType)
   {
      this.serviceType = serviceType;
   }

   public List<Service> getAssociatedServices()
   {
      return associatedServices;
   }

   public void setAssociatedServices(List<Service> associatedServices)
   {
      this.associatedServices = associatedServices;
   }

   public boolean isHasLinkedServices()
   {
      return hasLinkedServices;
   }

   public void setHasLinkedServices(boolean hasLinkedServices)
   {
      this.hasLinkedServices = hasLinkedServices;
   }

   public String getCreatedBy()
   {
      return createdBy;
   }

   public void setCreatedBy(String createdBy)
   {
      this.createdBy = createdBy;
   }

}
