package TR;

import java.util.ArrayList;

public class Staff {

	private ArrayList<Employee> employees;
	
	public Staff(int staffSize){
		employees = new ArrayList<Employee>(staffSize);
		for(int i = 0; i < staffSize; i++){
			employees.add(new Employee());
		}
	}
	
	public int getSize(){
		return employees.size();
	}
	
	public Employee getFree(){
		for(Employee e: employees){
			if(e.isFree())
				return e;
		}
		return null;
	}
	
	public Employee getEmployee(int i){
		return employees.get(i);
	}
}
