
public class TellerLogin extends Message {
	String login = "";
	String password = "";
	
	//for Teller
	public TellerLogin(String login, String pw) {
		super();
		this.login = login;
		this.password = pw;
	}
	
	//for Server, success
	public TellerLogin(int sessionID, int id, boolean success) {
		super(sessionID, id, success);
	}
	
	//for Server, fail
	public TellerLogin(Message m, String why) {
		super(m, why);
	}
}
