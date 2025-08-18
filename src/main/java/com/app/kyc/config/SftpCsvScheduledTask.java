package com.kyc.config;

import com.jcraft.jsch.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

@Component
public class SftpCsvScheduledTask {

    private final String sftpHost = "10.0.150.248";
    private final int sftpPort = 2222;
    private final String sftpUser = "kumar.saragadam";
    private final String sftpPass = "J@!3nFin4^67nHn13%(1n";

    private final String remoteFile = "/Vodacom_SFTP/KYC_dumps/export_arptc.csv";
    private final String localFolder = "C:/kyc_downloads/vodacom_adicorp/";

   // @Scheduled(cron = "0 */1 * * * *")  // every 1 minute
    public void performScheduledCsvDownload() {
        System.out.println("🔁 Starting CSV download task...");

        Session session = null;
        ChannelSftp channelSftp = null;

        try {
            // Setup JSch
            JSch jsch = new JSch();
            session = jsch.getSession(sftpUser, sftpHost, sftpPort);
            session.setPassword(sftpPass);

            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);

            session.connect();
            System.out.println("✅ Connected to SFTP server: " + sftpHost);

            // Connect SFTP
            channelSftp = (ChannelSftp) session.openChannel("sftp");
            channelSftp.connect();

            // Build local filename with timestamp
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String localFilePath = localFolder + "export_arptc_" + timestamp + ".csv";

            // Download the file
            try (FileOutputStream fos = new FileOutputStream(localFilePath)) {
                InputStream inputStream = channelSftp.get(remoteFile);
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    fos.write(buffer, 0, bytesRead);
                }
                System.out.println("✅ File downloaded to: " + localFilePath);
            }

        } catch (Exception e) {
            System.err.println("❌ Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (channelSftp != null && channelSftp.isConnected()) {
                channelSftp.disconnect();
            }
            if (session != null && session.isConnected()) {
                session.disconnect();
            }
            System.out.println("🎉 File download task completed!");
        }
    }
}
