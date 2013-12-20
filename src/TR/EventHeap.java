package TR;

import java.util.ArrayList;

public class EventHeap {

	private ArrayList<Event> events;
	
	public EventHeap(){
		events = new ArrayList<Event>();
	}
	
	public void insert(Event event){
		this.events.add(event);
	}
	
	public boolean isEmpty(){
		return this.events.isEmpty();
	}
	
	public Event getFirst(){
		return events.remove(0);
	}
}
