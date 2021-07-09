

public class Account {
	
	private final int id;
	private final AccountType type;
	private boolean positiveStatus = false;
	private Money balance;
	private LastTransaction lastTransaction;
	
	//TODO: When classes become available
//	private ArrayList<Pending> pendings = new ArrayList<>();	
//	private ArrayList<Fee> fees = new ArrayList<>();	
	
	public Account(int id, AccountType at) {
		this.id = id;
		this.type = at;
	}
	
	//full constructor for loading from file; collections can be set separately 
	public Account(
			int id, AccountType accountType,
			boolean positiveStatus, Money balance,
			LastTransaction lastTransaction) {
		
		this(id, accountType);
		this.positiveStatus = positiveStatus;
		this.balance = balance;
		this.lastTransaction = lastTransaction;
	}

	public boolean isPositiveStatus() { return this.positiveStatus; }
	public void setPositiveStatus(boolean ps) { this.positiveStatus = ps; }
	
	public LastTransaction getLastTransaction() { return this.lastTransaction; }
	public void setPositiveStatus(LastTransaction lt) { this.lastTransaction = lt; }
	
	public Money getBalance() { return this.balance; }
	public void setBalance(Money b) { this.balance = b; }
	
	//TODO: When classes become available
//	public ArrayList<Pending> getPendings(){ return this.pendings; }
//	public void setPendings(ArrayList<Pending> p){ this.pendings = p; }
	
//	public ArrayList<Fee> getFees(){ return this.fees; }
//	public void setFees(ArrayList<Fee> f){ this.fees = f; }
	
	public int getID() { return this.id; }
	
	public AccountType getType() { return this.type; }

//	public void addPending(Pending p) { this.pendings.add(p); }
	
	//TODO: public Pending findPending(Date d)
	
	//TODO: public boolean removePending(Date d)
	
//	public void addFee(Fee f) { this.fees.add(f); }
	
	//TODO: public Fee findFee(Date d)
	
	//TODO: public boolean removeFee(Date d)
	
	
}
