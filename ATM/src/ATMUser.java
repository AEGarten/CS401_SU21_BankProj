
public class ATMUser {

	private int ATMUserID;
	private int ATMpin;
	private int checkingID;
	private int savingsID;
	private boolean checkingPositive;
	private boolean savingsPositive;
	private int checkNumber; //for deposits
	
	public ATMUser() {
		this.setATMUserID(0);
		this.setATMpin(0);
		this.setCheckingID(0);
		this.setSavingsID(0);
		this.setCheckingPositive(true);
		this.setSavingsPositive(true);
		this.checkNumber = 0;
	}
	
	public ATMUser(int ATMUserID, int ATMpin, int checkingID, int savingsID, boolean checkingPositive, boolean savingsPositive, int checkNumber) {
		this.ATMUserID = ATMUserID;
		this.ATMpin = ATMpin;
		this.checkingID = checkingID;
		this.savingsID = savingsID;
		this.checkingPositive = checkingPositive;
		this.savingsPositive = savingsPositive;
		this.checkNumber = checkNumber;
	}

	public int getATMUserID() {return ATMUserID;}
	public void setATMUserID(int aTMUserID) {ATMUserID = aTMUserID;}
	public int getCheckingID() {return checkingID;}
	public void setCheckingID(int checkingID) {this.checkingID = checkingID;}
	public int getSavingsID() {return savingsID;}
	public void setSavingsID(int savingsID) {this.savingsID = savingsID;}
	public boolean isCheckingPositive() {return checkingPositive;}
	public void setCheckingPositive(boolean checkingPositive) {this.checkingPositive = checkingPositive;}
	public boolean isSavingsPositive() {return savingsPositive;}
	public void setSavingsPositive(boolean savingsPositive) {this.savingsPositive = savingsPositive;}
	public int getATMpin() {return ATMpin;}
	public void setATMpin(int ATMpin) {this.ATMpin = ATMpin;}
	


}