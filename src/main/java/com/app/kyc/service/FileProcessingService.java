package com.app.kyc.service;

import com.app.kyc.entity.ProcessedFile;
import com.app.kyc.entity.Registration;
import com.app.kyc.repository.ProcessedFileRepository;
import com.app.kyc.repository.RegistrationRepository;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class FileProcessingService {

    @Autowired
    private ProcessedFileRepository processedFileRepository;

    @Autowired
    private RegistrationRepository registrationRepository;

    private final int batchSize = 100;

    public void processFile(Path filePath) {
        if (Files.notExists(filePath)) {
            System.out.println("⛔ File does not exist: " + filePath);
            return;
        }

        ProcessedFile fileLog = new ProcessedFile();
        fileLog.setFilename(filePath.getFileName().toString());
        fileLog.setStatus(FileStatus.IN_PROGRESS);
        fileLog.setStartedAt(LocalDateTime.now());
        fileLog.setRecordsProcessed(0);
        processedFileRepository.save(fileLog);

        List<Registration> batch = new ArrayList<>();
        int totalProcessed = 0;

        try (
                FileReader fr = new FileReader(filePath.toFile());
                CSVReader reader = new CSVReader(fr)
        ) {
            String[] row;
            boolean isHeader = true;

            while ((row = reader.readNext()) != null) {
                if (isHeader) {
                    isHeader = false;
                    continue;
                }

                // Skip empty or junk rows
                if (row.length <= 1 || row[0].trim().isEmpty()) {
                    System.out.println("⚠️ Skipping empty or malformed row: " + Arrays.toString(row));
                    continue;
                }

                Registration reg = mapRowToRegistration(row);
                if (reg != null) {
                    batch.add(reg);
                }

                if (batch.size() >= batchSize) {
                    registrationRepository.saveAll(batch);
                    totalProcessed += batch.size();
                    batch.clear();
                    System.out.println("✅ Saved batch of " + batchSize + " records.");
                }
            }

            if (!batch.isEmpty()) {
                registrationRepository.saveAll(batch);
                totalProcessed += batch.size();
                System.out.println("✅ Saved final batch of " + batch.size() + " records.");
            }

            fileLog.setRecordsProcessed(totalProcessed);
            fileLog.setStatus(FileStatus.COMPLETE);
            fileLog.setCompletedAt(LocalDateTime.now());

        } catch (IOException | CsvValidationException e) {
            fileLog.setStatus(FileStatus.FAILED);
            fileLog.setLastError(e.getMessage());
            System.err.println("❌ Error processing file: " + e.getMessage());
        } finally {
            fileLog.setLastUpdated(LocalDateTime.now());
            processedFileRepository.save(fileLog);
        }

        // ⚠️ Move file AFTER closing readers
        try {
            moveToProcessedFolder(filePath, fileLog);
        } catch (IOException e) {
            fileLog.setStatus(FileStatus.FAILED);
            fileLog.setLastError("Move failed: " + e.getMessage());
            System.err.println("❌ Error moving file: " + e.getMessage());
            processedFileRepository.save(fileLog);
        }
    }

    private Registration mapRowToRegistration(String[] fields) {
        if (fields == null || fields.length < 17) {
            System.out.println("⚠️ Skipping invalid row (null or too short): " + Arrays.toString(fields));
            return null;
        }

        try {
            Registration reg = new Registration();
            reg.setMsisdn(fields[0].trim());
            reg.setRegDate(fields[1].trim());
            reg.setFirstName(fields[2].trim());
            reg.setMiddleName(fields[3].trim());
            reg.setLastName(fields[4].trim());
            reg.setGender(fields[5].trim());
            reg.setDob(fields[6].trim());
            reg.setPlaceOfBirth(fields[7].trim());
            reg.setAddress(fields[8].trim());
            reg.setMsisdn1(fields[13].trim());  // Column 14
            reg.setMsisdn2(fields[14].trim());  // Column 15
            reg.setCardType(fields[15].trim()); // Column 16
            reg.setCardId(fields[16].trim());   // Column 17
            return reg;
        } catch (Exception e) {
            System.out.println("❌ Error mapping row: " + Arrays.toString(fields) + " - " + e.getMessage());
            return null;
        }
    }

    private void moveToProcessedFolder(Path filePath, ProcessedFile fileLog) throws IOException {
        Path processedDir = filePath.getParent().resolve("processed");
        Files.createDirectories(processedDir);

        String ts = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String newName = filePath.getFileName().toString().replace(".csv", "_" + ts + ".csv");
        Path target = processedDir.resolve(newName);

        Files.move(filePath, target, StandardCopyOption.REPLACE_EXISTING);
        fileLog.setFilenameNew(newName);

        System.out.println("✅ Moved processed file to: " + target);
    }
}
