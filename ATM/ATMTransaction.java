import java.util.*;
public class ATMTransaction {
	private String transactionID;
	private String date;
	private String time;
	private String type;
	
	public ATMTransaction() {
		transactionID = "";
		date = new Date();
		time = System.currentTimeMillis();
		type = "";
	}
	
	public String getDate() {
		return date;
	}
	
	public void setDate(String date) {
		this.date = date;
	}
	
	public String getTime() {
		return time;
	}
	
	public void setTime(String time) {
		this.time = time;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
}
