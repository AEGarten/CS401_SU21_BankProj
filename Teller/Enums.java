
public class Enums {

}

enum Process {
	DEPOSIT,
	WITHDRAWAL,
	TRANSFER,
	TRANSFER_TOCUSTOMER,	//when transferring to a different customer at this same bank
	BALANCE,
	CLOSE_ACCOUNT,
	ADD_ACCOUNT,
	NEW_CUSTOMER,
	CLOSE_CUSTOMER,
	ADD_EMPLOYEE,
	REMOVE_EMPLOYEE,
	CHANGE_PASSWORD,
	DIVIDEND,
	PENDING,
	ONLINE,
	SHUTDOWN,
	SAVE,
	LOAD,
	LOGIN,
	LOGOUT,
	ACCESS,	//when an employee wants Access to a customer
	GET_ID	//Most ids come from the server. This indicates an id request.
}

enum EmployeeType { EMPLOYEE, SUPERVISOR }

enum AccountType { SAVINGS, CHECKING }

