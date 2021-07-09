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
	private String sessionID;
	private String tellerID;
	private String tellerPW;
	private String customerID;
	private String customerPW;
	private Boolean connected;
	private Customer customer;
	Scanner scan = new Scanner(System.in);
	OutputStream outputstream;
	ObjectOutputStream objectOutputStream;

	public Teller() {
		employee = new Employee();
		customer = new Customer();
		sessionID = "", tellerID = "", tellerPW = "", customerID = "", customerPW = "";
		connected = false;
		outputstream = socket.getOutputStream();
		objectOutputStream = new ObjectOutputStream(outputstream);
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
			System.out.println("Enter User Account ID: ");
			tellerID = scan.nextLine();
			nessagesOut.add(new Message(tellerID));
			System.out.println("Enter User Password: ");
			tellerPW = scan.nextln();
			messagesOut.add(new Message(tellerPW));

			// Send ID/PW to server for verification
			objectOutputStream.writeObject(messagesOut);

			// Receive server response
			InputStream inputStream = socket.getInputStream();
			ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);

			// True if connected / False if failed to log in
			connected = (Boolean) objectInputStream.readBoolean();
			System.out.println("Teller logged in.");
		}
	}

	public void accessCustomer() {
		while (!connected) {
			System.out.println("Enter Customer Account ID: ");
			customerID = scan.nextLine();
			nessagesOut.add(new Message(customerID));
			System.out.println("Enter Customer Password: ");
			customerPW = scan.nextln();
			messagesOut.add(new Message(customerPW));

			objectOutputStream.writeObject(messagesOut);

			// Receive server response
			InputStream inputStream = socket.getInputStream();
			ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);

			// True if connected / False if failed to log in
			connected = (Boolean) objectInputStream.readBoolean();
			System.out.println("Custommer logged in.");
			customer = (Customer) objectInputStream.readObject();
		}
		listen();
	}

	public void closeConnection() {
		socket.close();
	}

	public void listen() {
		System.out.println("Transaction: ");
		String listen = scan.nextLine();
		switch (listen) {
		case "DEPOSIT":
			message.perform = Process.DEPOSIT; 
			break;
		case "WITHDRAWAL":
			message.perform = Process.WITHDRAWAL; 
			break;
		case "TRANSFER":
			message.perform = Process.TRANSFER; 
			break;
		case "TRANSFER_TOCUSTOMER":
			System.out.println("Customer Account Number: ")
			String acc = scan.nextLine();
			message.perform = TRANSFER_TOCUSTOMER; 
			break;
		case "BALANCE":
			message.perform = Process.BALANCE; 
			break;
		case "CLOSE_ACCOUNT":
			message.perform = Process.CLOSE_ACCOUNT; 
			break;
		case "LOGOUT":
			message.perform = Process.LOGOUT; 
			break;
		}
	}

	public void send() {
		ObjectOutputStream.writeObject(message.packet.customerID);
		switch (Process.perform) {
		case "DEPOSIT":
			System.out.println("Enter Deposit Amount: ");
			double deposit = scan.nextDouble();
			objectOutputStream.writeObject(message.packet.deposit);
			System.out.println("Enter Which Account: ");
			string acc = scan.nextLine();
			objectOutputStream.writeObject(message.packet.acc);
		case "WITHDRAW":
			System.out.println("Enter Withdraw Amount: ");
			double withdraw = scan.nextDouble();
			objectOutputStream.writeObject(message.packet.withdraw);
			System.out.println("Enter Which Account: ");
			string acc = scan.nextLine();
			objectOutputStream.writeObject(message.packet.acc);
		case "Transfer":
			System.out.println("Enter Transfer Amount: ");
			double deposit = scan.nextDouble();
			objectOutputStream.writeObject(message.packet.transfer);
			System.out.println("Enter Which Account: ");
			string acc = scan.nextLine();
			objectOutputStream.writeObject(message.packet.acc);
		case "BALANCE":
			double amount;
			objectOutputStream.writeObject(message.packet.balance);
			System.out.println("Enter Which Account: ");
			string acc = scan.nextLine();
			objectOutputStream.writeObject(message.packet.acc);
			amount = (double) objectInputStream.readDouble();
		case "CLOSE_ACCOUNT":
			objectOutputStream.writeObject(message.packet.close);
			System.out.println("Enter Which Account: ");
			string acc = scan.nextLine();
			objectOutputStream.writeObject(message.packet.acc);
		case "LOGOUT":
			socket.close();
		}
	}
}