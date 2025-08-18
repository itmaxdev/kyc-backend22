package com.app.kyc.model;

public enum NotificationType {

   ANOMALY_REPORTED(0, "Anomaly Reported");

   private Integer code;
   private String message;

   NotificationType(Integer code, String message)
   {
      this.code = code;
      this.message = message;
   }

   public Integer getCode()
   {
      return code;
   }

   public void setCode(Integer code)
   {
      this.code = code;
   }

   public String getMessage()
   {
      return message;
   }

   public void setMessage(String message)
   {
      this.message = message;
   }
}
