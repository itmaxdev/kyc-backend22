package com.app.kyc.service;

import  com.app.kyc.entity.ProcessedFileEntity;
import  com.app.kyc.repository.ProcessedFileEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileProcessingEntityService {

    @Autowired
    private ProcessedFileEntityRepository repo;

    public void processFile(MultipartFile file) throws IOException {
        String filename = file.getOriginalFilename();
        ProcessedFileEntity pf = repo.findByFilename(filename).orElse(new ProcessedFileEntity());
        pf.setFilename(filename);
        pf.setLastUpdated(LocalDateTime.now());

        int chunkSize = 100;
        List<String> chunk = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            int processed = pf.getRecordsProcessed();

            for (int i = 0; i < processed; i++) reader.readLine(); // skip

            String line;
            while ((line = reader.readLine()) != null) {
                chunk.add(line);
                if (chunk.size() >= chunkSize) {
                    processChunk(chunk, pf);
                    chunk.clear();
                }
            }
            if (!chunk.isEmpty()) processChunk(chunk, pf);

            pf.setStatus("COMPLETE");
            pf.setCompletedAt(LocalDateTime.now());
        } catch (Exception e) {
            pf.setStatus("FAILED");
            pf.setLastError(e.getMessage());
        } finally {
            pf.setLastUpdated(LocalDateTime.now());
            repo.save(pf);
        }
    }

    private void processChunk(List<String> chunk, ProcessedFileEntity pf) {
        for (String line : chunk) {
            // Simulate processing (e.g., parsing CSV, saving to DB)
            System.out.println("Processing: " + line);
            pf.setRecordsProcessed(pf.getRecordsProcessed() + 1);
        }
        repo.save(pf);
    }
}
