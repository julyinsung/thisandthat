package ftp.example;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.SocketException;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;


public class FTPService {

	private static final Logger logger = Logger.getLogger(FTPService.class.getName());
	static Properties props = null;
	
	public int sendFile(List<String> files, Map<String, String> ftpInfo) {
		int result = 200;
		int retryCnt = 3;
		while(retryCnt > 0){
			try {
				executeUpload(files, ftpInfo);
				break;
			} catch (FTPException e) {
				logger.log(Level.SEVERE, e.getMsg());
				result = 900;
				break;
			} catch (SocketException e) {
				retryCnt = retryCnt - 1;
				logger.info("retry count: "+ (3-retryCnt));
//				e.printStackTrace();
			} catch (IOException e) {
				retryCnt = retryCnt - 1;
				logger.info("retry count: "+ (3-retryCnt));
//				e.printStackTrace();
			}
			if(retryCnt == 0){
				logger.log(Level.SEVERE, "Error Retry Connect.");
				result = 900;
			}
		}
		return result;
	}
	
	private void executeUpload(List<String> files, Map<String, String> ftpInfo) throws FTPException, SocketException, IOException{
		String propFile = "C:\\Users\\july\\Documents\\dev_source\\thingplug\\ftp\\src\\ftp\\example\\config.properties";
		setProperties(propFile);
		
		FTPClient ftp = new FTPClient();
		ftp.setConnectTimeout(Integer.parseInt(props.getProperty("ftp.conn.timeout")));
		ftp.setControlEncoding("UTF-8");
		
		try {
			// ����
			int port = Integer.parseInt(ftpInfo.get("port"));
			ftp.connect(ftpInfo.get("ip"), port);
			logger.info("Connected to " + ftpInfo.get("ip") + " on " + port);

			if(!FTPReply.isPositiveCompletion(ftp.getReplyCode())){
				throw new FTPException(0, ftp.getReplyString());
			}
			
			// �α���
        	ftp.login(ftpInfo.get("user"), ftpInfo.get("password"));
        	if(!FTPReply.isPositiveCompletion(ftp.getReplyCode())){
				throw new FTPException(0, ftp.getReplyString());
			}
        	
        	ftp.enterLocalPassiveMode(); // Passive Mode �����϶� 
        	
        	// ���ε� ���� Ÿ�� ����
        	ftp.setFileType(FTP.BINARY_FILE_TYPE);
        	if(!FTPReply.isPositiveCompletion(ftp.getReplyCode())){
				throw new FTPException(0, ftp.getReplyString());
			}
        	
 		    // �۾� ���丮 ����
		    ftp.changeWorkingDirectory(ftpInfo.get("dir"));
		    if(!FTPReply.isPositiveCompletion(ftp.getReplyCode())){
				throw new FTPException(0, ftp.getReplyString());
			}

			//���� ������ŭ ����
			test(files, ftp);
			
//		} catch (SocketException e){
//			result = FTPInfo.StatusCode.SOCKET_EXCEPTION.getValue();
//			logger.log(Level.SEVERE, "Status Code: "+result +" "+e.toString());
//			
//		} catch (IOException e) {
//			result = FTPInfo.StatusCode.IO_EXCEPTION.getValue();
//			logger.log(Level.SEVERE, "Status Code: "+result +" "+e.toString());
			
		} finally {
				try {
					ftp.disconnect();
					logger.info("FIS Close, FTP Disconnect");
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
//		return result;
	}

	private void test(List<String> files, FTPClient ftp)
			throws FileNotFoundException, IOException, FTPException {
		
		for(int i = 0; i < files.size(); i++){
			File uploadfile = new File(files.get(i));
			FileInputStream fis = new FileInputStream(uploadfile);
			String fileName = uploadfile.getName();
			String tempFile = fileName+".temp";
			
			//���� ���ε�� ���Ͽ� .temp�� �ٿ��� ���ε� �Ѵ�.
			ftp.storeFile(tempFile, fis);
			
		    if (!FTPReply.isPositiveCompletion(ftp.getReplyCode())){
		    	throw new FTPException(0, ftp.getReplyString());
		    	
		    } else {
		    	logger.info("FTP Reply: " + ftp.getReplyString());
		    	
		    	// ���������� �����̸� .temp Ȯ���ڸ� �����Ѵ�.
		    	ftp.rename(tempFile, fileName);
		    	
		    	// ������ �����Ͽ� rename�� �����̸� ������ �̸��� ������ ����� �ٽ� rename�Ѵ�.
		    	if(!FTPReply.isPositiveCompletion(ftp.getReplyCode())){
		    		logger.info("FTP Reply: " + ftp.getReplyString());
		    		
		    		// �ߺ��� ���� ����
		    		ftp.deleteFile(fileName);
		    		if(!FTPReply.isPositiveCompletion(ftp.getReplyCode())){
						throw new FTPException(0, ftp.getReplyString());
					}else {
						logger.info("FTP Reply: " + ftp.getReplyString());
					}
		    		
		    		// ���� rename
		    		ftp.rename(tempFile, fileName);
		    		if(!FTPReply.isPositiveCompletion(ftp.getReplyCode())){
						throw new FTPException(0, ftp.getReplyString());
					}else {
						logger.info("FTP Reply: " + ftp.getReplyString());	
					}
		        } else {
		        	logger.info("FTP Reply: " + ftp.getReplyString());
		        }
		    }
		}
	}
	
	
	private static void setProperties(String file){
		props = new Properties();
        FileInputStream fis;
		try {
			fis = new FileInputStream(file);
			props.load(new java.io.BufferedInputStream(fis));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
