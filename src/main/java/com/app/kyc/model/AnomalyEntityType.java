package com.app.kyc.model;

import java.util.stream.Stream;

public enum AnomalyEntityType {
   IncompleteData(0, "Incomplete Data"), DuplicateRecords(1, "Duplicate Records"), ExceedingThreshold(2, "Exceeding Threshold");

   private Integer code;
   private String anomalyEntityType;

   private AnomalyEntityType(Integer code, String anomalyEntityType)
   {
      this.code = code;
      this.anomalyEntityType = anomalyEntityType;
   }

   public Integer getCode()
   {
      return code;
   }

   public void setCode(Integer code)
   {
      this.code = code;
   }

   public String getAnomalyEntityType()
   {
      return anomalyEntityType;
   }

   public void setAnomalyEntityType(String anomalyEntityType)
   {
      this.anomalyEntityType = anomalyEntityType;
   }

   public static AnomalyEntityType valueOf(Integer code)
   {
      return Stream.of(AnomalyEntityType.values()).filter(anomalyEntityType -> anomalyEntityType.getCode() == code).findFirst().orElseThrow(IllegalArgumentException::new);
   }

}
