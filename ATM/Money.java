

public class Money {
	
	private int dollars = 0;
	private int cents = 0;
	private double fraction = 0.0;
	
	public Money() {}
	
	public Money(int dollars, int cents) {
		this.dollars = dollars;
		this.cents = cents;
	}
	
	public Money(Money m) {
		this(m.dollars, m.cents);
		this.fraction = m.fraction;
	}
	
	//setters & getters: dollars, cents
	public int getDollars() { return this.dollars; }
	public int setDollars() { return this.dollars; }
	
	public int getCents() { return this.cents; }
	public int setCents() { return this.cents; }
	
	public double getFraction() { return this.fraction; }
	
	public Money add(Money m) {
		Money sum = new Money();
		sum.cents = this.cents + m.cents;
		sum.dollars = this.dollars + m.dollars;
		sum.fraction = this.fraction + m.fraction;
		
		resolve(sum);
		return sum;
	}
	
	public Money sub(Money m) {
		Money diff = new Money();		
		diff.cents = this.cents - m.cents;
		diff.dollars = this.dollars - m.dollars;
		diff.fraction = this.fraction - m.fraction;

		resolve(diff);
		return diff;
	}
	
	/*
	 * Do it in pieces and try to keep numbers out of the left side of the decimal
	 * The more digits to the left of the decimal, the less on the right and the more inaccurate the double
	 * Converting to cents puts more digits to the left of the decimal
	 * 23.196 * 0.1 = 2.3196
	 * 23 * 0.1 = 2.3
	 * 0.19 * 0.1 = 0.019
	 * 0.006 * 0.1 = 0.0006
	 * 2.3 + 0.019 + 0.0006 = 2.3196
	 */
	
	
	public Money mult(double d) {
		Money prod = new Money();
		prod.fraction = this.fraction * d;
//System.out.println(prod.cents);
		prod.cents = (int) (this.cents * d);	
//System.out.println(prod.cents);
		prod.fraction += this.cents*d - prod.cents; 
//System.out.println(prod.fraction);
		prod.dollars = (int) (this.dollars * d);
//System.out.println(prod.dollars);
		prod.fraction += (this.dollars*d - prod.dollars) * 100;	
//System.out.println(prod.fraction);
		
		
		resolve(prod);
//System.out.println(prod.fraction);
		return prod;
	}
	
//	public Money div(double d) {
//		Money quot = new Money();
//		quot.cents = (int) (this.cents / d);
//		quot.dollars = (int) (this.dollars / d);
//		
//		resolve(quot);
//		return quot;
//	}
	
	//comparison
	public boolean equals(Money m) {
		if (this.dollars == m.dollars && this.cents == m.cents) {
			double diff = this.fraction - m.fraction;
			double threshhold = 0.0001f;
			if (diff*diff < threshhold*threshhold) return true; //square always positive
		}
		return false;
	}
	
	public boolean isGreater(Money m) {
		if (this.dollars > m.dollars) return true;
		if (this.cents > m.cents) return true;
		if ( ((float)this.fraction) > ((float)m.fraction) ) return true; //larger target area
		
		return false;
	}
	
	//resolves overages where cents holds whole dollars(s)
	public void resolve(Money m) {
		m.cents += (int) m.fraction;
		m.dollars += m.cents / 100;
		m.cents = m.cents % 100;
		m.fraction -= (int) m.fraction;
	}
	
	//To be accurate to within one 0.001 cents, only need to round when > 0.009
	public Money round() {
		Money rounded = new Money(this.dollars, this.cents + 1);
		if (this.fraction > 0.9) return rounded;
		else return this;
	}
	
	//rounding propagates errors, only use at the end of a series of calculations
	public void setAsRounded() {
		if (this.fraction > 0.99) {
			this.fraction = 0.0;
			this.cents += 1;
			
			resolve(this);
		}
	}
	
	public String toString() {
		Money report = new Money(this);
		report = report.round();
		
		return report.dollars + "." + report.cents;
	}
}
