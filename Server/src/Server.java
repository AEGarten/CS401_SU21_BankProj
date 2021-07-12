import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class Server {
	
	enum ClientType { ATM, TELLER }
	
	private static int idCount = 0;
	private static Random rand = new Random();
	private static int port;
	private static DataBase db;
	private static boolean stopping = false;
	private static boolean online = true;
	//TODO add shutdown precaution when active (mutators) Clients present
	
	//for keeping track of clients, what type, re: what Customer, supervisor status; all mapped to session id
	private static ConcurrentHashMap<Integer, ClientInfo> sessionIDs = new ConcurrentHashMap<>();
	
	public Server(int port) throws IOException {
		Server.port = port;
		new Thread(new ConnectionListener()).start();
	}
	
	//keeps track of various info for each client in session
	public static class ClientInfo{
		public ClientType type;
		public int reCustomerID;
		public boolean isSupervisor;
		
		public ClientInfo(ClientType ct, int id, boolean isSupe) {
			this.type = ct;
			this.reCustomerID = id; //re: concerning what customer
			this.isSupervisor = isSupe;
		}
	}
	
	public static synchronized void Stop() { 	
		stopping = true;
		System.out.println("Server stopping");
	}
	
	private static synchronized int reserveSessionID(ClientInfo ci) {
		Server.idCount += 1 + rand.nextInt(50);
		sessionIDs.put(Server.idCount, ci);
		return Server.idCount;
	}

	private static class ConnectionListener implements Runnable {
	
		@Override
		public void run() {
			System.out.println("Server running");
			Socket newClient = null;
			
			try (
				ServerSocket serverSocket = new ServerSocket(Server.port);
			){
				while (!stopping) {
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
		private boolean validated = false; //has the clients credentials been validated
		ClientInfo clientInfo;	//clientType, re: Customer id, isSupervisor
		int sessionID = 0;	//continues session after closing connection w/o login each time
		
		public ClientHandler(Socket newClient) { this.client = newClient; }
		
		
		@Override
		public void run() {
			try (
				ObjectInputStream frClient = new ObjectInputStream(client.getInputStream());
				ObjectOutputStream toClient = new ObjectOutputStream(client.getOutputStream());
			){
				
				boolean closeOut = false;	//close out client connection
				while (!closeOut) {
				
					Message msgOut = null;
					Message msgIn = (Message) frClient.readObject();
					
					//if not validated, attempt login
					if (!validated) {
						if (msgIn.perform == Process.LOGIN) {
							
							//if from ATM
							if (msgIn.packet instanceof ATMPacket) {
								
								//fail out if not online
								if (!online) {
									msgOut = fail(msgIn);
									msgOut.perform = Process.SHUTDOWN;
									closeOut = true;
									break;
								}
								
								clientInfo.reCustomerID = db.getCustomerFromCard(
										msgIn.packet.actOnID		//should be card id
										);
								
								//if 0 then could be null, good point to stop instead of synch for db access
								if (clientInfo.reCustomerID <= 0) {
									toClient.writeObject(fail(msgIn));
									break;
								}
								
								msgOut = validateATM(msgIn);
								toClient.writeObject(msgOut);	//response
								msgIn = (Message) frClient.readObject(); //new input
								
							}
							//if from Teller
							else {
								
								//TODO update when employee updated
								if (msgIn.authentication.equals("Login, Password")) {
									validated = true;
									clientInfo = new ClientInfo(ClientType.TELLER, 0, false); 
									sessionID = Server.reserveSessionID(clientInfo);
									
									if (!online && !clientInfo.isSupervisor) {
										msgOut = fail(msgIn);
										msgOut.perform = Process.SHUTDOWN;
										closeOut = true;
										break;
									}
									
									msgOut = new Message(sessionID, msgIn.id, true);
									
									toClient.writeObject(msgOut);	//response
									msgIn = (Message) frClient.readObject(); //new input
								}
							}
						}
						
						//no login, see if valid sessionID
						else if (sessionIDs.contains(msgIn.sessionID)) {
							clientInfo = sessionIDs.get(msgIn.sessionID);
							
							//if shutdown, send back shutdown and close out connection
							//still allow supervisor access to bring back online
							if (!online && !clientInfo.isSupervisor) {
								sessionID = 0;
								msgOut = fail(msgIn);
								msgOut.perform = Process.SHUTDOWN;
								closeOut = true;
								break;
							}
							
							validated = true;
							sessionID = msgIn.sessionID;
						}
						else break;	//no login, no valid sessionID, close connection
					}
					//by now should be valid, if not send msgIn back (already has msg.success==false)
					if (validated) {
						switch(msgIn.perform) {
						
						case LOGOUT: {
							msgOut = logout(msgIn);
							closeOut = true;
						} break;
						
						case ACCESS: msgOut = access(msgIn); break;
							
	//					case ADD_ACCOUNT:
	//						break;
	//					case ADD_EMPLOYEE:
	//						break;
						
						case BALANCE: {
							msgOut = balance(msgIn); 
							closeOut = true;
						} break;
							
	//					case CHANGE_PASSWORD:
	//						break;
	//					case CLOSE_ACCOUNT:
	//						break;
	//					case CLOSE_CUSTOMER:
	//						break;
	//					case DEPOSIT:
	//						break;
						
						case DISMISS: msgOut = dismiss(msgIn); break;

	//					case DIVIDEND:
	//						break;
	//					case LOAD:
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
						
					}
					else toClient.writeObject(fail(msgIn));
				}
			}
			catch (ClassNotFoundException e) { e.printStackTrace(); }
			catch (IOException e) { e.printStackTrace(); }
			finally {
				try { if (client != null) client.close(); } 
				catch (Exception e) { e.printStackTrace(); }
			}
			
			
		}
		
		public Message fail(Message m) { return new Message(sessionID, m.id); }
		
		public Message success(int sessionID, int id) { return new Message(sessionID, id, true); }
		
		public Message validateATM(Message in) {
			Customer customer = db.findCustomer(clientInfo.reCustomerID);
			if (in.authentication.equals(customer.getPIN())) {
				validated = true;
				clientInfo = new ClientInfo(ClientType.ATM, customer.getID(), false);
				sessionID = reserveSessionID(clientInfo);
				
				
				ArrayList<Account> accts = customer.getAccounts();
				
				//search this Customer's Accounts for ATMPacket values attached to their validated card
				int checkID = 0, savID = 0;
				boolean checkPosStat = false, savPosStat = false;
				boolean gotChecking = false, gotSavings = false;	//slight optimization
				for (Account a: accts) {
					if (a.hasAttachedCard()) {
						
						if (!gotChecking && a.getType() == AccountType.CHECKING) {
							if (a.getCardID() == in.packet.actOnID) {
								checkID = a.getID();
								checkPosStat = a.isPositiveStatus();
								
								if (gotSavings) break; //if already got Savings, then done
								gotChecking = true;
							}
						}
						else if (!gotSavings && a.getType() == AccountType.CHECKING) {
							if (a.getCardID() == in.packet.actOnID) {
								savID = a.getCardID();
								savPosStat = a.isPositiveStatus();
								
								if (gotChecking) break; //if already got Checking, then done
								gotSavings = true;
							}
							
						}
					}
				}
				
				Message out = success(sessionID, in.id);
				out.packet = new ATMPacket(checkID, savID, checkPosStat, savPosStat);
				return out;
			}
			return fail(in);
		}
		
		public Message logout(Message in) {
			Message out = success(sessionID, in.id);
			sessionIDs.remove(sessionID);
			return out;
		}
		
		public Message access(Message in) {
			Message out;
			CustomerPacket cust = new CustomerPacket();
			cust.customer = db.findCustomer(in.packet.actOnID);
			
			if (cust.customer == null) out = fail(in);
			else {
				out = success(sessionID, in.id);
				clientInfo = new ClientInfo(ClientType.TELLER, in.packet.actOnID, false);
				out.packet = cust;
			}
			return out; 
		}
		
		public Message dismiss(Message in) {
			Message out = new Message(reserveSessionID(clientInfo), in.id, true);
			sessionIDs.remove(sessionID);
			validated = false;
			return out;
		}
		
		//need to know customer before can act on acctID
		public Message balance(Message in) {
			Customer cust = db.findCustomer(clientInfo.reCustomerID);
			if (cust == null) return fail(in);
			Account acct = cust.findAccount(in.packet.actOnID);
			if (acct == null) return fail(in);
			Message out = new Message(reserveSessionID(clientInfo), in.id, true);
			sessionIDs.remove(sessionID);
			out.packet.amount = acct.getBalance();
			return out;
		}
			
	}
	
	
	
	
	
}

