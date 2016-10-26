package ftp.example;

public class FTPInfo{
	
	public enum StatusCode {
		SUCCESS(200),
		SOCKET_EXCEPTION(900),
		IO_EXCEPTION(910);
		
		private int value;
		
		private StatusCode(int value) {
			this.value = value;
		}
		
		public int getValue() { return value; }
	}
	
	public enum NWFTP {
		NABLE("nable"),
		CONTELA("contela");
		
		private String value;
		
		private NWFTP(String value) {
			this.value = value;
		}
		
		public String getValue() { return value; }
	}
	
	public enum CCBSFTP {
		CCBS("ccbs");
		
		private String value;
		
		private CCBSFTP(String value) {
			this.value = value;
		}
		
		public String getValue() { return value; }
	}
	
	public enum DeleteFile {
		Y(true), N(false);
		
		private boolean value;
		
		private DeleteFile(boolean value) {
			this.value = value;
		}
		
		public boolean getValue() { return value; }
	}
	
}

