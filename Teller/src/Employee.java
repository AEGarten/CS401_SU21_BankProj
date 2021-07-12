

public class Employee {

	private String name;
<<<<<<< HEAD:Teller/Employee.java
	private int employeeID;
	private String loginusername;
=======
	private final String loginID;
>>>>>>> main:Teller/src/Employee.java
	private String loginpwd;
	private EmployeeType type;
	
	
	public Employee() {
		this.name = "";
		this.employeeID = 0;
		this.loginusername = "";
		this.loginpwd = "";
	        this.type = EmployeeType.EMPLOYEE;
	}
	
	public Employee(String name, int employeeID, String loginusername, String loginpwd) {
		this.name = name;
		this.employeeID = employeeID;
		this.loginusername = loginusername;
		this.loginpwd = loginpwd;
	}

	public String getName() {return name;}
	public void setName(String name) {this.name = name;}

<<<<<<< HEAD:Teller/Employee.java
	public int getEmployeeID() {return employeeID;}

	public void setEmployeeID(int employeeID) {this.employeeID = employeeID;}

=======
>>>>>>> main:Teller/src/Employee.java
	public String getLoginpwd() {return loginpwd;}
	public void setLoginpwd(String loginpwd) {this.loginpwd = loginpwd;}

	public String getLoginusername() {return loginusername;}

	public void setLoginusername(String loginusername) {this.loginusername = loginusername;}
	
<<<<<<< HEAD:Teller/Employee.java
	public EmployeeType getType() { return this.type; }
	
	public void setType(EmployeeType type) { this.type = type; }
=======
	public String getLoginID() {return loginID;}
>>>>>>> main:Teller/src/Employee.java
	
	
}
