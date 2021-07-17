import java.io.Serializable;
import java.util.Date;

public class Message implements Serializable {
	
	public final Process perform;
	public final boolean success;
	public final int id;
	public final int sessionID;
	
	public Message() {
		this.sessionID = 0;
		this.id = (int) (new Date().getTime() - 1625500000000L); //get long to int
		this.success = false;
		this.perform = Process.LOGIN;
	}
	
	//usually done by Message subclass
	public Message(int sessID, Process perform) {
		this.sessionID = sessID;
		this.id = (int) (new Date().getTime() - 1625500000000L); //get long to int
		this.success = false;
		this.perform = perform;
	}
	
	//for server, detailed success
	public Message(int sessionID, int id, boolean success) {
		this.sessionID = sessionID;
		this.id = id;
		this.success = success;
		this.perform = Process.LOGIN;
	}
	
	//for server use, fail
	public Message(Message m) {
		this.sessionID = m.sessionID;
		this.id = m.sessionID;
		this.perform = m.perform;
		this.success = false;
	}
	
	//for server use, simple success
	public Message(Message m, boolean success) {
		this.sessionID = m.sessionID;
		this.id = m.id;
		this.perform = m.perform;
		this.success = success;
	}
	
}
