
/*
	 * Packet	//children: ATMPacket, AccountPacket, CustomerPacket, EmployeePacket
	 * --------
	 * +id: int			//for what Customer/Employee/Account/Card ID to be engaged
	 * +amount: Money	//for holding amount of change: Transfer/Deposit/Withdrawal
	 * +target: String	//for transfers, where to?; closing accounts, which account?
	 */

public class ATMPacket extends Packet{
	public int checkingID;
	public int savingsID;
	public boolean checkingPositive;
	public boolean savingsPositive;
	public int checkNumber; //for deposits
}
