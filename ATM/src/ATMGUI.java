import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.UnknownHostException;

public class ATMGUI {
	
	public static void main(String[] args) throws UnknownHostException, ClassNotFoundException, IOException {
		
		String[] credentials = loginGUI();
		ATM atm = new ATM();
		
		boolean successmessage = atm.login(credentials[0], credentials[1]);
		if(successmessage) {
			ATMGUI frameTabel = new ATMGUI(atm);
		}
	}
	public ATMGUI(ATM atm) throws IOException, ClassNotFoundException{
		 String[] commands = {"Deposit",
				 	"Withdrawal",
				 	"Transfer Funds",
				 	"View Balance",
				 	"Logout"};
		 
		 int choice;
		 
		 do {
			 choice = JOptionPane.showOptionDialog(null,
					 "Select a command", 
					 "ATM", 
					 JOptionPane.YES_NO_CANCEL_OPTION, 
					 JOptionPane.QUESTION_MESSAGE, 
					 null, 
					 commands,
					 commands[commands.length - 1]);
		 
			 switch (choice) {
			 	case 0: atm.deposit();break;
			 	case 1: atm.withdrawal();break;
			 	case 2: atm.transferFunds();break;
			 	case 3: atm.viewBalance();break;
			 	case 4: atm.logout();break;
			 	default:  // do nothing
			 }
			 
		 } while (choice != commands.length-1);
		 System.exit(0);
		
	}
	
	public static String[] loginGUI() {
		String loginID = "";
		String Pin = "";
		String[] credentials = new String[2];
		loginID = JOptionPane.showInputDialog("Enter Card ID");
		if (loginID == null) {
			return null;		// dialog was cancelled
		}
		
		Pin = JOptionPane.showInputDialog("Enter Pin");
		if (Pin == null) {
			return null;
		}
		credentials[0] = loginID;
		credentials[1] = Pin;
		return credentials;
	}

	
}
