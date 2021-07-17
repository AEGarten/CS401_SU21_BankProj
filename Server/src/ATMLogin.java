
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

public class ATMLogin extends Message {
	public int checkingID = 0;
	public int savingsID = 0;
	public boolean checkingPositive = false;
	public boolean savingsPositive = false;
	public int cardID = 0;
	public int PIN = 0;
	
	//ATM
	public ATMLogin(int cardID, int PIN) {
		super();
		this.cardID = cardID;
		this.PIN = PIN;
	}
	
	//server success
	public ATMLogin(int checkID, int savID, boolean checkPos, boolean savPos, int sessionID, Message m) {
		super(m.sessionID, m.id, true);
		this.checkingID = checkID;
		this.savingsID = savID;
		this.checkingPositive = checkPos;
		this.savingsPositive = savPos;
	}
	
	//server fail
	public ATMLogin(Message m, String why) {
		super(m, why);
	}

}
