package com.kyc.service;

import com.kyc.config.SftpClient;
import com.kyc.model.RemoteFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class RetentionCleanupService {
    @Autowired
    private SftpClient sftp;

    //@Scheduled(cron = "0 0 0 * * *") // midnight daily
    public void cleanupProcessedFiles() throws Exception {
        List<String> operators = List.of("Africell","Airtel","Orange","Vodacom");
        for (String op: operators) {
            String procDir = op + "_SFTP/KYC_dumps/processed/";
            List<RemoteFile> files = sftp.listFiles(procDir);
            Instant cutoff = Instant.now().minus(90, ChronoUnit.DAYS);
            for (RemoteFile f : files) {
                if (f.getModifiedTime().isBefore(cutoff)) {
                    sftp.delete(f.getPath());
                }
            }
        }
    }
}
