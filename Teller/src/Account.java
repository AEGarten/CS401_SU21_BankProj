import java.util.ArrayList;
import java.util.Date;

public class Account {
	
	private final int id;
	private final AccountType type;

	private Money balance = new Money();
	private boolean positiveStatus = false;
	private LastTransaction lastTransaction;
	private Date opened = new Date();
	private Date closed;
	private boolean attachedCard = false;  //is there an ATM assoc w this account
	private int cardID;						//if so what is the card id

	private ArrayList<Fee> fees = new ArrayList<>();	
	
	public Account(int id, AccountType at) {
		this.id = id;
		this.type = at;
	}
	
	//full constructor for loading from file; collections can be set separately 
	public Account(
			int id, AccountType accountType,
			Money balance, LastTransaction lastTransaction,
			Date opened, Date closed,
			boolean attCard, int cardID) {
		
		this(id, accountType);
		this.balance = balance;
		this.lastTransaction = lastTransaction;
		this.opened = opened;
		this.closed = closed;
		this.attachedCard = attCard;
		this.cardID = cardID;
	}

	public boolean isPositiveStatus() { return this.balance.isGreater(new Money()); }
	
	public LastTransaction getLastTransaction() { return this.lastTransaction; }
	public void setLastTransaction(LastTransaction lt) { this.lastTransaction = lt; }
	
	public Money getBalance() { return this.balance; }
	public void setBalance(Money b) { this.balance = b; }
	
	public Date getOpened() { return this.opened; }
	public void setOpened(Date d) { this.opened = d; }
	
	public Date getClosed() { return this.closed; }
	public void setClosed(Date d) { this.closed = d; }
	
	public boolean hasAttachedCard() { return this.attachedCard; }
	public void setAttachedCard(boolean ac) { this.attachedCard = ac; }
	
	public int getCardID() { return this.cardID; }
	public void setCardID(int cid) { this.cardID = cid; }
	
	public ArrayList<Fee> getFees(){ return this.fees; }
	public void setFees(ArrayList<Fee> f){ this.fees = f; }
	
	public int getID() { return this.id; }
	
	public AccountType getType() { return this.type; }
	
	public boolean addFee(Fee f) { 
		return this.fees.add(f); 
	}
	
	public Fee findFee(Date d) {
		for (Fee f: fees) 
			if (f.date.compareTo(d) == 0) return f;
		
		return null;
	}
	
	public boolean removeFee(Fee f) {
		return fees.remove(f);
	}
	
	public String toString() {
		return "#"+ id +" "+ type +" $"+ balance;
	}
	
	public String details() {
		String out = "#"+ id +" "+ type +" $"+ balance + " opened:" +
		opened.getMonth() +"/"+ opened.getDate() +"/"+ (opened.getYear() - 100);
		if (closed != null && closed.after(opened)) {
			out += " Closed:"+ closed.getMonth() +"/"+ closed.getDate() +"/"+ (closed.getYear() - 100);
		}
		out += "\n";
		
		if (lastTransaction != null) out += "Last transaction " + lastTransaction +"\n";
		if (!fees.isEmpty()) {
			out += "Fees:\n";
			for (Fee f: fees) out += "\t"+ f +"\n";
		}
		return out;
	}
	
}
