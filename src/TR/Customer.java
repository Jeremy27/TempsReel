package TR;

import java.util.Random;

public class Customer {

	private static Random random; 
	private int duration;
	
	public static void initRandom(long seed){
		random = new Random(seed);
	}
	
	public Customer(){
		duration = Customer.random.nextInt(1800);
	}
	
	public int getDuration(){
		return this.duration;
	}
	
}
