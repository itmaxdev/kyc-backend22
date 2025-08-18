package com.app.kyc.service;

import com.app.kyc.request.DashboardRequestDTO;
import com.app.kyc.response.DashboardResponseDTO;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface DashboardService
{
//   DashboardResponseDTO getDashboard(DashboardRequestDTO dashboardRequestDTO);
//   DahsboardHeaderListsResponse getHeaderLists();
   DashboardResponseDTO getDashboardV2(DashboardRequestDTO dashboardRequestDTO) throws JsonProcessingException;
   DashboardResponseDTO getAnomalyTimeSeries(DashboardRequestDTO dashboardRequestDTO);
}
