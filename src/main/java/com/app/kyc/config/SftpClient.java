package com.kyc.config;

import com.jcraft.jsch.*;
import com.kyc.model.RemoteFile;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

@Service
public class SftpClient {
    private final String host = "sftp.example.com";
    private final int port = 22;
    private final String username = "your_user";
    private final String password = "your_password";

    private ChannelSftp setupJsch() throws JSchException {
        JSch jsch = new JSch();
        Session session = jsch.getSession(username, host, port);
        session.setPassword(password);

        Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no"); // for dev/test
        session.setConfig(config);
        session.connect();

        Channel channel = session.openChannel("sftp");
        channel.connect();
        return (ChannelSftp) channel;
    }

    public List<RemoteFile> listFiles(String remoteDir) {
        List<RemoteFile> files = new ArrayList<>();
        try {
            ChannelSftp sftp = setupJsch();
            Vector<ChannelSftp.LsEntry> entries = sftp.ls(remoteDir);
            for (ChannelSftp.LsEntry entry : entries) {
                if (!entry.getAttrs().isDir()) {
                    files.add(new RemoteFile(
                            entry.getFilename(),
                            remoteDir + entry.getFilename(),
                            Instant.ofEpochSecond(entry.getAttrs().getMTime())
                    ));
                }
            }
            sftp.disconnect();
            sftp.getSession().disconnect();
        } catch (Exception e) {
            e.printStackTrace(); // log or throw depending on your error handling
        }
        return files;
    }

    public InputStream getFileStream(String filePath) throws Exception {
        ChannelSftp sftp = setupJsch();
        InputStream is = sftp.get(filePath);
        // NOTE: caller must close InputStream and handle disconnect
        return is;
    }

    public void move(String srcPath, String dstPath) throws Exception {
        ChannelSftp sftp = setupJsch();
        sftp.rename(srcPath, dstPath);
        sftp.disconnect();
        sftp.getSession().disconnect();
    }

    public void delete(String path) throws Exception {
        ChannelSftp sftp = setupJsch();
        sftp.rm(path);
        sftp.disconnect();
        sftp.getSession().disconnect();
    }
}
