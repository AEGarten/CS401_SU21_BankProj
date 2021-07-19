import java.io.Serializable;

public class Money implements Serializable {
	
	private int dollars = 0;
	private int cents = 0;
	private double fraction = 0.0; //each whole number equals one cent
	boolean isPositive = true; 		//if 0 => isPositive is true, regardless of correctness
	
	
	public Money() {}
	
	public Money(int dollars, int cents, boolean isPositive) {
		this.dollars = dollars;
		this.cents = cents;
		this.isPositive = isPositive;
		
		if (this.dollars < 0 || this.cents < 0) {
			this.dollars = Math.abs(this.dollars);
			this.cents = Math.abs(this.dollars);
			this.isPositive = false;
		}
	}
	
	public Money(Money m) {
		this(m.dollars, m.cents, m.isPositive);
		this.fraction = m.fraction;
	}
	
	
	//setters & getters: dollars, cents
	public int getDollars() { return this.dollars; }
	public void setDollars(int d) { this.dollars = d; }
	
	public int getCents() { return this.cents; }
	public void setCents(int c) { this.cents = c; }
	
	public boolean isPositive() { return this.isPositive; }
	public void setIsPositive(boolean p) { this.isPositive = p; }
	
	public double getFraction() { return this.fraction; }
	
	
	public Money add(Money m) {
		
		//if either negative treat differently
		if (!this.isPositive || !m.isPositive) return negAdd(m);
		
		Money sum = new Money();
		sum.cents = this.cents + m.cents;
		sum.dollars = this.dollars + m.dollars;
		sum.fraction = this.fraction + m.fraction;
		
		resolve(sum);
		return sum;
	}
	
	public Money sub(Money m) {
		//if either negative handle differently
		if (!this.isPositive || !m.isPositive) return this.negSub(m);
		
		/*
		 * Example types of calculations
		 * $ 12.130 - 13.000 = -0.870
		 * $ 12.130 - 13.141 = -1.001
		 * $ 12.130 - 12.140 = -0.010
		 * $ 12.130 - 12.131 = -0.001
		 * $ 12.130 - 12.130 =  0.000
		 * $ 12.130 - 11.141 =  0.989
		 * $ 12.130 - 11.140 =  0.990
		 * 
		 * if this < m, swap arguments
		 * if place (fraction/cents) < 0, then add difference to one unit next up
		 */
		
		Money diff = new Money();
		if (m.isGreater(this)) {
			diff.fraction = m.fraction - this.fraction;
			if (diff.fraction < 0.0) {
				diff.fraction += 1.0;
				diff.cents -= 1;
			}
			diff.cents += m.cents - this.cents;
			if (diff.cents < 0) {
				diff.cents += 100;
				diff.dollars -= 1;
			}
			diff.dollars += m.dollars - this.dollars;
			diff.isPositive = !diff.isPositive;
		}
		else {
			diff.fraction = this.fraction - m.fraction;
			if (diff.fraction < 0.0) {
				diff.fraction += 1.0;
				diff.cents -= 1;
			}
			diff.cents += this.cents - m.cents;
			if (diff.cents < 0) {
				diff.cents += 100;
				diff.dollars -= 1;
			}
			diff.dollars += this.dollars - m.dollars;
		}
		
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
		if (d < 0.0) {
			this.isPositive = !this.isPositive;
			d = Math.abs(d);
		}
		
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
	
	//mirrors mult, see mult
	public Money div(double d) {
		Money quot = new Money();
		
		if (d < 0.0) {
			this.isPositive = !this.isPositive;
			d = Math.abs(d);
		}
		
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
		if (this.isPositive != m.isPositive) return false;
		if (this.dollars == m.dollars && this.cents == m.cents) {
			
			//straight on comparison is too sensitive, use a lower threshold
			double diff = this.fraction - m.fraction;
			double threshhold = 0.001;
			if (Math.abs(diff) < threshhold) return true; //square always positive
		}
		return false;
	}
	
	public boolean isGreater(Money m) {
		
		//if signs different, compare signs
		if (this.isPositive ^ m.isPositive) {
			if (this.isPositive && !m.isPositive) return true;
			if (!this.isPositive && m.isPositive) return false;
		}
		
		if (this.dollars > m.dollars) return true;
		if (this.dollars == m.dollars) {
			if (this.cents > m.cents) return true;
			if (this.cents == m.cents)
				if ( ((float)this.fraction) > ((float)m.fraction) ) return true; //larger target area
		}
		return false;
	}
	
	//resolves overages where cents holds whole dollars(s)
	//resolves sign of zero
	private void resolve(Money m) {
		m.cents += (int) m.fraction;
		m.dollars += m.cents / 100;
		m.cents = m.cents % 100;
		m.fraction -= (int) m.fraction;
		
		//edge case of 0.000 and isPositive false will cause problems
		if (m.dollars == 0 && 
				m.cents == 0 && 
				Math.abs(m.fraction) < 0.001) {
			m.isPositive = true;
		}
	}
	
	//absolute value of the Money
	public Money absMoney() {
		Money abs = new Money(this);
		abs.setIsPositive(true);
		return abs;
	}
		
	/*
	 * Examples of diffSignAdd
	 *  -3 + 4 = 1	=> 4 - 3 = 1
	 *  -4 + 3 = -1	=> 3 - 4 = -1
	 *  3 + -4 = -1	=> 3 - 4 = -1
	 *  4 + -3 = 1	=> 4 - 3 = 1
	 *  
	 *  converts all ops to between absolute values
	 *  subtract neg from pos, if both neg add and set negative
	 */
	private Money negAdd(Money m) {
		
		if (this.isPositive) return this.sub(m.absMoney());
		else {
			if (m.isPositive) return m.sub(this.absMoney());
			else {
				Money result = this.absMoney().add(m.absMoney());
				result.isPositive = false;
				return result;
			}
		}
	}
		
	/*  
	 *  Example negSub
	 *  -3 - 4 = -7		=> 3 + 4 = 7	=> -7
	 *  -4 - 3 = -7		=> 4 + 3 = 7 	=> -7
	 *  
	 *  4 - -3 = 7		=> 4 + 3 = 7
	 *  3 - -4 = 7		=> 3 + 4 = 7
	 *  
	 *  -4 - -3 = -1	=> 4 - 3 = 1	=> -1
	 *  -3 - -4 = 1		=> 3 - 4 = -1	=> 1
	 *  
	 * converts all operation to between absolute values 
	 * if this positive, add 
	 * else reverse the result sign of	..subtraction if same signs
	 * 									..addition if different signs 
	 */
	private Money negSub(Money m) {
		if (!this.isPositive) {
			Money result;
			if (!m.isPositive) result = this.absMoney().sub(m.absMoney());
			else result = this.absMoney().add(m);
			
			result.isPositive = !result.isPositive;
			return result;
		}
		else return this.add(m.absMoney());
	}
	
	//To be accurate to within one 0.001 cent, only need to round when > 0.009
	public Money round() {
		Money rounded = new Money(this.dollars, this.cents + 1, this.isPositive);
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
		String cents = String.format("%02d", report.cents);
		
		return (isPositive ? "" : "-") + report.dollars + "." + cents;	//$ omitted to give users the choice
	}
}
