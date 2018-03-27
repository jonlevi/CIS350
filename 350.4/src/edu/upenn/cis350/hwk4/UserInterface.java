package edu.upenn.cis350.hwk4;

public abstract class UserInterface {
	
	public abstract void run();
	protected abstract int getIntInput();
	protected abstract double getDoubleInput();
	protected abstract String getStringInput();
	protected abstract void display(String s);
	public void log(String message) {
		Logger.getInstance().log(message);
	}
}
