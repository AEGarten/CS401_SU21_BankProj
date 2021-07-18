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
	private static DataBase db = new DataBase();
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
				serverSocket.setReuseAddress(true);
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
		int sessionID = 0;	//continues session after closing connection w/o login each time
		ClientInfo clientInfo = new ClientInfo(null, sessionID, false);	//clientType, re: Customer id, isSupervisor
		boolean closeConnection = false;	//close out client connection
		
		public ClientHandler(Socket newClient) { this.client = newClient; }
		
		
		@Override
		public void run() {
			System.out.println("New client connected");
			
			try (
				ObjectInputStream frClient = new ObjectInputStream(client.getInputStream());
				ObjectOutputStream toClient = new ObjectOutputStream(client.getOutputStream());
			){
				while (!closeConnection) {
System.out.println("validating ...");				
					Message msgOut = null;
					Message msgIn = (Message) frClient.readObject();
					
					//if not validated, attempt login
					if (!validated) {
						

						if (msgIn instanceof ATMLogin) {
System.out.println("ATM type ...");							
							//fail out if not online
							if (!online) {
								msgOut = fail(msgIn, "Server not Online");
								closeConnection = true;
								break;
							}
							
							ATMLogin ATMmsg = (ATMLogin) msgIn;
							clientInfo.reCustomerID = db.getCustomerFromCard(ATMmsg.cardID);
							
							//if 0 then could be null, good point to stop instead of synch for db access
							if (clientInfo.reCustomerID <= 0) {
								toClient.writeObject(fail(msgIn, "invalid Card ID"));
								break;
							}
							
							msgOut = validateATM(ATMmsg);
							toClient.writeObject(msgOut);	//response

							

							msgIn = (Message) frClient.readObject(); //new input

						}
						//if from Teller
						else if (msgIn instanceof TellerLogin) {
							TellerLogin Tmsg = (TellerLogin) msgIn;
							
							//TODO update when employee updated
							if (Tmsg.login.equals("Login") && Tmsg.password.equals("Password")) {
								validated = true;
								clientInfo = new ClientInfo(ClientType.TELLER, 0, false); //no Customer to access yet, so 0
								sessionID = Server.reserveSessionID(clientInfo);
								
								if (!online && !clientInfo.isSupervisor) {
									msgOut = fail(msgIn, "Server not Online, Supervisor access required");
									closeConnection = true;
									break;
								}
								
								msgOut = new TellerLogin(sessionID, msgIn.id, true);
								
								toClient.writeObject(msgOut);	//response
								msgIn = (Message) frClient.readObject(); //new input
							}
						}	
							
						else break;	//no login, no valid sessionID, close connection
					}
					//by now should be valid, if not send msgIn back (already has msg.success==false)
					if (validated) {
						switch(msgIn.perform) {
						
						case LOGOUT: msgOut = logout((Logout) msgIn); break;
						
						case ACCESS: msgOut = access((CustomerAccess) msgIn); break;

							
	//					case ADD_ACCOUNT:
	//						break;
	//					case ADD_EMPLOYEE:
	//						break;
						
						case BALANCE: msgOut = balance((Balance) msgIn);  break;
							
	//					case CHANGE_PASSWORD:
	//						break;
	//					case CLOSE_ACCOUNT:
	//						break;
	//					case CLOSE_CUSTOMER:
	//						break;
	//					case DEPOSIT:
	//						break;
						
						case DISMISS: msgOut = dismiss((Dismiss) msgIn); break;

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
						default: toClient.writeObject(fail(msgIn, "invalid Process")); break;
							

						}
					}
					
					else toClient.writeObject(fail(msgIn, "need Login object"));
				}

			}
			catch (ClassNotFoundException e) { e.printStackTrace(); }
			catch (IOException e) { e.printStackTrace(); }
			finally {
				try { if (client != null) client.close(); } 
				catch (Exception e) { e.printStackTrace(); }
System.out.println("Thread closed");
			}
			
			
		}
		
		public Message fail(Message m, String why) { return new Message(m, why); }
		
		public ATMLogin validateATM(ATMLogin in) {
			Customer customer = db.findCustomer(clientInfo.reCustomerID);
			if (in.PIN == customer.getPIN()) {
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
							if (a.getCardID() == in.cardID) {
								checkID = a.getID();
								checkPosStat = a.isPositiveStatus();
								
								if (gotSavings) break; //if already got Savings, then done
								gotChecking = true;
							}
						}
						else if (!gotSavings && a.getType() == AccountType.CHECKING) {
							if (a.getCardID() == in.cardID) {
								savID = a.getCardID();
								savPosStat = a.isPositiveStatus();
								
								if (gotChecking) break; //if already got Checking, then done
								gotSavings = true;
							}
							
						}
					}
				}
				
				ATMLogin out = new ATMLogin(checkID, savID, checkPosStat, savPosStat, sessionID, in);
				
				return out;
			}
			return new ATMLogin(in, "no attached card"); //fail
		}
		
		public Logout logout(Logout in) {
			sessionIDs.remove(sessionID);
			closeConnection = true;
			return new Logout(in);
		}
		
		public CustomerAccess access(CustomerAccess in) {
			CustomerAccess out;
			in.customer = db.findCustomer(in.customerID);
			
			if ( in.customer == null || 
					!in.passcode.equals(in.customer.getPasscode()) ) 
						out = new CustomerAccess(in, "no matching customer");
			else {
				out = new CustomerAccess(in.customer, in);
				clientInfo = new ClientInfo(ClientType.TELLER, in.customer.getID(), false);
			}
			return out; 
		}
		
		public Dismiss dismiss(Dismiss in) {
			return new Dismiss(in);
		}
		
		//need to know customer before can act on acctID
		public Message balance(Balance in) {
			Customer cust = db.findCustomer(clientInfo.reCustomerID);
			if (cust == null) return fail(in, "no matching Customer");
			Account acct = cust.findAccount(in.accountID);
			if (acct == null) return fail(in, "no matching Account");
			Message out = new Balance(acct.getBalance(), in);
			closeConnection = true;
			return out;

		}
			
	}
	
	
	
	
	
}

