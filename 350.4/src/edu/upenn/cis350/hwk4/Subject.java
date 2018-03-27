package edu.upenn.cis350.hwk4;

import java.util.ArrayList;
import java.util.List;

public abstract class Subject {
	private List<Observer> observers = new ArrayList<Observer>();
	
	public void attach(Observer observer){
		observers.add(observer);
	}
	
	public void detach(Observer observer) {
		observers.remove(observer);
	}
	
	public void notifyAllObservers(){
		for (Observer observer : observers) {
			observer.update(this); //send the subject as an argument so the observer can monitor//whatever it needs to
		}
	}
}
