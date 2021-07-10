import java.util.Date;

public class Pending {
	final int id =  (int) (new Date().getTime() - 1625500000000L); //get long to int
	Date date = new Date();
	Money amount;
	int checkNumber;
	
	public Pending(Date date, Money amount, int checkNumber) {
		this.date = date;
		this.amount = amount;
		this.checkNumber = checkNumber;
	}
	
	public int getID() {
		return id;
	}
}
