
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

public class CustomerAccess extends Message {
	String passcode = "";
	int customerID = 0;
	Customer customer = null;
	
	//Teller
	CustomerAccess(int sessionID, String passcode, int customerID){
		super(sessionID, Process.ACCESS);
		this.passcode = passcode;
		this.customerID = customerID;
	}
	
	//Server success
	CustomerAccess(Customer c, Message m){
		super(m.sessionID, m.id, true);
		this.customer = c;
	}
	
	//Server fail
		CustomerAccess(Message m, String why){
			super(m, why);
		}
}
