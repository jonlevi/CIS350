package edu.upenn.cis350.hwk4;

import java.io.File;
import java.io.FileWriter;

public class Logger {
	private static Logger instance;
	private FileWriter writer;
	private static String logfile;
	
	public static void setFile(String logfile) {
		Logger.logfile = logfile;
	}
	
	private Logger(String filename) {
		writer = null;
		try {
			writer = new FileWriter(new File(filename), true);
			
		}
		catch (Exception e) { e.printStackTrace(); }

	}
	
	public static Logger getInstance() {
		if (logfile == null) logfile = "log.txt";
		
		if (instance == null) {
			instance = new Logger(logfile);
		}
		return instance;
	}
	
	
	
	public void log(String message) {
		try {
			writer.write(message + "\n");
			writer.flush();
		} catch (Exception e) {}
		
	}
	
	public void close() {
		try { 
			writer.close(); 
		} catch (Exception e) {
				
		}
	}

}

