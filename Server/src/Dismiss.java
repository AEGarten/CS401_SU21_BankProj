
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


public class Dismiss extends Message {
	
	//Teller
	public Dismiss(int sessionID) {
		super(sessionID, Process.DISMISS);
	}
	
	//Server
	public Dismiss(Message m) {
		super(m, true);
	}
}
