import java.util.Random;

public class CheckValidation {
	private static Random rand = new Random();
	
	public boolean validateCheck() {
		int chances = 1 + rand.nextInt(101);
		
		if (chances > 95) return false;
		return true;
	}
}
