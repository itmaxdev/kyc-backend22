package com.app.kyc.config;


import com.app.kyc.service.FileProcessingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Component
public class FileProcessingTask {

    @Autowired
    private FileProcessingService fileProcessingService;

    @Value("#{'${local.file.paths}'.split(',')}")
    private List<String> localFilePaths;

    @Scheduled(cron = "0 */1 * * * *") // every minute
    public void processLocalFiles() {
        System.out.println("🔁 Running Scheduled Task: " + java.time.LocalDateTime.now());

        for (String pathStr : localFilePaths) {
            try {
                Path filePath = Paths.get(pathStr.trim());
                System.out.println("📂 Looking for file: " + filePath);

                if (Files.exists(filePath) && Files.isRegularFile(filePath)) {
                    System.out.println("✅ Found file: " + filePath);
                    fileProcessingService.processFile(filePath);
                } else {
                    System.out.println("⛔ File not found (or not a file): " + filePath);
                }

            } catch (Exception e) {
                System.err.println("❌ Error processing file at " + pathStr + ": " + e.getMessage());
                e.printStackTrace();
            }
        }
    }



}
