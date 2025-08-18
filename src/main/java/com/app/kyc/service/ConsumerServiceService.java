package com.app.kyc.service;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.app.kyc.entity.ConsumerService;
import com.app.kyc.model.DashboardObjectInterface;


public interface ConsumerServiceService
{
   void addConsumerService(ConsumerService consumerService);

   

   List<List<String>> loadAirtelConsumers();

   Map<String, Object> getAllConsumerServices(Long consumerId);
   
   ConsumerService getConsumerServiceById(Long consumerServiceId);

   List<ConsumerService> getConsumerServiceByCreatedOnGreaterThanAndCreatedOnLessThanEqual(Long industryId, Date startDate, Date endDate);

   int countConsumerServicesByIndustryId(Long industryId, Date start, Date end);

   int countConsumerServicesByIndustryIdBetweenDates(List<Long> serviceProviderIds, Date start, Date end);

   int countConsumersByServiceTypeId(Long serviceTypeId, Date start, Date end);

   int countConsumersByConsumerId(Long consumerId);

   int countConsumersByServiceProviderIdAndServiceTypeId(Long serviceProviderId, Long serviceTypeId, Date start, Date end);

   int countConsumersServicesByServiceProviderIdAndServiceTypeId(Long serviceProviderId, Long serviceTypeId, Date start, Date end);

   int countConsumersServicesByServiceTypeId(Long serviceTypeId, Date start, Date end);

   List<ConsumerService> getConsumerServiceByServiceTypeId(Long serviceTypeId, Date start, Date end);

   List<ConsumerService> getConsumerServiceByServiceProviderId(Long serviceProviderId, Date start, Date end);

   List<ConsumerService> getConsumerServiceByServiceProviderIdAndServiceTypeId(Long serviceProviderId, Long serviceTypeId, Date start, Date end);

   int countConsumersByServiceId(Long serviceId);

   List<DashboardObjectInterface> countConsumerServicesGroupedByServiceProviderId(List<Long> serviceProvidersIdList, Date startDate, Date endDate);

}
