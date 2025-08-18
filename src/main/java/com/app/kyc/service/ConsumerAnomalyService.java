package com.app.kyc.service;

import com.app.kyc.model.DashboardAnomalyStatusInterface;

import java.util.Date;
import java.util.List;

public interface ConsumerAnomalyService {
    List<DashboardAnomalyStatusInterface> countAnomaliesByServiceProviderAndAnomalyStatus(Date startDate, Date endDate,List<Long> serviceProviderIds);

}
