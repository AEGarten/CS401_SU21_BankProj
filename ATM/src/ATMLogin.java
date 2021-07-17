
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
	public ATMLogin(int checkID, int savID, boolean checkPos, boolean savPos, int sessionID, int id, boolean success) {
		super(sessionID, id, success);
		this.checkingID = checkID;
		this.savingsID = savID;
		this.checkingPositive = checkPos;
		this.savingsPositive = savPos;
	}

}
