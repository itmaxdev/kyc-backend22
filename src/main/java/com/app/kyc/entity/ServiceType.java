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

@Entity
@Table(name = "service_types")
public class ServiceType
{

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;
   private String name;
   private boolean Deleted;
   private Date createdOn;
   private Long createdBy;

   @ManyToOne
   private Industry industry;

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

   public Industry getIndustry()
   {
      return industry;
   }

   public void setIndustry(Industry industry)
   {
      this.industry = industry;
   }

   @Override
   public String toString()
   {
      return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
   }

}
