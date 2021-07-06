import java.util.Date;

public class Pending {
	final int id =  (int) (new Date().getTime() - 1625500000000L); //get long to int
	
	public static void main(String[] args) {
	
	System.out.println(new Date().getTime() - 1625500000000L);
	}
}
