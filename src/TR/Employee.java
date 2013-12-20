package TR;

public class Employee {
	
	private boolean free;
	private int date;
	private int workingTime;
	
	public Employee(){
		free = true;
		workingTime = 0;
	}
	
	public void setBusy(int date){
		this.free = false;
		this.date = date;
	}
	
	public void setFree(int date){
		this.free = true;
		this.date = date;
		workingTime += date - this.date;
	}
	
	public boolean isFree(){
		return free;
	}
	
	public int getWorkingTime(){
		return this.workingTime;
	}
	
	
}
