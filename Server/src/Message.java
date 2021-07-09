import java.io.Serializable;
import java.util.Date;

public class Message implements Serializable {
	
	public Process perform;
	
	//Goes inside Message. Usually preferable to use one of its children instead, depending on what info needs to be sent
	public Packet packet; 
	/*
	 * Packet			//children: ATMPacket, AccountPacket, CustomerPacket, EmployeePacket
	 * --------
	 * +id: int			//for what Customer/Employee/Account/Card ID to be engaged
	 * +amount: Money	//for holding amount of change: Transfer/Deposit/Withdrawal
	 * +target: String	//for transfers, where to?; closing accounts, which account?
	 */
	
	public String authentication; //for ATM: PIN; Teller:Employee login, password; Teller:Employee+Customer login, passcode
	public final boolean success;
	public final int id;
	public final int sessionID;
	
	public Message() {
		this.sessionID = 0;
		this.id = (int) (new Date().getTime() - 1625500000000L); //get long to int
		this.success = false;
	}
	
	//after login sessID is available to clients
	public Message(int sessID) {
		this.sessionID = sessID;
		this.id = (int) (new Date().getTime() - 1625500000000L); //get long to int
		this.success = false;
	}
	
	//for server use
	public Message(int sessID, int id) {
		this.sessionID = sessID;
		this.id = id;
		this.success = false;
	}
	
	//for server use
	public Message(int sessID, int id, boolean success) {
		this.sessionID = sessID;
		this.id = id;
		this.success = success;
	}
	
}
