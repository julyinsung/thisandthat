package ftp.example;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;

import jdk.nashorn.internal.ir.annotations.Ignore;

public class FTPServiceTest {

	Map<String, String> ftpInfo;
	FTPService service;
	List<String> files;
	Properties props = null;
	
	@Before
	public void setup(){
		String propFile = "C:\\Users\\july\\Documents\\dev_source\\java\\thisandthat\\src\\main\\java\\ftp\\example\\config.properties";
		
		File f = new File(propFile);
		System.out.println(f.exists());
		
		props = new Properties();
        FileInputStream fis;
		try {
			fis = new FileInputStream(propFile);
			props.load(new java.io.BufferedInputStream(fis));
		} catch (IOException e) {
			e.printStackTrace();
		}
		service = new FTPService();
		
		ftpInfo = new HashMap<String, String>();
        ftpInfo.put("ip", props.getProperty("na.ftp.addr"));
        ftpInfo.put("port", props.getProperty("na.ftp.port"));
        ftpInfo.put("user", props.getProperty("na.ftp.user"));
        ftpInfo.put("password", props.getProperty("na.ftp.pwd"));
        ftpInfo.put("dir", props.getProperty("na.ftp.dir"));
        
        files = new ArrayList<String>();
        files.add("C:\\Users\\july\\Documents\\project\\Thing_Plug\\work_info.txt");
        files.add("C:\\Users\\july\\Documents\\project\\Thing_Plug\\LGUPLUS.zip");
        
	}
	
	@Ignore
	@Test
	public void testSendFile() {
		int result = service.sendFile(files, ftpInfo);
		assertEquals(200, result);
	}
	
}
