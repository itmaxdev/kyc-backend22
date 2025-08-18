package com.app.kyc.response;

import java.util.Date;
import java.util.List;

import com.app.kyc.model.AnomlyDto;
import com.app.kyc.model.ConsumerDto;

public class ServiceProviderAllInfoResponseDTO
{
   Long id;

   String name;

   String industry;

   Date createdOn;

   List<ServiceUserResponseDTO> services;

   List<ConsumerDto> consumers;

   List<AnomlyDto> anomalies;

   public Long getId()
   {
      return id;
   }

   public void setId(Long id)
   {
      this.id = id;
   }

   public String getName()
   {
      return name;
   }

   public void setName(String name)
   {
      this.name = name;
   }

   public String getIndustry()
   {
      return industry;
   }

   public void setIndustry(String industry)
   {
      this.industry = industry;
   }

   public Date getCreatedOn()
   {
      return createdOn;
   }

   public void setCreatedOn(Date createdOn)
   {
      this.createdOn = createdOn;
   }

   public List<ServiceUserResponseDTO> getServices()
   {
      return services;
   }

   public void setServices(List<ServiceUserResponseDTO> services)
   {
      this.services = services;
   }

   public List<ConsumerDto> getConsumers()
   {
      return consumers;
   }

   public void setConsumers(List<ConsumerDto> consumers)
   {
      this.consumers = consumers;
   }

   public List<AnomlyDto> getAnomalies()
   {
      return anomalies;
   }

   public void setAnomalies(List<AnomlyDto> anomalies)
   {
      this.anomalies = anomalies;
   }

}
