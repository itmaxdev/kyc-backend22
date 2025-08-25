package com.app.kyc.config;

import com.app.kyc.service.FileProcessingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Locale;

@Component
public class FileProcessingTask {

    @Autowired
    private FileProcessingService fileProcessingService;

    @Value("#{'${local.file.paths}'.split(',')}")
    private List<String> localFilePaths;

    @Scheduled(cron = "0 */1 * * * *") // every minute
    public void processLocalFiles() {
        System.out.println("Running Scheduled Task: " + java.time.LocalDateTime.now());

        for (String pathStr : localFilePaths) {
            try {
                Path filePath = Paths.get(pathStr.trim());
                System.out.println(" Looking for file: " + filePath);

                if (Files.exists(filePath) && Files.isRegularFile(filePath)) {
                    String operator = detectOperator(filePath.getFileName().toString());
                    System.out.println(" Found file: " + filePath + " | Operator: " + operator);
                    if (operator.equalsIgnoreCase("Vodacom")) {
                        fileProcessingService.processFileVodacom(filePath, operator); // pass operator
                    } else if (operator.equalsIgnoreCase("Airtel")) {
                        {
                            fileProcessingService.processFileAirtel(filePath, operator); // pass operator
                        }
                    } else if (operator.equalsIgnoreCase("Orange")) {
                        {
                            fileProcessingService.processFileOrange(filePath, operator); // pass operator
                        }
                    } else {
                        System.out.println(" File not found (or not a file): " + filePath);
                    }
                }
            } catch (Exception e) {
                System.err.println(" Error processing file at " + pathStr + ": " + e.getMessage());
                e.printStackTrace();
            }
        }

    // Map filename → operator (case-insensitive, supports “export_arptc” w/ or w/o extension)


}

    private String detectOperator(String filename) {
            String f = filename.toLowerCase(Locale.ROOT);
            if (f.contains("kyc_sample_record_from_kyc_data_source_ok.csv")) return "Airtel";
            if (f.contains("dump_echantillon_kyc.csv"))                     return "Orange";
            if (f.contains("export_arptc"))                                  return "Vodacom"; // matches export_arptc or export_arptc.csv
            return "Unknown";
        }

    }
