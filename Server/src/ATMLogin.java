
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
	public int checkingID;
	public int savingsID;
	public boolean checkingPositive;
	public boolean savingsPositive;
	public int cardID;
	public int PIN;
	
	//ATM
	public ATMLogin(int cardID, int PIN) {
		super();
		this.cardID = cardID;
		this.PIN = PIN;
	}
	
	//for server use
	public ATMLogin(int checkID, int savID, boolean checkPos, boolean savPos, int sessionID, Message m) {
		super(m.sessionID, m.id, true);
		this.checkingID = checkID;
		this.savingsID = savID;
		this.checkingPositive = checkPos;
		this.savingsPositive = savPos;
	}

}
