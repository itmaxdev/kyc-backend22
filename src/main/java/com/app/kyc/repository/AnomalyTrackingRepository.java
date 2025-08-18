package com.app.kyc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.kyc.entity.AnomalyTracking;

@Repository
public interface AnomalyTrackingRepository extends JpaRepository<AnomalyTracking, Long>
{

   List<AnomalyTracking> findAllByAnomalyId(Long id);

}
