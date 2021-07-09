
//Goes inside Message. Has children of different types, so can change what is held without changing Message class
public class Packet {
	public int id;	//for what Customer/Employee/Account/Card ID to be engaged
	public Money amount;
	public String target; //for transfers, closing accounts

	public Packet() {};
	
	public Packet(int id, Money amount, String target) {
		this.id = id;
		this.amount = amount;
		this.target = target;
	}

}