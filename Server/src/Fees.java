import java.util.Date;

public class Fees {
	Money amount;
	Date date;
	String type;

	public Fees() {
		amount = new Money();
		date = new Date();
		type = "";
	}

	public Fees(Date date, Money amount, String type) {
		this.date = date;
		this.amount = amount;
		this.type = type;
	}
}
