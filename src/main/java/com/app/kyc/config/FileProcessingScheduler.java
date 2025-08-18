package com.app.kyc.config;

import com.app.kyc.service.FileProcessingService;
import com.app.kyc.service.KycFileProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class FileProcessingScheduler {

    @Autowired
    private FileProcessingService fileProcessingService;

    @Autowired
    private KycFileProcessor kycFileProcessor;

    @Value("${file.folders}")
    private String[] folders;

    @Value("${file.expected-name:export_arptc.csv}")
    private String expectedFileName;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");

    // Runs every minute
   // @Scheduled(cron = "0 */30 * * * *")
    public void scanAndProcessFiles() {
        System.out.println("🔁 Starting scheduled file scan...");

        for (String folderName : folders) {
            Path dir = Paths.get(folderName).toAbsolutePath();
            System.out.println("📂 Checking directory: " + dir);

            if (!Files.exists(dir)) {
                System.out.println("❌ Folder does not exist: " + dir);
                continue;
            }

            Path filePath = dir.resolve(expectedFileName);
            System.out.println("🔍 Looking for file: " + filePath);

            if (Files.exists(filePath)) {
                System.out.println("✅ Found file: " + filePath);

                try {
                    // Process the file
                    fileProcessingService.processFile(filePath);

                    // Move to processed folder with timestamp
                    Path processedDir = dir.resolve("processed");
                    if (!Files.exists(processedDir)) {
                        Files.createDirectory(processedDir);
                    }

                    String timestamp = LocalDateTime.now().format(formatter);
                    String newFileName = "export_arptc_" + timestamp + ".csv";
                    Path targetPath = processedDir.resolve(newFileName);

                    Files.move(filePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
                    System.out.println("📦 File moved to: " + targetPath);

                } catch (IOException e) {
                    System.err.println("❌ Error processing file: " + e.getMessage());
                    e.printStackTrace();
                }

            } else {
                System.out.println("⛔ File not found in: " + dir);
            }
        }

        System.out.println("✅ File scan completed.");
    }
}
