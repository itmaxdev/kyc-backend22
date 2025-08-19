package com.kyc.config;

import com.jcraft.jsch.*;
import com.app.kyc.service.FileProcessingService;
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
public class SftpRarScheduledTask {

    @Autowired
    private FileProcessingService fileProcessingService;

    @Value("${remote.host}")
    private String remoteHost;

    @Value("${remote.user}")
    private String remoteUser;

    @Value("${remote.password}")
    private String remotePassword;

    @Value("${remote.file.path}")
    private String remoteFilePath;

    @Value("${local.download.dir}")
    private String localDownloadDir;

    //@Scheduled(cron = "0 */1 * * * *")

    public void fetchFileFromRemoteServer() {
        System.out.println("🔁 Running Scheduled Task: " + java.time.LocalDateTime.now());

        Session session = null;
        ChannelSftp sftpChannel = null;

        try {
            JSch jsch = new JSch();
            session = jsch.getSession(remoteUser, remoteHost, 22);
            session.setPassword(remotePassword);

            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.connect();
            System.out.println("✅ Connected to intermediate server: " + remoteHost);

            Channel channel = session.openChannel("sftp");
            channel.connect();
            sftpChannel = (ChannelSftp) channel;

            String fileName = Paths.get(remoteFilePath).getFileName().toString();
            Path localPath = Paths.get(localDownloadDir, fileName);
            System.out.println("⬇️ Downloading: " + remoteFilePath + " to " + localPath);

            sftpChannel.get(remoteFilePath, localPath.toString());

            System.out.println("✅ File downloaded: " + localPath);
            fileProcessingService.processFile(localPath);

        } catch (Exception e) {
            System.err.println("❌ Error during remote SFTP download: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (sftpChannel != null && sftpChannel.isConnected()) {
                sftpChannel.exit();
            }
            if (session != null && session.isConnected()) {
                session.disconnect();
            }
        }
    }

}
