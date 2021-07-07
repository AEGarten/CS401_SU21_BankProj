

public class Account {
	
	public final int id;
	public final AccountType type;
	private boolean postiveStatus = false;
	private Money balance;
	private LastTransaction lastTransaction;
	
	//TODO: When classes become available
//	private LinkedList<> pendings = new LinkedList<Pending>();	
//	private LinkedList<> fees = new LinkedList<Fee>();	
	
	public Account(int id, AccountType at) {
		this.id = id;
		this.type = at;
	}

	public boolean isPositiveStatus() { return this.postiveStatus; }
	public void setPositiveStatus(boolean ps) { this.postiveStatus = ps; }
	
	public LastTransaction getLastTransaction() { return this.lastTransaction; }
	public void setPositiveStatus(LastTransaction lt) { this.lastTransaction = lt; }
	
	public Money getBalance() { return this.balance; }
	public void setBalance(Money b) { this.balance = b; }
	
	//TODO: When classes become available
//	public LinkedList<Pending> getPendings(){ return this.pendings; }
//	public void setPendings(LinkedList<Pending> p){ this.pendings = p; }
	
//	public LinkedList<Fee> getFees(){ return this.fees; }
//	public void setFees(LinkedList<Fee> f){ this.fees = f; }

//	public void addPending(Pending p) { this.pendings.addFirst(p); }
	
//	public Pending popPending() {
//		Pending last = this.pendings.getLast();
//		this.pendings.removeLast();
//		return last; 
//	}
	
//	public void addFee(Pending f) { this.fees.addFirst(f); }
	
//	public Pending popFee() {
//	Fee last = this.fees.getLast();
//	this.fees.removeLast();
//	return last; 
//	}
	
}
