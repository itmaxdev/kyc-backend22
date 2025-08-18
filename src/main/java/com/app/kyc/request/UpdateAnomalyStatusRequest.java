package com.app.kyc.request;

import com.app.kyc.model.AnomalyStatus;

public class UpdateAnomalyStatusRequest
{
   Long anomalyId;
   AnomalyStatus status;
   String note;

   public Long getAnomalyId()
   {
      return anomalyId;
   }

   public void setAnomalyId(Long anomalyId)
   {
      this.anomalyId = anomalyId;
   }

   public AnomalyStatus getStatus()
   {
      return status;
   }

   public void setStatus(AnomalyStatus status)
   {
      this.status = status;
   }

   public String getNote()
   {
      return note;
   }

   public void setNote(String note)
   {
      this.note = note;
   }

}
