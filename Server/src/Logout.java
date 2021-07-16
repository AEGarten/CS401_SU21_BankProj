
public class Logout extends Message {
	
	//client use
	public Logout(int sessionID) {
		super(sessionID, Process.LOGOUT);
	}
	
	//server use
	public Logout(Message m, boolean success) {
		super(m, success);
	}
}
