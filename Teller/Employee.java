

public class Employee {

	private String name;
	private int employeeID;
	private String loginusername;
	private String loginpwd;
	private EmployeeType type;
	
	
	public Employee() {
		this.name = "";
		this.employeeID = 0;
		this.setLoginusername("");
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


	public int getEmployeeID() {return employeeID;}

	public void setEmployeeID(int employeeID) {this.employeeID = employeeID;}



	public String getLoginpwd() {return loginpwd;}
	public void setLoginpwd(String loginpwd) {this.loginpwd = loginpwd;}

		

	public EmployeeType getType() { return this.type; }
	
	public void setType(EmployeeType type) { this.type = type; }

	
	
	public String getLoginusername() { return loginusername; }

	public void setLoginusername(String loginusername) { this.loginusername = loginusername; }

}
