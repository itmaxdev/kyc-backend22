package com.app.kyc.response;

public class AnomalyTypesListResponseDTO
{
   private Long id;

   private String name;

   private String anomalyArea;

   private String anomalyType;

   private String severityScore;

   public AnomalyTypesListResponseDTO(Long id, String name, String anomalyArea, String anomalyType, String severityScore)
   {
      super();
      this.id = id;
      this.name = name;
      this.anomalyArea = anomalyArea;
      this.anomalyType = anomalyType;
      this.severityScore = severityScore;
   }

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

   public String getAnomalyArea()
   {
      return anomalyArea;
   }

   public void setAnomalyArea(String anomalyArea)
   {
      this.anomalyArea = anomalyArea;
   }

   public String getAnomalyType()
   {
      return anomalyType;
   }

   public void setAnomalyType(String anomalyType)
   {
      this.anomalyType = anomalyType;
   }

   public String getSeverityScore()
   {
      return severityScore;
   }

   public void setSeverityScore(String severityScore)
   {
      this.severityScore = severityScore;
   }

}
