
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

public class TellerDeposit extends Message {
	public Account account;
	
	//Teller use
	public TellerDeposit(Customer customer, int sessionID, Money amount, int accountID) {
		super(sessionID, Process.DEPOSIT);
		
		Account acct = customer.findAccount(accountID);
		LastTransaction lastTrans = new LastTransaction(acct.getBalance(), amount, "Teller deposit");
		acct.setLastTransaction(lastTrans);
		acct.setBalance(acct.getBalance().add(amount));
		this.account = acct;
	}
	
	//Server use, success
	public TellerDeposit(Message m) {
		super(m, true);
	}
	
	//Server use, fail
	public TellerDeposit(Message m, String why) {
		super(m, why);
	}
}
