package com.app.kyc.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.app.kyc.model.AnomalyEntityType;
import com.app.kyc.model.AnomalySeverity;

@Entity
@Table(name = "anomaly_types")
public class AnomalyType
{
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   private String name;
   private Long entity_id;
   private AnomalySeverity severity;
   private AnomalyEntityType targetEntityType;
   private Long createdBy;
   private Date createdOn;



   private String description;

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

   public Long getEntity_id()
   {
      return entity_id;
   }

   public void setEntity_id(Long entity_id)
   {
      this.entity_id = entity_id;
   }

   public AnomalySeverity getSeverity()
   {
      return severity;
   }

   public void setSeverity(AnomalySeverity severity)
   {
      this.severity = severity;
   }

   public AnomalyEntityType getTargetEntityType()
   {
      return targetEntityType;
   }

   public void setTargetEntityType(AnomalyEntityType targetEntityType)
   {
      this.targetEntityType = targetEntityType;
   }

   public Long getCreatedBy()
   {
      return createdBy;
   }

   public void setCreatedBy(Long createdBy)
   {
      this.createdBy = createdBy;
   }

   public Date getCreatedOn()
   {
      return createdOn;
   }

   public void setCreatedOn(Date createdOn)
   {
      this.createdOn = createdOn;
   }
   public String getDescription() {
      return description;
   }

   public void setDescription(String description) {
      this.description = description;
   }

}
