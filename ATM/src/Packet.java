
//Goes inside Message. Has children of different types, so can change what is held without changing Message class
public class Packet {
	public int actOnID;	//for what Customer/Employee/Account/Card ID to perform operation
	public Money amount;
	public int addendumID; 	//for transfers, closing accounts (not customers)
	public int checkNumber;	//for check deposits

	public Packet() {};
	
	public Packet(int id, Money amount, int plusID, int checkNum) {
		this.actOnID = id;
		this.amount = amount;
		this.addendumID = plusID;
		this.checkNumber = checkNum;
	}

}