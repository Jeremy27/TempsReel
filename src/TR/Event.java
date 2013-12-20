package TR;

public class Event{

	private int date;
	private Customer customer;
	private Employee employee;
	
	
	
	public static enum Type {COMING, LEAVING};
	private Type type;
	
	public Event(int date, Customer customer){
		this.date = date;
		this.customer = customer;
	}
	
	public Event(int date, Employee employee){
		this.date = date;
		this.employee = employee;
	}

	public int getDate() {
		return date;
	}

	public void setDate(int date) {
		this.date = date;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	
	public Type getType(){
		return type;
	}
	
	public void setType(Type type){
		this.type = type;
	}
	
	
	
}
