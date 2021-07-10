import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class DataBase {
	
	private UniqueIDs customerIDs = new UniqueIDs();
	private UniqueIDs accountIDs = new UniqueIDs();
//	private UniqueIDs employeeIDs = new UniqueIDs();
	
	private ArrayList<Customer> customers = new ArrayList<>();
//	private ArrayList<Empoyee> employees = new ArrayList<>();
	
	//maps Customers to their cards, more efficient than searching each Customer then each Acct
	private int [][] cardToCustomerTable;
	
	//locking for concurrency; excludes writers from readers (who can work together)
	private final ReadWriteLock rwLock = new ReentrantReadWriteLock();
	private final Lock readLock = rwLock.readLock();
	private final Lock writeLock = rwLock.writeLock();
	
	
	//reserve (not get) because it mutates id list
	public int reserveNewCustomerID() {
		int id = customerIDs.findNewID();
		customerIDs.addID(id);
		return id;
	}
	
	public int reserveNewAccountID() {
		int id = accountIDs.findNewID();
		accountIDs.addID(id);
		return id;
	}
	
	public Customer findCustomer(int custID) {
		int foundIndex;
		
		readLock.lock();
		try {
			foundIndex = Collections.binarySearch(customers, custID, 
					(a, b) -> ((Customer) a).getID() - ((Customer) b).getID());
			
			return (foundIndex > 0) ? customers.get(foundIndex) : null;
		}
		finally { readLock.unlock(); }
	}
	
	public boolean setCustomer(Customer c) {
		int toReplace;
		
		//if need read for write to get index, then whole thing needs lock, no pieces
		readLock.lock();
		
		//find it
		try {
			toReplace = Collections.binarySearch(customers, c.getID(), 
					(a, b) -> ((Customer) a).getID() - ((Customer) b).getID());
			if (toReplace > 0) {
				
				//delete it and replace, does not affect sort order
				writeLock.lock();
				try { 
					customers.remove(toReplace);
					customers.add(toReplace, c);
				} 
				finally { writeLock.unlock(); }
				
				return true;
			}
		}
		finally { readLock.unlock(); }
		return false;		
	}
	
	public boolean removeCustomer(int id) {
		Customer toRemove = findCustomer(id);
		
		if (toRemove == null) return false; //fail to find
		
		writeLock.lock();
		try { customers.remove(toRemove); }
		finally { writeLock.unlock(); }
		
		customerIDs.removeID(toRemove.getID());
		return true;
	}
	
	public boolean addCustomer(Customer c) {
		boolean success = false;
		
		writeLock.lock();
		try { 
			success = customers.add(c); 
			Collections.sort(customers, (a, b) -> a.getID() - b.getID());
		}
		finally { writeLock.unlock(); }
		customerIDs.addID(c.getID());
		return success;
	}
	
}
