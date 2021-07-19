
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

public class ATMTransfer extends Message {
	public Money amount;
	public int toAccountID;
	public int frAccountID;
	boolean frAccountPositive;
	boolean toAccountPositive;
	
	//ATM use
	public ATMTransfer(int sessionID, Money amount, int toID, int frID) {
		super(sessionID, Process.TRANSFER);
			this.amount = amount;
			this.toAccountID = toID;
			this.frAccountID = frID;
	}
	
	//Server, success
	public ATMTransfer(Message m, boolean toPositive, boolean frPositive) {
		super(m, true);
		this.toAccountPositive = toPositive;
		this.frAccountPositive = frPositive;
	}
	
	//Server, fail
	public ATMTransfer(Message m, String why) {
		super(m, why);
	}
}
