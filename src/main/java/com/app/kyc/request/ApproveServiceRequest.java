package com.app.kyc.request;

import javax.validation.constraints.NotNull;

import com.app.kyc.model.ServiceStatus;

public class ApproveServiceRequest
{
   @NotNull
   Long serviceId;

   @NotNull
   ServiceStatus status;

   Long userId;

   String rejectionReason;

   public ApproveServiceRequest(Long serviceId, ServiceStatus status, String rejectionReason, Long userId)
   {
      super();
      this.serviceId = serviceId;
      this.status = status;
      this.rejectionReason = rejectionReason;
      this.userId = userId;
   }

   public Long getServiceId()
   {
      return serviceId;
   }

   public void setServiceId(Long serviceId)
   {
      this.serviceId = serviceId;
   }

   public ServiceStatus getStatus()
   {
      return status;
   }

   public void setStatus(ServiceStatus status)
   {
      this.status = status;
   }

   public String getRejectionReason()
   {
      return rejectionReason;
   }

   public void setRejectionReason(String rejectionReason)
   {
      this.rejectionReason = rejectionReason;
   }

   public Long getUserId()
   {
      return userId;
   }

   public void setUserId(Long userId)
   {
      this.userId = userId;
   }

}
