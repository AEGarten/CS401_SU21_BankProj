
public class TestMoney {

	public static void main(String[] args) {
		Money m1 = new Money(23, 16);
		Money m2 = new Money(0, 01);
		Money m3 = new Money(1000000, 50);		
		
		
		System.out.println(m3.mult(1.16));
		
//		System.out.println(m3.add(m2));
//		System.out.println(m1.sub(m2));
//		System.out.println(m1.equals(m2));
//		System.out.println(m1.isGreater(m2));
		System.out.println(m1.mult(1.00001).isGreater(m1));
	}

}
