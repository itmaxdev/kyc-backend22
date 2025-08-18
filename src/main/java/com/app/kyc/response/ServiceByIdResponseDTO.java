package com.app.kyc.response;

import com.app.kyc.entity.Service;
import com.app.kyc.entity.User;

public class ServiceByIdResponseDTO
{
   Service service;

   User approvedBy;

   User createdBy;

   User serviceProviderCreatedBy;

   User serviceTypeCreatedBy;

   public ServiceByIdResponseDTO(Service service, User approvedBy, User createdBy, User serviceProviderCreatedBy, User serviceTypeCreatedBy)
   {
      super();
      this.service = service;
      this.approvedBy = approvedBy;
      this.createdBy = createdBy;
      this.serviceProviderCreatedBy = serviceProviderCreatedBy;
      this.serviceTypeCreatedBy = serviceTypeCreatedBy;
   }

   public Service getService()
   {
      return service;
   }

   public void setService(Service service)
   {
      this.service = service;
   }

   public User getApprovedBy()
   {
      return approvedBy;
   }

   public void setApprovedBy(User approvedBy)
   {
      this.approvedBy = approvedBy;
   }

   public User getCreatedBy()
   {
      return createdBy;
   }

   public void setCreatedBy(User createdBy)
   {
      this.createdBy = createdBy;
   }

   public User getServiceProviderCreatedBy()
   {
      return serviceProviderCreatedBy;
   }

   public void setServiceProviderCreatedBy(User serviceProviderCreatedBy)
   {
      this.serviceProviderCreatedBy = serviceProviderCreatedBy;
   }

   public User getServiceTypeCreatedBy()
   {
      return serviceTypeCreatedBy;
   }

   public void setServiceTypeCreatedBy(User serviceTypeCreatedBy)
   {
      this.serviceTypeCreatedBy = serviceTypeCreatedBy;
   }

}
