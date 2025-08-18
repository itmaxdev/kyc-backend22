package com.app.kyc.repository;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.kyc.entity.ConsumerService;
import com.app.kyc.model.DashboardObjectInterface;

@Repository
public interface ConsumerServiceRepository extends JpaRepository<ConsumerService, Long>
{
   @Query(value = "select count(*) from consumers_services where service_id in (" + "select id from services where service_provider_id in (select id from service_providers where industry_id = :industryId))", nativeQuery = true)
   int countByIndustryId(Long industryId);

   @Query(value = "select * from consumers_services where service_id in (" + "select id from services where service_provider_id in (select id from service_providers where industry_id = :industryId))" + "and created_on > :start and created_on <= :end", nativeQuery = true)
   List<ConsumerService> findAllConsumerServiceByCreatedOnGreaterThanAndCreatedOnLessThanEqual(Long industryId, Date start, Date end);

   @Query(value = "select count(*) from consumers_services where service_id in (" + "select id from services where service_provider_id in (select id from service_providers where industry_id = :industryId))" + "and created_on > :start and created_on <= :end", nativeQuery = true)
   int countByIndustryIdAndCreatedOnGreaterThanAndCreatedOnLessThanEqual(Long industryId, Date start, Date end);


   long countByConsumer_ServiceProvider_IdInAndCreatedOnBetween(Collection<Long> ids, Date createdOnStart, Date createdOnEnd);



   @Query(value = "select count(distinct consumer_id) from consumers_services where service_id in (" + "select id from service_types where id = :serviceTypeId)" + "and created_on > :start and created_on <= :end", nativeQuery = true)
   int countConsumersByServiceTypeIdAndCreatedOnGreaterThanAndCreatedOnLessThanEqual(Long serviceTypeId, Date start, Date end);

   @Query(value = "select count(distinct consumer_id) from consumers_services where service_id in (" + "select id from services where service_type_id = :serviceTypeId)" + "and service_id in (select id from services where service_provider_id = :serviceProviderId)" + "and created_on > :start and created_on <= :end", nativeQuery = true)
   int countConsumersByServiceProviderIdAndServiceTypeIdAndCreatedOnGreaterThanAndCreatedOnLessThanEqual(Long serviceProviderId, Long serviceTypeId, Date start, Date end);

   @Query(value = "select count(*) from consumers_services where service_id in (" + "select id from services where service_type_id = :serviceTypeId)" + "and service_id in (select id from services where service_provider_id = :serviceProviderId)" + "and created_on > :start and created_on <= :end", nativeQuery = true)
   int countConsumersServicesByServiceProviderIdAndServiceTypeIdAndCreatedOnGreaterThanAndCreatedOnLessThanEqual(Long serviceProviderId, Long serviceTypeId, Date start, Date end);

   @Query(value = "select count(*) from consumers_services where service_id in (" + "select id from service_types where id = :serviceTypeId)" + "and created_on > :start and created_on <= :end", nativeQuery = true)
   int countByServiceTypeIdAndCreatedOnGreaterThanAndCreatedOnLessThanEqual(Long serviceTypeId, Date start, Date end);

   @Query(value = "select * from consumers_services where service_id in (" + "select id from service_types where id = :serviceTypeId)" + "and created_on > :start and created_on <= :end", nativeQuery = true)
   List<ConsumerService> findAllConsumersServicesByServiceTypeIdAndCreatedOnGreaterThanAndCreatedOnLessThanEqual(Long serviceTypeId, Date start, Date end);

   @Query(value = "select * from consumers_services where service_id in (" + "select id from service_providers where id = :serviceProviderId)" + "and created_on > :start and created_on <= :end", nativeQuery = true)
   List<ConsumerService> findAllConsumersServicesByServiceProviderIdAndCreatedOnGreaterThanAndCreatedOnLessThanEqual(Long serviceProviderId, Date start, Date end);

   @Query(value = "select * from consumers_services where service_id in (" + "select id from service_types where id = :serviceTypeId)" + "and service_id in (select id from service_providers where id = :serviceProviderId)" + "and created_on > :start and created_on <= :end", nativeQuery = true)
   List<ConsumerService> findAllConsumersServicesByServiceProviderIdAndServiceTypeIdAndCreatedOnGreaterThanAndCreatedOnLessThanEqual(Long serviceProviderId, Long serviceTypeId,
      Date start, Date end);

   int countByConsumerId(Long consumerId);

   int countByServiceId(Long serviceId);

   @Query(value = "select (select name from service_providers where id = service_provider_id) as name, count(*) as value from consumers_services join services" + " on service_id = services.id" + " where service_provider_id in :serviceProvidersIdList" + " and consumers_services.created_on > :startDate and consumers_services.created_on <= :endDate" + " group by service_provider_id", nativeQuery = true)
   List<DashboardObjectInterface> countConsumerServicesGroupedByServiceProviderId(List<Long> serviceProvidersIdList, Date startDate, Date endDate);

}
