package com.app.kyc.repository;

import  com.app.kyc.entity.ProcessedFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProcessedFileRepository extends JpaRepository<ProcessedFile, UUID> {
    Optional<ProcessedFile> findByOperatorAndFilename(String operator, String filename);

}
