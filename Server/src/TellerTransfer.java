
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

public class TellerTransfer extends Message {
	public Account toAccount;
	public Account frAccount;
	
	//ATM use
	public TellerTransfer(Customer customer, int sessionID, Money amount, int toID, int frID) {
		super(sessionID, Process.TRANSFER);
			
		Account toAcct = customer.findAccount(toID);
		Account frAcct = customer.findAccount(frID);
		
		//first update to-account, adding amount
		LastTransaction toLastTrans = new LastTransaction(
				toAcct.getBalance(), amount, "Teller transfer from:" + frAcct.getID());
		toAcct.setLastTransaction(toLastTrans);
		toAcct.setBalance(toAcct.getBalance().add(amount));
		this.toAccount = toAcct;
		
		//then update from-account, subtracting amount as it's made negative
		amount.setIsPositive(false);
		LastTransaction frLastTrans = new LastTransaction(
				frAcct.getBalance(), amount, "Teller transfer to:" + toAcct.getID());
		frAcct.setLastTransaction(frLastTrans);
		frAcct.setBalance(frAcct.getBalance().add(amount));
		this.frAccount = frAcct;
	}
	
	//Server, success
	public TellerTransfer(Message m) {
		super(m, true);
	}
	
	//Server, fail
	public TellerTransfer(Message m, String why) {
		super(m, why);
	}
}
