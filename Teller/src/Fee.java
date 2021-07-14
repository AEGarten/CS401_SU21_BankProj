import java.util.Date;

public class Fee {
	Money amount;
	Date date;
	String type;

	public Fee() {
		amount = new Money();
		date = new Date();
		type = "";
	}

	public Fee(Date date, Money amount, String type) {
		this.date = date;
		this.amount = amount;
		this.type = type;
	}
}
