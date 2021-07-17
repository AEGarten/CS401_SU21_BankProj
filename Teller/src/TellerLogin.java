
public class TellerLogin extends Message {
	String login;
	String password;
	
	//for Teller
	public TellerLogin(String login, String pw) {
		super();
		this.login = login;
		this.password = pw;
	}
	
	//for Server
	public TellerLogin(int sessionID, int id, boolean success) {
		super(sessionID, id, success);
	}
	
	//for Server
	public TellerLogin(Message m) {
		super(m);
	}
}
