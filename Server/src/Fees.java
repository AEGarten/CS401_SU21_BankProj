import java.util.Date;
public class Fees extends Pending{
	private final int id = 0;
	Money money;
	public Fees() {
		money = new Money();
	}
	public Date date() {
		return date;
	}
	
	public String amount() {
		return money.toString();
		
	}
	
	public void pending(Date date, Money money) {
		// Need Pending
	}
	
	public void pending(int id, Date date, Money money) {
		// Need pending
	}
	
	public int getID() {
		return id;
	}
}
