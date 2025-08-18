package com.app.kyc.response;

import java.util.List;

import com.app.kyc.model.AnomalyTrackingDto;
import com.app.kyc.model.AnomlyDto;

public class AnomalyDetailsResponseDTO
{
   AnomlyDto anomalyDto;

   List<AnomalyTrackingDto> anomalyTrackingDto;

   public AnomalyDetailsResponseDTO(AnomlyDto anomalyDto, List<AnomalyTrackingDto> anomalyTrackingDto)
   {
      this.anomalyDto = anomalyDto;
      this.anomalyTrackingDto = anomalyTrackingDto;
   }

   public AnomlyDto getAnomaly()
   {
      return anomalyDto;
   }

   public void setAnomaly(AnomlyDto anomaly)
   {
      this.anomalyDto = anomaly;
   }

   public List<AnomalyTrackingDto> getAnomalyTrackingDto() {
      return anomalyTrackingDto;
   }

   public void setAnomalyTrackingDto(List<AnomalyTrackingDto> anomalyTrackingDto) {
      this.anomalyTrackingDto = anomalyTrackingDto;
   }
}
