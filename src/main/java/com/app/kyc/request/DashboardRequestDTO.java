package com.app.kyc.request;

import java.util.Date;

import com.app.kyc.model.DashboardObject;
import com.fasterxml.jackson.annotation.JsonFormat;

public class DashboardRequestDTO
{
   boolean defaultResponse;

   @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
   Date startDate;
   @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
   Date endDate;

   DashboardObject industry;
   DashboardObject serviceProvider;
   DashboardObject serviceType;

   public DashboardRequestDTO()
   {
   }

   public DashboardRequestDTO(Date startDate, Date endDate, DashboardObject industry, DashboardObject serviceProvider, DashboardObject serviceType)
   {
      this.startDate = startDate;
      this.endDate = endDate;
      this.industry = industry;
      this.serviceProvider = serviceProvider;
      this.serviceType = serviceType;
   }

   public boolean isDefaultResponse()
   {
      return defaultResponse;
   }

   public void setDefaultResponse(boolean defaultResponse)
   {
      this.defaultResponse = defaultResponse;
   }

   public Date getStartDate()
   {
      return startDate;
   }

   public void setStartDate(Date startDate)
   {
      this.startDate = startDate;
   }

   public Date getEndDate()
   {
      return endDate;
   }

   public void setEndDate(Date endDate)
   {
      this.endDate = endDate;
   }

   public DashboardObject getIndustry()
   {
      return industry;
   }

   public void setIndustry(DashboardObject industry)
   {
      this.industry = industry;
   }

   public DashboardObject getServiceProvider()
   {
      return serviceProvider;
   }

   public void setServiceProvider(DashboardObject serviceProvider)
   {
      this.serviceProvider = serviceProvider;
   }

   public DashboardObject getServiceType()
   {
      return serviceType;
   }

   public void setServiceType(DashboardObject serviceType)
   {
      this.serviceType = serviceType;
   }

}