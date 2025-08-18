package com.app.kyc.repository;

import java.util.List;

import com.app.kyc.model.DashboardObjectInterface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.kyc.entity.ServiceType;

@Repository
public interface ServiceTypeRepository extends JpaRepository<ServiceType, Long>
{
   @Query(value = "SELECT * FROM service_types WHERE name = ?", nativeQuery = true)
   ServiceType findByName(@Param("name") String name);

   List<ServiceType> findAllByIndustryId(Long industryId);

   @Query("select s.id as value, s.name as name from ServiceType s where s.industry.id = ?1")
   List<DashboardObjectInterface> findAllByIndustryIdForDashboard(Long industryId);

   @Query("select s.id from ServiceType s where s.industry.id = ?1")
   List<Long> findServiceTypesIdByIndustryId(Long industryId);

}
