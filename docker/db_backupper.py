import ftplib
import os

def download_file_from_ftp(server, port, username, password, remote_filepath, local_filepath):
    if os.path.isfile(local_filepath):
        print("Local db file deleted")
        os.remove(local_filepath)

    try:
        # Connect to the FTP server
        ftp = ftplib.FTP()
        ftp.connect(server, port)
        ftp.login(username, password)
        print(f"Connected to FTP server: {server}")

        # Download the file
        with open(local_filepath, 'wb') as local_file:
            ftp.retrbinary(f'RETR {remote_filepath}', local_file.write)
        print(f"File downloaded successfully to {local_filepath}")

        # Close the connection
        ftp.quit()
    except ftplib.all_errors as e:
        print(f"FTP error: {e}")

# FTP server details
ftp_server = os.environ['BACKUP_FTP_HOST']             # Replace with your FTP server
ftp_port = int(os.environ['BACKUP_FTP_PORT'])               # Default FTP port
ftp_username = os.environ['BACKUP_FTP_USERNAME']       # Replace with your FTP username
ftp_password = os.environ['BACKUP_FTP_PASSWORD']       # Replace with your FTP password
remote_file = os.environ['BACKUP_FTP_REMOTE_FILE']            # Replace with the remote file path
local_file = os.environ['BACKUP_FTP_LOCAL_FILE']

# Download the file
download_file_from_ftp(ftp_server, ftp_port, ftp_username, ftp_password, remote_file, local_file)
