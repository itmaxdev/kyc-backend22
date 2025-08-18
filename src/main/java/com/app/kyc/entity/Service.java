package com.app.kyc.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.app.kyc.model.ServiceStatus;

@Entity
@Table(name = "services")
public class Service
{

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;
   private String name;
   private boolean Deleted;
   private Date createdOn;
   private Long createdBy;
   private Long approvedBy;
   private ServiceStatus status;
   private String rejectionReason;

   @ManyToOne
   private ServiceProvider serviceProvider;

   @ManyToOne
   private ServiceType serviceType;

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

   public boolean isDeleted()
   {
      return Deleted;
   }

   public void setDeleted(boolean deleted)
   {
      Deleted = deleted;
   }

   public Date getCreatedOn()
   {
      return createdOn;
   }

   public void setCreatedOn(Date createdOn)
   {
      this.createdOn = createdOn;
   }

   public Long getCreatedBy()
   {
      return createdBy;
   }

   public void setCreatedBy(Long createdBy)
   {
      this.createdBy = createdBy;
   }

   public Long getApprovedBy()
   {
      return approvedBy;
   }

   public void setApprovedBy(Long approvedBy)
   {
      this.approvedBy = approvedBy;
   }

   public ServiceStatus getStatus()
   {
      return status;
   }

   public void setStatus(ServiceStatus status)
   {
      this.status = status;
   }

   public ServiceProvider getServiceProvider()
   {
      return serviceProvider;
   }

   public void setServiceProvider(ServiceProvider serviceProvider)
   {
      this.serviceProvider = serviceProvider;
   }

   public ServiceType getServiceType()
   {
      return serviceType;
   }

   public void setServiceType(ServiceType serviceType)
   {
      this.serviceType = serviceType;
   }

   public String getRejectionReason()
   {
      return rejectionReason;
   }

   public void setRejectionReason(String rejectionReason)
   {
      this.rejectionReason = rejectionReason;
   }

   @Override
   public String toString()
   {
      return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
   }

}
