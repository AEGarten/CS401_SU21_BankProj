
public class Enums {

}

enum Process {
	DEPOSIT,			//Teller or ATM
	WITHDRAWAL,			//Teller or ATM
	TRANSFER,			//Teller or ATM
	TRANSFER_TOCUSTOMER,	//Teller: when transferring to a different customer at this same bank
	BALANCE,			//ATM only
	CLOSE_ACCOUNT,		//Teller only
	ADD_ACCOUNT,		//Teller only
	NEW_CUSTOMER,		//Teller only
	CLOSE_CUSTOMER,		//Teller only
	ADD_EMPLOYEE,		//Supervisor only
	REMOVE_EMPLOYEE,	//Supervisor only
	CHANGE_PASSWORD,	//Supervisor only
	DIVIDEND,			//Supervisor only
	ONLINE,				//Supervisor only
	SHUTDOWN,			//Supervisor only
	SAVE,				//Supervisor only
	LOAD,				//Supervisor only
	LOGIN,				//Teller or ATM
	LOGOUT, 			//Teller or ATM
	ACCESS,				//Teller: when an employee wants Access to a customer
	DISMISS,			//Teller: when a customer is finished with Teller (Customer logout)
	
	EMPLOYEE_ACCESS,	//Supervisor only
	EMPLOYEE_DISMISS	//Supervisor only
}

enum EmployeeType { EMPLOYEE, SUPERVISOR }

enum AccountType { SAVINGS, CHECKING }

