package com.app.kyc.response;

import java.util.Date;

import com.app.kyc.entity.User;

public class ServiceProviderUserResponseDTO
{
   Long Id;

   String serviceProviderName;

   Date createdOn;

   String industryName;

   User createdBy;

   boolean hasServices;

   String color;

   public ServiceProviderUserResponseDTO(Long id, String serviceProviderName, Date createdOn, String industryName, User createdBy, boolean hasServices, String color)
   {
      this.Id = id;
      this.serviceProviderName = serviceProviderName;
      this.createdOn = createdOn;
      this.industryName = industryName;
      this.createdBy = createdBy;
      this.hasServices = hasServices;
      this.color = color;
   }

   public Long getId()
   {
      return Id;
   }

   public void setId(Long id)
   {
      Id = id;
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

   public String getIndustryName()
   {
      return industryName;
   }

   public void setIndustryName(String industryName)
   {
      this.industryName = industryName;
   }

   public User getCreatedBy()
   {
      return createdBy;
   }

   public void setCreatedBy(User createdBy)
   {
      this.createdBy = createdBy;
   }

   public boolean isHasServices()
   {
      return hasServices;
   }

   public void setHasServices(boolean hasServices)
   {
      this.hasServices = hasServices;
   }

   public String getColor() { return color; }

   public void setColor(String color) { this.color = color; }

}
