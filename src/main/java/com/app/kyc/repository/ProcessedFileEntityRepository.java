package com.app.kyc.repository;

import com.app.kyc.entity.ProcessedFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProcessedFileEntityRepository extends JpaRepository<ProcessedFileEntity, Long> {
    Optional<ProcessedFileEntity> findByFilename(String filename);
}
