package com.app.kyc.service;

import com.app.kyc.entity.Consumer;
import com.app.kyc.entity.ProcessedFile;
import com.app.kyc.entity.ServiceProvider;
import com.app.kyc.entity.User;
import com.app.kyc.repository.ConsumerRepository;
import com.app.kyc.repository.ProcessedFileRepository;
import com.app.kyc.repository.ServiceProviderRepository;
import com.app.kyc.web.security.SecurityHelper;
import com.opencsv.CSVReader;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.transaction.annotation.Transactional;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class FileProcessingService {

    @Autowired private ProcessedFileRepository processedFileRepository;
    @Autowired private ServiceProviderRepository serviceProviderRepository;

    @Autowired private ConsumerRepository consumerRepository;

    @Autowired private ConsumerServiceImpl consumerServiceImpl;

    @Autowired
    UserService userService;

    HttpServletRequest request;

    @Autowired
    SecurityHelper securityHelper;
    @PersistenceContext
    private EntityManager em;

    // Keep batch size aligned with spring.jpa.properties.hibernate.jdbc.batch_size
    @Value("${spring.jpa.properties.hibernate.jdbc.batch_size:500}")
    private int batchSize;

    /**
     * Fast path: one transaction, JDBC batching, flush/clear each batch.
     */
    @Transactional
    public void processFile(Path filePath, String operator) throws IOException {
        System.out.println("▶️ ENTER processFile: " + filePath + " | operator=" + operator);

        if (Files.notExists(filePath)) {
            System.out.println("⛔ File does not exist: " + filePath);
            return;
        }

        long t0 = System.currentTimeMillis();
        System.out.println("🔎 Looking up ServiceProvider…");
        ServiceProvider sp = serviceProviderRepository.findByNameIgnoreCase(operator)
                .orElseThrow(() -> new IllegalArgumentException("Unknown operator: " + operator));

        List<Consumer> consumerRepositoryAll = consumerRepository.findAll();
        System.out.println("🔎 Looking up consumer size …"+consumerRepositoryAll.size());

        System.out.println("🔗 ServiceProvider id=" + sp.getId() + ", name=" + sp.getName()
                + " (" + (System.currentTimeMillis() - t0) + " ms)");

        System.out.println("📝 Saving ProcessedFile (IN_PROGRESS) …");
        ProcessedFile fileLog = new ProcessedFile();
        fileLog.setFilename(filePath.getFileName().toString());
        fileLog.setStatus(FileStatus.IN_PROGRESS);
        fileLog.setStartedAt(LocalDateTime.now());
        fileLog.setRecordsProcessed(0);
        processedFileRepository.save(fileLog);  // <-- if logs stop here, it's DB connection
        System.out.println("✅ ProcessedFile saved.");

        System.out.println("📖 Opening CSV…");

        int totalProcessed = 0;
        int totalSkipped = 0;

        try (FileReader fr = new FileReader(filePath.toFile());
             CSVReader reader = new CSVReaderBuilder(fr)
                     .withCSVParser(new CSVParserBuilder().withSeparator(',').build())
                     .build()) {

            String[] row;
            boolean isHeader = true;

            while ((row = reader.readNext()) != null) {
                if (isHeader) { isHeader = false; continue; }

                // Skip empty / malformed
                if (row.length <= 1 || row[0] == null || row[0].trim().isEmpty()) {
                    totalSkipped++;
                    continue;
                }

                Consumer reg = mapRowToRegistration(row);
                if (reg == null) { totalSkipped++; continue; }

                // Link operator per row
                reg.setServiceProvider(sp);

                // ⬅️ Upsert to tolerate duplicates on msisdn
                em.merge(reg); // instead of em.persist(reg)
                totalProcessed++;

                // Flush/clear every batch to keep PC small and push batched statements
                if (totalProcessed % batchSize == 0) {
                    em.flush();
                    em.clear();
                    System.out.println("✅ Flushed " + totalProcessed + " records so far. Elapsed " +
                            (System.currentTimeMillis() - t0) + " ms");
                }

                // heartbeat every 5k
                if (totalProcessed % 5000 == 0) {
                    System.out.println("⏳ Progress: " + totalProcessed + " processed, " + totalSkipped + " skipped");
                }
            }

            // final flush
            em.flush();
            em.clear();

            fileLog.setRecordsProcessed(totalProcessed);
            fileLog.setStatus(FileStatus.COMPLETE);
            fileLog.setCompletedAt(LocalDateTime.now());
            fileLog.setLastUpdated(LocalDateTime.now());
            processedFileRepository.save(fileLog);


                User user = userService.getUserByEmail("cadmin@test.com");

                consumerServiceImpl.checkConsumer(consumerRepositoryAll,user, sp);



            System.out.println("🎉 DONE: processed=" + totalProcessed + ", skipped=" + totalSkipped +
                    " in " + (System.currentTimeMillis() - t0) + " ms");

        } catch (Exception ex) {
            // mark failed (still inside transaction; status will be persisted)
            fileLog.setStatus(FileStatus.FAILED);
            fileLog.setLastError("Ingestion error: " + ex.getMessage());
            fileLog.setLastUpdated(LocalDateTime.now());
            processedFileRepository.save(fileLog);
            throw ex; // let Spring roll back the transaction
        }

        // Move AFTER DB work
        try {
            moveToProcessedFolder(filePath, fileLog);
            fileLog.setLastUpdated(LocalDateTime.now());
            processedFileRepository.save(fileLog);
        } catch (IOException e) {
            fileLog.setStatus(FileStatus.FAILED);
            fileLog.setLastError("Move failed: " + e.getMessage());
            fileLog.setLastUpdated(LocalDateTime.now());
            processedFileRepository.save(fileLog);
            System.err.println("❌ Error moving file: " + e.getMessage());
        }
    }

    /**
     * Map CSV row → Consumer. Keep light. Parse/sanitize here if needed.
     * NOTE: If DB columns are DATE/LocalDate, parse strings before setting.
     */
    private Consumer mapRowToRegistration(String[] fields) {
        java.util.Date currentDate = new java.util.Date();
        if (fields == null || fields.length < 17) return null;

        try {
            Consumer reg = new Consumer();
            reg.setMsisdn(safe(fields[0]));
            reg.setCreatedOn(currentDate);
            reg.setRegistrationDate(safe(fields[1])); // parse to LocalDate if column is DATE
            reg.setFirstName(safe(fields[2]));
            reg.setMiddleName(safe(fields[3]));
            reg.setLastName(safe(fields[4]));
            reg.setGender(safe(fields[5]));
            reg.setBirthDate(safe(fields[6]));        // parse if DATE in DB
            reg.setBirthPlace(safe(fields[7]));
            reg.setAddress(safe(fields[8]) + " " + safe(fields[9]) + " " + safe(fields[10]) + " " + safe(fields[11]) + " " + safe(fields[12]));
            reg.setAlternateMsisdn1(safe(fields[13]));
            reg.setAlternateMsisdn2(safe(fields[14]));
            reg.setIdentificationType(safe(fields[15]));
            reg.setIdentificationNumber(safe(fields[16]));
            return reg;
        } catch (Exception e) {
            return null; // skip bad row
        }
    }

    private static String safe(String s) {
        return (s == null) ? null : s.trim();
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
