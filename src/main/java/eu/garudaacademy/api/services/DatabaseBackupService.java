package eu.garudaacademy.api.services;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
public class DatabaseBackupService {

    private String BACKUP_FTP_HOST = System.getenv("BACKUP_FTP_HOST");
    private String BACKUP_FTP_PORT = System.getenv("BACKUP_FTP_PORT");
    private String BACKUP_FTP_USERNAME = System.getenv("BACKUP_FTP_USERNAME");
    private String BACKUP_FTP_PASSWORD = System.getenv("BACKUP_FTP_PASSWORD");
    private String BACKUP_FTP_REMOTE_FILE = System.getenv("BACKUP_FTP_REMOTE_FILE");
    private String BACKUP_FTP_LOCAL_FILE = System.getenv("BACKUP_FTP_LOCAL_FILE");

    public void backupDb() {
        System.out.println("Start backupping");

        uploadFile(BACKUP_FTP_HOST, Integer.parseInt(BACKUP_FTP_PORT), BACKUP_FTP_USERNAME, BACKUP_FTP_PASSWORD, BACKUP_FTP_LOCAL_FILE, BACKUP_FTP_REMOTE_FILE);
        uploadFile(BACKUP_FTP_HOST, Integer.parseInt(BACKUP_FTP_PORT), BACKUP_FTP_USERNAME, BACKUP_FTP_PASSWORD, BACKUP_FTP_LOCAL_FILE, BACKUP_FTP_REMOTE_FILE + "b");
    }

    public void uploadFile(String server, int port, String user, String pass, String localFilePath, String remoteFilePath) {
        FTPClient ftpClient = new FTPClient();
        try {
            // Connect and login to the FTP server
            ftpClient.connect(server, port);
            ftpClient.login(user, pass);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            // Upload file to the FTP server
            try (InputStream inputStream = new FileInputStream(localFilePath)) {
                if (ftpClient.listFiles(remoteFilePath).length > 0) {
                    ftpClient.deleteFile(remoteFilePath);
                    System.out.println("Remote db file deleted");
                }

                boolean done = ftpClient.storeFile(remoteFilePath, inputStream);

                if (done) {
                    System.out.println("The file is uploaded successfully.");
                } else {
                    System.out.println("Failed to upload the file.");
                }
            }

            // Logout and disconnect from the FTP server
            ftpClient.logout();
        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
            ex.printStackTrace();

        } finally {
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.disconnect();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
