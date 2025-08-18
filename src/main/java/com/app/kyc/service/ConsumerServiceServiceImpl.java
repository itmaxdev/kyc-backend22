package com.app.kyc.service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.kyc.entity.ConsumerService;
import com.app.kyc.model.DashboardObjectInterface;
import com.app.kyc.repository.ConsumerServiceRepository;




// import com.opencsv.*;

import java.util.*;


@Service
public class ConsumerServiceServiceImpl implements ConsumerServiceService
{
   
   
   @Autowired
   ConsumerServiceRepository consumerServiceRepository;
   
   @Autowired
   private EntityManager entityManager;

   
   public void addConsumerService(ConsumerService consumerService)
   {
      consumerServiceRepository.save(consumerService);
   }
   
   
   @Override
   public ConsumerService getConsumerServiceById(Long consumerServiceId)
   {
      return consumerServiceRepository.findById(consumerServiceId).get();
   }
   
   @Override
   public Map<String, Object> getAllConsumerServices(Long consumerId)
   {
      CriteriaBuilder cb = entityManager.getCriteriaBuilder();
      CriteriaQuery<ConsumerService> query = cb.createQuery(ConsumerService.class);
      
      List<Predicate> predicates = new ArrayList<>();
      
      Root<ConsumerService> root = query.from(ConsumerService.class);
      
      Predicate consumerIdPredicate = cb.equal(root.get("consumer"), consumerId);
      predicates.add(consumerIdPredicate);
      query.select(root).where(predicates.toArray(new Predicate[predicates.size()]));
      TypedQuery<ConsumerService> typedQuery = entityManager.createQuery(query);
      
      List<ConsumerService> response = typedQuery.getResultList();
      entityManager.close();
      Map<String, Object> servicesWithCount = new HashMap<String, Object>();
      servicesWithCount.put("data", response);
      servicesWithCount.put("count", response.size());
      return servicesWithCount;
   }
   
   @Override
   public int countConsumerServicesByIndustryId(Long industryId, Date start, Date end)
   {
      return (int) consumerServiceRepository.countByIndustryIdAndCreatedOnGreaterThanAndCreatedOnLessThanEqual(industryId, start, end);
   }

   @Override
   public int countConsumerServicesByIndustryIdBetweenDates(List<Long> serviceProviderIds, Date start, Date end)
   {
      return (int) consumerServiceRepository.countByConsumer_ServiceProvider_IdInAndCreatedOnBetween(serviceProviderIds, start, end);
   }

   @Override
   public List<ConsumerService> getConsumerServiceByCreatedOnGreaterThanAndCreatedOnLessThanEqual(Long industryId, Date startDate, Date endDate)
   {
      return consumerServiceRepository.findAllConsumerServiceByCreatedOnGreaterThanAndCreatedOnLessThanEqual(industryId, startDate, endDate);
   }
   
   @Override
   public int countConsumersByServiceTypeId(Long serviceTypeId, Date start, Date end)
   {
      return (int) consumerServiceRepository.countConsumersByServiceTypeIdAndCreatedOnGreaterThanAndCreatedOnLessThanEqual(serviceTypeId, start, end);
   }
   
   @Override
   public int countConsumersServicesByServiceTypeId(Long serviceTypeId, Date start, Date end)
   {
      return (int) consumerServiceRepository.countByServiceTypeIdAndCreatedOnGreaterThanAndCreatedOnLessThanEqual(serviceTypeId, start, end);
   }
   
   @Override
   public int countConsumersByServiceProviderIdAndServiceTypeId(Long serviceProviderId, Long serviceTypeId, Date start, Date end)
   {
      return (int) consumerServiceRepository.countConsumersByServiceProviderIdAndServiceTypeIdAndCreatedOnGreaterThanAndCreatedOnLessThanEqual(serviceProviderId, serviceTypeId,
      start, end);
   }
   
   @Override
   public int countConsumersServicesByServiceProviderIdAndServiceTypeId(Long serviceProviderId, Long serviceTypeId, Date start, Date end)
   {
      return (int) consumerServiceRepository.countConsumersServicesByServiceProviderIdAndServiceTypeIdAndCreatedOnGreaterThanAndCreatedOnLessThanEqual(serviceProviderId,
      serviceTypeId, start, end);
   }
   
   @Override
   public List<ConsumerService> getConsumerServiceByServiceTypeId(Long serviceTypeId, Date start, Date end)
   {
      return consumerServiceRepository.findAllConsumersServicesByServiceTypeIdAndCreatedOnGreaterThanAndCreatedOnLessThanEqual(serviceTypeId, start, end);
   }
   
   @Override
   public List<ConsumerService> getConsumerServiceByServiceProviderId(Long serviceProviderId, Date start, Date end)
   {
      return consumerServiceRepository.findAllConsumersServicesByServiceProviderIdAndCreatedOnGreaterThanAndCreatedOnLessThanEqual(serviceProviderId, start, end);
   }
   
   @Override
   public List<ConsumerService> getConsumerServiceByServiceProviderIdAndServiceTypeId(Long serviceProviderId, Long serviceTypeId, Date start, Date end)
   {
      return consumerServiceRepository.findAllConsumersServicesByServiceProviderIdAndServiceTypeIdAndCreatedOnGreaterThanAndCreatedOnLessThanEqual(serviceProviderId, serviceTypeId,
      start, end);
   }
   
   @Override
   public int countConsumersByConsumerId(Long consumerId)
   {
      return (int) consumerServiceRepository.countByConsumerId(consumerId);
   }
   
   @Override
   public int countConsumersByServiceId(Long serviceId)
   {
      return (int) consumerServiceRepository.countByServiceId(serviceId);
   }
   
   @Override
   public List<DashboardObjectInterface> countConsumerServicesGroupedByServiceProviderId(List<Long> serviceProvidersIdList, Date startDate, Date endDate)
   {
      return consumerServiceRepository.countConsumerServicesGroupedByServiceProviderId(serviceProvidersIdList, startDate, endDate);
   }

   @Override
   public List<List<String>> loadAirtelConsumers() {
      return null;
   }
   
}
