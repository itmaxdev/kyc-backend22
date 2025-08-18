package com.app.kyc.service;

import com.app.kyc.model.DashboardAnomalyStatusInterface;
import com.app.kyc.repository.ConsumerAnomalyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ConsumerAnomalyServiceImpl implements ConsumerAnomalyService{
    @Autowired
    ConsumerAnomalyRepository consumerAnomalyRepository;

    @Override
    public List<DashboardAnomalyStatusInterface> countAnomaliesByServiceProviderAndAnomalyStatus(Date startDate, Date endDate, List<Long> serviceProviderIds) {
        return consumerAnomalyRepository.countAnomaliesByServiceProviderAndAnomalyStatus(startDate, endDate, serviceProviderIds);
    }
}
