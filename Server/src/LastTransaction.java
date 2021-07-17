
import java.util.Date;

public class LastTransaction {
	public Date date = new Date();
	public Money priorBalance;
	public Money changeInBalance;
	public String type;
	
	//for backup file
	public LastTransaction(Date date, Money lastBalance, Money changeInBal, String type) {
		this(lastBalance, changeInBal, type);
		this.date = date;
	}
	
	//for regular use
	public LastTransaction(Money lastBalance, Money changeInBal, String type) {
		this.priorBalance = lastBalance;
		this.changeInBalance = changeInBal;
		this.type = type;
	}
	
	public String toString() {
		String out = date.getMonth() +"/"+ date.getDate() +"/"+ (date.getYear() - 100) + 
				" "+ date.getHours() +":"+ String.format("%02d", date.getMinutes()) + 
				" $"+ priorBalance +" "+ changeInBalance +" "+ type;
		
		return out;
	}
}
