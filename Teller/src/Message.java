import java.util.Date;

public class Message {
	
	public Process perform;
	public Packet packet;
	public String authentication; //for ATM: PIN; Teller:Employee login, password; Teller:Employee+Customer login, passcode
	public final boolean success;
	public final int id;
	public final int sessionID;
	
	public Message() {
		this.sessionID = 0;
		this.id = (int) (new Date().getTime() - 1625500000000L); //get long to int
		this.success = false;
	}
	
	public Message(int sessID, int id) {
		this.sessionID = sessID;
		this.id = id;
		this.success = false;
	}
	
	public Message(int sessID, int id, boolean success) {
		this.sessionID = sessID;
		this.id = id;
		this.success = success;
	}
	
}
