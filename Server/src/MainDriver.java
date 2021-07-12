import java.io.IOException;
import java.net.InetAddress;
import java.util.Scanner;

public class MainDriver {

	public static void main(String[] args) throws IOException {
		
		final int port = 1234;
		Server server = new Server(port);
		
//		String localhost = InetAddress.getLocalHost().getHostAddress().trim();
//		System.out.println(localhost);
	
		boolean stop = false;
		Scanner input = new Scanner(System.in); 
		while (!stop) {
			 System.out.println("Enter STOP to stop Server");
			 if (input.nextLine().equals("STOP")) {
				 if (Thread.activeCount() < 3) stop = true;
				 else System.out.println("Client connected");
			 } 
		}
		
		
		
		System.out.println("Server stopped");
		System.exit(0);
	}

}
