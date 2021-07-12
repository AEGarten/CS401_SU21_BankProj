

public class Employee {

	private String name;
	private final String loginID;
	private String loginpwd;
	
	
	
	public Employee() {
		this.name = "";
		this.loginID = "";
		this.loginpwd = "";
	}
	
	public Employee(String name, String loginID, String loginpwd) {
		this.name = name;
		this.loginID = loginID;
		this.loginpwd = loginpwd;
	}

	public String getName() {return name;}
	public void setName(String name) {this.name = name;}

	public String getLoginpwd() {return loginpwd;}
	public void setLoginpwd(String loginpwd) {this.loginpwd = loginpwd;}
	
	public String getLoginID() {return loginID;}
	
	
}
