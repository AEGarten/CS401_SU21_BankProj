

public class Money {
	
	private int dollars = 0;
	private int cents = 0;
	private double fraction = 0.0; //each whole number equals one cent
	
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
	public void setDollars(int d) { this.dollars = d; }
	
	public int getCents() { return this.cents; }
	public void setCents(int c) { this.cents = c; }
	
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
	 * Do it in pieces and try to keep digits out of the left side of the decimal
	 * The more digits to the left of the decimal, the less on the right and the more inaccurate the double
	 * Converting dollars to cents puts more digits to the left of the decimal, so don't
	 * Example
	 * 23.196 * 0.1 = 2.3196  //full calculation
	 * 
	 * pieces
	 * 23 * 0.1 = 2.3	//dollars
	 * 0.19 * 0.1 = 0.019	//cents
	 * 0.006 * 0.1 = 0.0006	//fraction
	 * 2.3 + 0.019 + 0.0006 = 2.3196 //same result
	 */
	public Money mult(double d) {
		Money prod = new Money();
		
		//do the pieces
		prod.fraction = this.fraction * d;
		prod.cents = (int) (this.cents * d);	
		prod.dollars = (int) (this.dollars * d);
		
		//add up just the fractions left over
		prod.fraction += this.cents*d - prod.cents; 
		prod.fraction += (this.dollars*d - prod.dollars) * 100;	
		
		resolve(prod);
		return prod;
	}
	
	//mirrors div, see div
	public Money div(double d) {
		Money quot = new Money();
		quot.fraction = this.fraction / d;
		quot.cents = (int) (this.cents / d);	
		quot.fraction += this.cents/d - quot.cents; 
		quot.dollars = (int) (this.dollars / d);
		quot.fraction += (this.dollars/d - quot.dollars) * 100;	
		
		resolve(quot);
		return quot;
	}
	
	//comparison
	public boolean equals(Money m) {
		if (this.dollars == m.dollars && this.cents == m.cents) {
			
			//straight on comparison is too sensitive, use a lower threshold
			double diff = this.fraction - m.fraction;
			double threshhold = 0.001;
			if (diff*diff < threshhold*threshhold) return true; //square always positive
		}
		return false;
	}
	
	public boolean isGreater(Money m) {
		if (this.dollars > m.dollars) return true;
		if (this.dollars == m.dollars) {
			if (this.cents > m.cents) return true;
			if (this.cents == m.cents)
				if ( ((float)this.fraction) > ((float)m.fraction) ) return true; //larger target area
		}
		return false;
	}
	
	//resolves overages where cents holds whole dollars(s)
	private void resolve(Money m) {
		m.cents += (int) m.fraction;
		m.dollars += m.cents / 100;
		m.cents = m.cents % 100;
		m.fraction -= (int) m.fraction;
	}
	
	//To be accurate to within one 0.001 cent, only need to round when > 0.009
	public Money round() {
		Money rounded = new Money(this.dollars, this.cents + 1);
		if (this.fraction > 0.89) return rounded;	//more lenient than 0.9
		else return this;
	}
	
	//rounding propagates errors, only use at the end of a series of calculations
	//better yet, don't use
	public void setAsRounded() {
		if (this.fraction > 0.98) {	//more strict because this is actually changing the number
			this.fraction = 0.0;
			this.cents += 1;
			
			resolve(this);
		}
	}
	
	public String toString() {
		Money report = new Money(this);
		report = report.round();
		
		return report.dollars + "." + report.cents;	//$ omitted to give users the choice
	}
}
