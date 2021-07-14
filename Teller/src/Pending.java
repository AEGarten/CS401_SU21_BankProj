import java.util.Date;

public class Pending {
	Date date = new Date();
	Money amount;
	int checkNumber;
	
	public Pending(Money amount, int checkNumber) {
		this.date = new Date();
		this.amount = amount;
		this.checkNumber = checkNumber;
	}
	
}
