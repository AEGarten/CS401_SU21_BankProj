import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ATM {

	private String location;
	private Money localcash;
	private ATMUser user;
	private Socket socketconnection;
	private int sessionID;
	private Message message;
	Scanner sc = new Scanner(System.in);
	
	public ATM() {
		this.location = new String();
		this.localcash = new Money();
		this.message = new Message();
		this.user = new ATMUser();
		this.socketconnection = new Socket();
		this.sessionID = 0;
	}
	
	public ATM(String location, Money localcash, Message message, ATMUser user, Socket socketconnection, int sessionID) {
		this.location = location;
		this.localcash = localcash;
		this.message = message;
		this.user = user;
		this.socketconnection = socketconnection;
		this.sessionID = sessionID;
	}
	
	
	
	public void login() throws UnknownHostException, IOException {
		System.out.println("Enter ATM card ID");
		String ATMID = sc.nextLine();
		System.out.println("Enter ATM pin");
		String ATMpin = sc.nextLine();
		
		socketconnection = new Socket("localhost", 1234);
		
		//message.authentication = ATMpin;
		message.authentication = "1234";
		message.packet.id = 567890;
		//message.packet.id = Integer.parseInt(ATMID);
		message.perform = Process.LOGIN;
		message.packet = new ATMPacket();
		
		//Server.loginvalidation(Message);
		
		OutputStream outputStream = socketconnection.getOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(message);

	}
	
	public void transferFunds() throws IOException {
		message.perform = Process.TRANSFER;
		OutputStream outputStream = socketconnection.getOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(message);
		
	}
	
	public void viewBalance() throws IOException {
		message.perform = Process.BALANCE;
		OutputStream outputStream = socketconnection.getOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(message);
	}
	
	public void deposit() throws IOException {
		System.out.println("Cash or check");
		String deposittender = sc.nextLine();
		System.out.println("Enter Deposit Amount");
		String depositamount = sc.nextLine();
		
		
		message.perform = Process.DEPOSIT;
		OutputStream outputStream = socketconnection.getOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(message);
	}
	
	public void withdrawal() throws IOException {
		
		message.perform = Process.WITHDRAWAL;
		OutputStream outputStream = socketconnection.getOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(message);	
	}
	
	public void listenToMessage(Socket socketconnection) throws IOException, ClassNotFoundException {
		ObjectInputStream inputStream = new ObjectInputStream(socketconnection.getInputStream());
		Message message = (Message) inputStream.readObject();
		printMessage(message);
	}
	
	public String printMessage(Message message) {
		String showmessage = null;
		return showmessage;
	}

}
