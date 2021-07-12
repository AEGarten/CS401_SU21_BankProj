import java.util.Date;

public class Pending {
	Date date = new Date();
	Money amount;
	int checkNumber;
	
	public Pending(Date date, Money amount, int checkNumber) {
		this.date = date;
		this.amount = amount;
		this.checkNumber = checkNumber;
	}
	
}
