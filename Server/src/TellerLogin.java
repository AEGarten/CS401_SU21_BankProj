
/*
	 * Message //parent
	 * -------------------------
	 * +perform: Process enum
	 * +success: boolean = false
	 * +id: int = auto					//auto generated from date
	 * +sessionID: int = 0				//continued login
	 * +why: String	= ""				//why fail
	 * -------------------------
	 * +Message()
	 * +Message(sessionID, perform) 	//subclass use
	 * +Message(sessionID, id, success)	//server: detailed success
	 * +Message(Message, success)		//server: simple success
	 * +Message(Message, why)			//server: fail
	 */

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
