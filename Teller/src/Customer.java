import java.util.Date;
import java.util.ArrayList;

public class Customer {
	
	private final int id;
	private String name;
	private String passcode;
	private String PIN;
	private int numSavings;
	private int numChecking;
	private ArrayList<Account> accounts = new ArrayList<>();
	private Date opened = new Date();
	private Date closed = null;
	
	//TODO cardConnected boolean, hashmap of cards to id, delete card to customer table fr db
	
	
	public Customer(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	//full constructor for loading from file; collections can be set separately
	public Customer(
			int id, String name,
			String passcode, int numSavings,
			int numChecking, String PIN, 
			Date opened, Date closed) {
		
		this(id, name);
		this.passcode = passcode;
		this.numSavings = numSavings;
		this.numChecking = numChecking;
		this.PIN = PIN;
		this.opened = opened;
		this.closed = closed;
	}
	
	public String getName() { return this.name; }
	public void setName(String n) { this.name = n; }
	
	public String getPasscode() { return this.passcode; }
	public void setPasscode(String p) { this.passcode = p; }
	
	public int getNumSavings() { return this.numSavings; }
	public void setNumSavings(int n) { this.numSavings = n; }
	
	public int getNumChecking() { return this.numChecking; }
	public void setNumChecking(int n) { this.numChecking = n; }
	
	public Date getOpened() { return this.opened; }
	public void setOpened(Date d) { this.opened = d; }
	
	public Date getClosed() { return this.closed; }
	public void setClosed(Date d) { this.closed = d; }
	
	public String getPIN() { return this.PIN; }
	public void setPIN(String PIN) { this.PIN = PIN; }
	
	public int getID() { return this.id; }
	
	public ArrayList<Account> getAccounts() { return this.accounts; }
	public void setAccounts(ArrayList<Account> a) { this.accounts = a; }
	
	public void addAccount(Account a) { this.accounts.add(a); }
	
	public Account findAccount(int id) {
		for (Account a: this.accounts) {
			if (a.getID() == id) return a;	//if found stop search
		}
		return null;
	}
	
	public boolean removeAccount(int id) {
		Account target;
		
		if ((target = findAccount(id)) == null) return false; //fail to find
		
		accounts.remove(target);
		return true;
	}
	
	public String toString() {
		String out;
		out = "#"+ id +" "+ name;
		if (closed != null && closed.after(opened)) {
			out += " closed:"+ closed.getMonth() +"/"+ closed.getDate() +"/"+ (closed.getYear() - 100);
		}
		out += "\n";
		
		for(Account a: accounts) {
			if (a.getClosed() != null && a.getClosed().after(a.getOpened())) continue;
			else out += a +"\n";
		}
		return out;
	}
	
	public String details() {
		String out;
		out = "#"+ id +" "+ name +" opened:"+ 
				opened.getMonth() +"/"+ opened.getDate() +"/"+ (opened.getYear() - 100);
		if (closed != null && closed.after(opened)) {
			out += " closed:"+ closed.getMonth() +"/"+ closed.getDate() +"/"+ (closed.getYear() - 100);
		}
		out += "\n";
		
		for(Account a: accounts) out += a.details() +"\n";
		return out;
	}
	
	
	
}
