package com.kyc.config;


import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.kyc.service.FileProcessingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

@Component
public class RemoteFileDownloadTask {

    @Value("${server.host}")
    private String remoteHost;

    @Value("${server.username}")
    private String remoteUser;

    @Value("${server.password}")
    private String remotePassword;

    @Value("${file.folders}")
    private String remoteFilePath;

    @Value("${local.download.dir}")
    private String localDownloadDir;

    @Autowired
    private FileProcessingService fileProcessingService;

    @Scheduled(cron = "0 */30 * * * *") // Every 30 minute
    public void downloadAndProcess() {
        Session session = null;

        try {
            System.out.println("🔁 Running Scheduled Task: " + java.time.LocalDateTime.now());

            JSch jsch = new JSch();
            session = jsch.getSession(remoteUser, remoteHost, 22);
            session.setPassword(remotePassword);

            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.connect();
            System.out.println("✅ Connected to remote server: " + remoteHost);

            // Prepare to download the file over exec
            String command = "cat " + remoteFilePath;
            ChannelExec channel = (ChannelExec) session.openChannel("exec");
            channel.setCommand(command);
            channel.setInputStream(null);

            InputStream in = channel.getInputStream();
            channel.connect();

            String fileName = Paths.get(remoteFilePath).getFileName().toString();
            Path localFilePath = Paths.get(localDownloadDir, fileName);
            try (OutputStream outputStream = new FileOutputStream(localFilePath.toFile())) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = in.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, bytesRead);
                }
            }

            System.out.println("✅ File downloaded to: " + localFilePath);

            fileProcessingService.processFile(localFilePath);

            channel.disconnect();
            session.disconnect();
        } catch (Exception e) {
            System.err.println("❌ Error during remote download: " + e.getMessage());
            e.printStackTrace();
            if (session != null && session.isConnected()) {
                session.disconnect();
            }
        }
    }
}
