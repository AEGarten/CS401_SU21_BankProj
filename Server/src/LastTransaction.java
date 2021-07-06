import java.util.Date;

public class LastTransaction {
	public Date date;
	public Money priorBalance;
	public Money changeInBalance;
	public String type;
	
	public LastTransaction(Date date, Money balance, Money changeInBal, String type) {
		this.date = date;
		this.priorBalance = balance;
		this.changeInBalance = changeInBal;
		this.type = type;
	}
	
}
