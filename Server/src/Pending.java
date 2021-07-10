import java.util.Date;

public class Pending {
	final int id =  (int) (new Date().getTime() - 1625500000000L); //get long to int
	Date date = new Date();
	Money amount;
	
	public Pending(Date date, Money amount) {
		this.date = date;
		this.amount = amount;
	}
	
	public int getID() {
		return id;
	}
}
