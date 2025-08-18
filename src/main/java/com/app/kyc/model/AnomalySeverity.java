package com.app.kyc.model;

public enum AnomalySeverity {

   MINOR(1, "Minor"), WARNING(2, "Warning"), SERIOUS(3, "Serious"), CRITICAL(4, "Critical"), SEVERE(5, "Severe");

   private Integer code;
   private String severity;

   AnomalySeverity(Integer code, String severity)
   {
      this.code = code;
      this.severity = severity;
   }

   public Integer getCode()
   {
      return code;
   }

   public void setCode(Integer code)
   {
      this.code = code;
   }

   public String getSeverity()
   {
      return severity;
   }

   public void setSevirity(String severity)
   {
      this.severity = severity;
   }
}
