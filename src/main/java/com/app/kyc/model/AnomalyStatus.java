package com.app.kyc.model;

public enum AnomalyStatus {

   REPORTED(0, "Reported"), UNDER_INVESTIGATION(1, "In Progress"), QUESTION_SUBMITTED(2, "Question Submitted"), QUESTION_ANSWERED(3,
      "Question Answered"), RESOLUTION_SUBMITTED(4, "Resolution Submitted"), RESOLVED_SUCCESSFULLY(5, "Resolved Successfully"), WITHDRAWN(6, "Withdrawn");

   private Integer code;
   private String status;

   AnomalyStatus(Integer code, String status)
   {
      this.code = code;
      this.status = status;
   }

   public Integer getCode()
   {
      return code;
   }

   public void setCode(Integer code)
   {
      this.code = code;
   }

   public String getStatus()
   {
      return status;
   }

   public void setStatus(String status)
   {
      this.status = status;
   }

}
