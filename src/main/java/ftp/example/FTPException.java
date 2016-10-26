package ftp.example;

public class FTPException extends Exception {

	private int code;
	private String msg;
	
	public FTPException(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	
	public int getCode(){
		return code;
	}
	
	public String getMsg(){
		return msg;
	}
}
