import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Date;
import java.util.Random;

public class Server {
	
	enum ClientType { ATM, TELLER }
	
	private static int idCount = 0;
	private static Random rand = new Random();
	private static int port;
//	private static DataBase db;
	
	public Server(int port) throws IOException {
		Server.port = port;
		new Thread(new ConnectionListener()).start();
	}
	
	public static int getSessionID() {
		Server.idCount += 1 + rand.nextInt(100);
		return Server.idCount;
	}

	private static class ConnectionListener implements Runnable {
	
		@Override
		public void run() {
				Socket newClient = null;
			
			try (
				ServerSocket serverSocket = new ServerSocket(Server.port);
			){
				while (true) {
					newClient = serverSocket.accept();
					new Thread(new ClientHandler(newClient)).start();
				}
			}
			catch (IOException e) { e.printStackTrace(); }
			finally {
				try { if (newClient != null) newClient.close(); }
				catch(Exception e) {}
			}
				
		}
	}
	
	private static class ClientHandler implements Runnable {
		private final Socket client;
		private int sessionID;
		ClientType type;
		boolean supervisorAccess;
		
		public ClientHandler(Socket newClient) { this.client = newClient; }
		
		
		@Override
		public void run() {
			try (
				ObjectInputStream frClient = new ObjectInputStream(client.getInputStream());
				ObjectOutputStream toClient = new ObjectOutputStream(client.getOutputStream());
			){
				boolean validated = false;
				Message msgOut = null;
				Message msgIn = (Message) frClient.readObject();
				
				
				//if not validated, attempt login
				if (!validated) {
					if (msgIn.perform == Process.LOGIN) {
						
						//if from ATM
						if (msgIn.packet instanceof ATMPacket) {
							if (msgIn.authentication.equals("1234") && msgIn.packet.id == 567890) {
								validated = true;
								this.sessionID = Server.getSessionID();
								this.type = ClientType.ATM;
								msgOut = new Message(this.sessionID, msgIn.id, true);
								
								//TODO: replace with ATMPacket constructor when deployed
								ATMPacket apkt = new ATMPacket();
								apkt.checkingID = 92837;
								apkt.savingsID = 52704706;
								apkt.checkingPositive = true;
								apkt.checkingPositive = true;
								msgOut.packet = apkt;
								
								toClient.writeObject(msgOut);
							}
						}
						//if from Teller
						else {
							if (msgIn.authentication.equals("Login, Password")) {
								validated = true;
								this.sessionID = Server.getSessionID();
								this.type = ClientType.TELLER;
								this.supervisorAccess = false;
								msgOut = new Message(this.sessionID, msgIn.id, true);
								toClient.writeObject(msgOut);
							}
						}
					}					
				}
				//by now should be valid, if not send msgIn back (already has msg.success==false)
				if (validated) {
					switch(msgIn.perform) {
					
					case LOGOUT: msgOut = logout(msgIn); break;
					
//					case ACCESS: 
//						break;
//					case ADD_ACCOUNT:
//						break;
//					case ADD_EMPLOYEE:
//						break;
//					case BALANCE:
//						break;
//					case CHANGE_PASSWORD:
//						break;
//					case CLOSE_ACCOUNT:
//						break;
//					case CLOSE_CUSTOMER:
//						break;
//					case DEPOSIT:
//						break;
//					case DISMISS:
//						break;
//					case DIVIDEND:
//						break;
//					case GET_ID:
//						break;
//					case LOAD:
//						break;
//					case LOGIN:
//						break;
//					case NEW_CUSTOMER:
//						break;
//					case ONLINE:
//						break;
//					case PENDING:
//						break;
//					case REMOVE_EMPLOYEE:
//						break;
//					case SAVE:
//						break;
//					case SHUTDOWN:
//						break;
//					case TRANSFER:
//						break;
//					case TRANSFER_TOCUSTOMER:
//						break;
//					case WITHDRAWAL:
//						break;
					default: toClient.writeObject(msgIn); break;
						
					}
					
					toClient.writeObject(msgOut);
				}
				else toClient.writeObject(msgIn);

				
			}
			catch (ClassNotFoundException e) { e.printStackTrace(); }
			catch (IOException e) { e.printStackTrace(); }
			finally {
				try { if (client != null) client.close(); } 
				catch (Exception e) { e.printStackTrace(); }
			}
			
			
		}
		
		public Message logout(Message in) {
			Message out = new Message(this.sessionID, in.id, true);
			this.sessionID = 0;
			return out;
		}
			
	}
	
	
	
	
	
}

