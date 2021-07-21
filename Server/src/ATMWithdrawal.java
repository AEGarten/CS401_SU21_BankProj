
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

public class ATMWithdrawal extends Message {
	public Money amount;
	public int accountID;
	boolean accountPositive;
	
	//ATM use
	public ATMWithdrawal(int sessionID, Money amount, int accountID) {
		super(sessionID, Process.WITHDRAWAL);
			this.amount = amount;
			this.accountID = accountID;
	}
	
	//Server, success
	public ATMWithdrawal(Message m, boolean positive) {
		super(m, true);
		this.accountPositive = positive;
	}
	
	//Server, fail
	public ATMWithdrawal(Message m, String why) {
		super(m, why);
	}
	
	
}
