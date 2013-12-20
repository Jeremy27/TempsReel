package TR;

import java.util.LinkedList;

@SuppressWarnings("serial")
public class CustomerQueue extends LinkedList<Customer>{

	private int maxSize;
	
	public CustomerQueue(int maxSize){
		this.maxSize = maxSize;
	}

	public boolean isFull(){
		return this.maxSize == this.size();
	}
}
