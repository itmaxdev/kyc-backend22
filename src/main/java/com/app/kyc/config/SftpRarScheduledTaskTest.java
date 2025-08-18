package com.kyc.config;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.kyc.entity.Registration;
import com.kyc.repository.RegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Component
public class SftpRarScheduledTaskTest {

    private final String intermediateHost = "10.0.150.39";
    private final String intermediateUser = "kumar.saragadam";
    private final String intermediatePass = "O9d#4n1#3ing8dS5%&8";

    private final String sftpHost = "10.0.150.248";
    private final String sftpUser = "sftp -P 2222 kumar.saragadam@10.0.150.248";
    private final String sftpPass = "J@!3nFin4^67nHn13%(1n";

    private final String remoteRarPath = "/Africell_SFTP/KYC_dumps/tbl_registration.rar";
    private final String localRarPath = "/home/kumar.saragadam/tbl_registration.rar";
    private final String extractDir = "/home/kumar.saragadam/";

    @Autowired
    private RegistrationRepository repository;
    private static final String FILE_PATH = "C:/Users/swaro/OneDrive/Documents/tbl_registration.csv";
    //@Scheduled(cron = "0 */1 * * * *")  // Every 5 minutes
    public void scheduledCsvImport() {
        importCsv(FILE_PATH);
    }


    public void importCsv(String filePath) {
        System.out.println("🔁 Running Scheduled Task: " + java.time.LocalDateTime.now());
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isHeader = true;
            List<Registration> registrations = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                if (isHeader) {
                    isHeader = false;
                    continue;
                }
                System.out.println("Inside one");
                String[] fields = line.split(",");
               /* if (fields.length < 4 || fields[0].isBlank() || fields[1].isBlank() || !fields[1].contains("@")) {
                    continue; // validation failed
                }*/
                System.out.println("Inside two");
                Registration reg = new Registration();
                reg.setMsisdn(fields[0].trim());
                reg.setFirstName(fields[1].trim());
                reg.setLastName(fields[2].trim());
                reg.setAddress(fields[3].trim());
                reg.setRegDate(fields[4].trim());
                reg.setDob(fields[5].trim());
                registrations.add(reg);
                System.out.println("Inside three");
            }
            repository.saveAll(registrations);
        } catch (Exception e) {
            throw new RuntimeException("CSV import failed", e);
        }
    }

   // @Scheduled(cron = "0 */5 * * * *")  // Every 5 minutes
    public void performScheduledDownloadAndExtract() {
        Session session = null;

        try {
            System.out.println("🔁 Running Scheduled Task: " + java.time.LocalDateTime.now());

            // SSH into intermediate server (10.0.150.39)
            JSch jsch = new JSch();
            session = jsch.getSession(intermediateUser, intermediateHost, 22);
            session.setPassword(intermediatePass);
            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.connect();
            System.out.println("✅ Connected to intermediate server");

            // Open an exec channel to run SFTP from inside the intermediate session
            ChannelExec sftpChannel = (ChannelExec) session.openChannel("exec");

            // Inline shell script to:
            // 1. connect to SFTP
            // 2. download file
            // 3. extract RAR
            String fullCommand = ""
                    + "sftp " + sftpUser + "@" + sftpHost + " <<EOF\n"
                    + sftpPass + "\n"
                    + "get " + remoteRarPath + " " + localRarPath + "\n"
                    + "bye\n"
                    + "EOF\n"
                    + "unrar x -o+ " + localRarPath + " " + extractDir + "\n";

            sftpChannel.setCommand(fullCommand);
            sftpChannel.setErrStream(System.err);
            sftpChannel.setInputStream(null);
            InputStream stdout = sftpChannel.getInputStream();

            sftpChannel.connect();

            byte[] tmp = new byte[1024];
            while (true) {
                while (stdout.available() > 0) {
                    int i = stdout.read(tmp, 0, 1024);
                    if (i < 0) break;
                    System.out.print(new String(tmp, 0, i));
                }
                if (sftpChannel.isClosed()) {
                    System.out.println("✅ Exit Status: " + sftpChannel.getExitStatus());
                    break;
                }
                Thread.sleep(1000);
            }

            sftpChannel.disconnect();
            session.disconnect();
            System.out.println("🎉 Task Completed: File downloaded and extracted");

        } catch (Exception e) {
            System.err.println("❌ Error during SFTP or extraction: " + e.getMessage());
            e.printStackTrace();
            if (session != null && session.isConnected()) {
                session.disconnect();
            }
        }
    }
}
