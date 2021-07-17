import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class DataBase {
	
	private UniqueIDs customerIDs = new UniqueIDs();
	private UniqueIDs accountIDs = new UniqueIDs();
	private UniqueIDs employeeIDs = new UniqueIDs();
	
	private ArrayList<Customer> customers = new ArrayList<>();
	private ArrayList<Employee> employees = new ArrayList<>();
	
	//maps cards to their Customers, more efficient than searching each Customer then each Acct
	private HashMap<Integer, Integer> cardToCustomerTable = new HashMap<>();
	
	//maps employee logins to their id, concurrent because it changes while Sever Onlines
	private ConcurrentHashMap<String, Integer> loginToIDTable = new ConcurrentHashMap<>();
	
	public DataBase() {
		//TODO remove hardcoding after FileManager added
		cardToCustomerTable.put(567890, 4543);
		
		accountIDs.addID(92837);
		accountIDs.addID(52704706);
		
		Account check = new Account(92837, AccountType.CHECKING);
		check.setBalance(new Money(23, 16, true));
		check.setAttachedCard(true);
		check.setCardID(567890);
		
		Account sav = new Account(52704706, AccountType.SAVINGS);
		sav.setBalance(new Money(100, 01, true));
		sav.setAttachedCard(true);
		sav.setCardID(567890);
		
		Customer cust = new Customer(4543, "Aidan Chartreuse");
		cust.addAccount(check);
		cust.addAccount(sav);
		customers.add(cust);
		
		employeeIDs.addID(197);
		loginToIDTable.put("Login", 197);
		
		Employee employee = new Employee("Dummy Employee", 197, "Login", "Password");
		employees.add(employee);
		
	}
	
	public int getCustomerFromCard(int cardNum) {
		if (cardToCustomerTable.containsKey(cardNum)) 
			return cardToCustomerTable.get(cardNum);
		
		return 0;
	}
	
	private void addCardToCustomer(int cardNum, int customerID) {
		cardToCustomerTable.put(cardNum, customerID);
	}
	
	public int getNewCustomerID() {
		return customerIDs.findNewID();
	}
	
	//reserve (not get) because it mutates id list for Account
	//Customer has its id added when adding to the collection, account doesn't
	public int reserveNewAccountID() {
		int id = accountIDs.findNewID();
		accountIDs.addID(id);
		return id;
	}
	
//	public int reserveNewEmployeeID() {
//		int id = employeeIDs.findNewID();
//		employeeIDs.addID(id);
//		return id;
//	}
	
	public synchronized Customer findCustomer(int custID) {
		int foundIndex;
		Customer key = new Customer(custID, null);
			
		foundIndex = Collections.binarySearch(customers, key, 
			(a, b) -> a.getID() - b.getID());		
		return (foundIndex > -1) ? customers.get(foundIndex) : null;
	}
	
	public synchronized boolean setCustomer(Customer c) {
		int toReplace;
		
		toReplace = Collections.binarySearch(customers, c, 
				(a, b) -> a.getID() - b.getID());
		
		if (toReplace > -1) {
			
			//delete it and replace, does not affect sort order
			customers.remove(toReplace);
			customers.add(toReplace, c);
			return true;				//finally executes before return
		}	
		return false;		
	}
	
	public synchronized boolean removeCustomer(int id) {
		Customer toRemove = findCustomer(id);
		if (toRemove == null) return false; //fail to find

		//does not affect sort order
		customers.remove(toRemove); 		
		customerIDs.removeID(toRemove.getID());
		return true;
	}
	
	public synchronized boolean addCustomer(Customer c) {
		boolean success = false;
		
		if (customerIDs.addID(c.getID()) && customers.add(c)) {
			success = true;
			Collections.sort(customers, (a, b) -> a.getID() - b.getID()); //only sort if add successful
		}
		return success;
	}
	
	public synchronized boolean addEmployee(Employee e) {
		boolean success = false;
		
		if (employeeIDs.addID(e.getEmployeeID()) && employees.add(e)) {
			success = true;
			Collections.sort(employees, (a, b) -> a.getEmployeeID() - b.getEmployeeID()); //only sort if add successful
		}
		return success;
	}
	
	public synchronized Employee findEmployee(int empID) {
		int foundIndex;
		Employee key = new Employee("", empID, "", "");
			
		foundIndex = Collections.binarySearch(employees, key, 
			(a, b) -> a.getEmployeeID() - b.getEmployeeID() );		
		return (foundIndex > -1) ? employees.get(foundIndex) : null;
	}
	
	public synchronized boolean setEmployee(Employee e) {
		int toReplace;
		
		toReplace = Collections.binarySearch(employees, e, 
				(a, b) -> a.getEmployeeID() - b.getEmployeeID());
		
		if (toReplace > -1) {
			
			//delete it and replace, does not affect sort order
			employees.remove(toReplace);
			employees.add(toReplace, e);
			return true;				//finally executes before return
		}	
		return false;		
	}
	
	public synchronized boolean removeEmployee(int id) {
		Employee toRemove = findEmployee(id);
		if (toRemove == null) return false; //fail to find

		//does not affect sort order
		customers.remove(toRemove); 		
		customerIDs.removeID(toRemove.getEmployeeID());
		return true;
	}
	
	
	
}
