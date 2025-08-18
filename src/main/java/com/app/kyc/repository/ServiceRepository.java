package com.app.kyc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.kyc.entity.Service;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Long>
{
   @Query(value = "SELECT * FROM services WHERE name = ?", nativeQuery = true)
   Service findByName(@Param("name") String name);

   @Query(value = "SELECT * FROM services WHERE service_type_id = ?", nativeQuery = true)
   List<Service> findByServiceType(@Param("serviceTypeId") Long serviceTypeId);

   @Query(value = "SELECT * FROM services WHERE service_provider_id = ?", nativeQuery = true)
   List<Service> findByServiceProvider(@Param("serviceProviderId") Long serviceProviderId);

   @Query(value = "SELECT * FROM services WHERE id in (select * from consumer_services where consumer_id = ?)", nativeQuery = true)
   List<Service> findByConsumerSubscription(@Param("consumerId") Long consumerId);

}
