import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.ultil.*;
import java.util.Scanner;

public class Teller {
	private Socket socket;
	private Employee employee;
	private ArrayList<Message> messagesOut = new ArrayList<Message>();
	private Message message;
	private int sessionID;
	private String tellerID;
	private String tellerPW;
	private int customerID;
	private String customerPW;
	private Boolean connected;
	private Customer customer;
	private int sessionID;
	private Message customerMsg;
	CustomerPacket customerPacket;
	Scanner scan = new Scanner(System.in);
	OutputStream outputstream;
	ObjectOutputStream objectOutputStream;

	public Teller() {
		employee = new Employee();
		customer = new Customer();
		sessionID = "", tellerID = "", tellerPW = "", customerID = "", customerPW = "";
		connected = false;
		message = new Message();
		outputstream = socket.getOutputStream();
		objectOutputStream = new ObjectOutputStream(outputstream);
		objectInputStream = new ObjectInputStream(inputstream);
		try (socket = new Socket("localhost", 1234)){
			while (true) {
				makeConnection();
				accessCustomer();
			}
		} catch (UnknownHostException u) {
			System.out.println(u);
		} catch (IOException i) {
			System.out.println(i);
		}
	}

	public void makeConnection() {
		while (!connected) {
			System.out.println("Enter Employee Account ID: ");
			tellerID = scan.nextLine();

			System.out.println("Enter Employee Password: ");
			tellerPW = scan.nextln();

//			message.authentication = "Login, Password";
//			message.perform = Process.LOGIN;
//			// Send ID/PW to server for verification

			
			TellerLogin tLogin = new TellerLogin("Login", "Password");
			objectOutputStream.writeObject(tLogin);
			// Receive server response
			InputStream inputStream = socket.getInputStream();
			ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);

			// True if connected / False if failed to log in
			TellerLogin receive = (TellerLogin) objectInputStream.readObject();
			// connected = (Boolean) objectInputStream.readBoolean();

			sessionID = receive.sessionID;
			connected = receive.success;
		}
		System.out.println("Teller logged in.");
	}

	public void accessCustomer() {
		while (!connected) {
			System.out.println("Enter Customer Account ID: ");
			customerID = scan.nextLine();
			// messagesOut.add(new Message(customerID));
			System.out.println("Enter Customer Password: ");
			customerPW = scan.nextln();
			// messagesOut.add(new Message(customerPW));

//			customerMsg = new Message(sessionID);
//
//			customerMsg.perform = Process.ACCESS;
//			customerMsg.packet.actOnID = "CustomerID";

			
			CustomerAccess cAccess = new CustomerAccess(sessionID, "Passcode", customerID);
			objectOutputStream.writeObject(cAccess);
			
			// Receive server response
			InputStream inputStream = socket.getInputStream();
			ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);

			CustomerAccess receive = (CustomerAccess) objectInputStream.readObject();

			connected = receive.success;
			customer = receive.customer;
		}
		System.out.println("Customer logged in.");
		listen();
	}

	public void closeConnection() {
		socket.close();
	}

	public void listen() {
		InputStream inputStream = socket.getInputStream();
		ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
		System.out.println("Transaction: ");
		String listen = scan.nextLine();
		Message sendMsg = new Message();
		switch (listen) {
		case "DEPOSIT":
			System.out.println("Accounts: " + customer.getAccounts() + "\nChoose Account: ");
			int acc = scan.nextLine();
			Account account = customer.findAccount(acc);
			
		}
//		case "DEPOSIT":
//			System.out.println("Accounts: " + customer.getAccounts() + "\nChoose Account: ");
//			int acc = scan.nextLine();
//		
//			Account account = customer.findAccount(acc);
//			
//			sendMsg = new Message(sessionID);
//			sendMsg.perform = Process.DEPOSIT;
//			
//			// AccPacket
//			AccountPacket accPacket = new AccountPacket();
//			// sendMsg.packet = accPacket;
//			sendMsg.packet.actOnID = acc; 
//			System.out.println("Is this a check? (Y/N)");
//			String check = scan.nextLine();
//			System.out.println("Enter Check Number");
//			String checkNumber = scan.nextLine();
//			System.out.println("Enter Amount: (Dollars/Pennies)");
//			System.out.println("Enter Dollars: ");
//			double dollar = scan.nextDouble();
//			System.out.println("Enter Pennies: ");
//			double pennies = scan.nextDouble();
//			
//			Money money = new Money(dollar, pennies, true);
//			
//			if (check == "Y") {
//				Pending pend = new Pending(money, checkNumber);
//				account.addPending(pend);
//			}
//			
//			Money newBalance = account.getBalance().add(money);
//			account.setBalance(newBalance);
//			
//			accPacket.account = account;
//			
//			sendMsg.packet = accPacket;
//			Message receive = (Message) objectInputStream.readObject();
//			if (boolean success = receive.success) {
//				System.out.println("Successfully Deposited.");
//				sessionID = receive.sessionID;
//			}
//			
//			break;
//		case "WITHDRAWAL":
//			message.perform = Process.WITHDRAWAL; 
//			break;
//		case "TRANSFER":
//			message.perform = Process.TRANSFER; 
//			break;
//		case "TRANSFER_TOCUSTOMER":
//			System.out.println("Customer Account Number: ")
//			String acc = scan.nextLine();
//			message.perform = TRANSFER_TOCUSTOMER; 
//			break;
//		case "BALANCE":
//			System.out.println(for (var account : customer.getAccounts()) "$"+ account.getBalance()); 
//			break;
//		case "ADD_ACCOUNT":
//			message.perform = Process.ADD_ACCOUNT;
//			objectOutputStream.writeObject(message);
//			Message receive = (Message) objectInputStream.readObject();
//			if (receive.success == true) System.out.println("Successfully Created Account.");
//			int newAccID = receive.packet.actOnID;
//			System.out.println("Checking or Savings:");
//			String response = scan.nextLine();
//			if (response == "CHECKING")	Account account = new Account(newAccID, AccountType.CHECKING);
//			else Account account = new Account(newAccID, AccountType.SAVINGS);
//			AccountPacket newPacket = new AccountPacket();
//			newPacket.account = account;
//			message.packet = newPacket;
//			objectOutputStream.writeObject(message);
//			receive = (Message) objectInputStream.readObject();
//			if (receive.success == true) System.out.println("Successfully Created " + response + " Account.");
//			else System.out.println("Failed to Create New " + response + " Account");
//		case "CLOSE_ACCOUNT":
//			message.perform = Process.CLOSE_ACCOUNT; 
//			break;
//		case "LOGOUT":
//			message.perform = Process.LOGOUT; 
//			break;
//		}
	}
}