package com.app.kyc.repository;

import com.app.kyc.model.DashboardObjectInterface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.kyc.entity.Industry;

import java.util.List;

@Repository
public interface IndustryRepository extends JpaRepository<Industry, Long>
{
   @Query(value = "SELECT * FROM industries WHERE name = ?", nativeQuery = true)
   Industry findByName(@Param("name") String name);

   @Query("select i.name as name, i.id as value from Industry i order by i.id asc")
   List<DashboardObjectInterface> findAllForDashboard();
}
