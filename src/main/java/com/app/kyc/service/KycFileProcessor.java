package com.app.kyc.service;

import  com.app.kyc.entity.ProcessedFile;
import  com.app.kyc.repository.ProcessedFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class KycFileProcessor {

    @Autowired
    private ProcessedFileRepository repository;

    public void process(InputStream inputStream, ProcessedFile file, int batchSize) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            int counter = 0;
            List<String> batch = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                batch.add(line);
                if (batch.size() >= batchSize) {
                    persistBatch(batch);
                    file.setRecordsProcessed(file.getRecordsProcessed() + batch.size());
                    repository.save(file);
                    batch.clear();
                }
            }
            if (!batch.isEmpty()) {
                persistBatch(batch);
                file.setRecordsProcessed(file.getRecordsProcessed() + batch.size());
            }
            file.setStatus(FileStatus.COMPLETE);
            file.setCompletedAt(LocalDateTime.now());
        } catch (Exception e) {
            file.setStatus(FileStatus.FAILED);
            file.setLastError(e.getMessage());
        } finally {
            file.setLastUpdated(LocalDateTime.now());
            repository.save(file);
        }
    }

    private void persistBatch(List<String> batch) {
        // Add your record persistence logic here
        batch.forEach(System.out::println); // For now just print
    }

}