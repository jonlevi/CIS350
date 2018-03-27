import java.io.File;
import java.io.FileWriter;

public class Logger {
	private static Logger instance;
	private FileWriter writer;
	
	private Logger(String filename) {
		writer = null;
		try {
			writer = new FileWriter(new File(filename), true);
			
		}
		catch (Exception e) { e.printStackTrace(); }

	}
	
	public static Logger getInstance() {
		if (instance == null) {
			instance = new Logger("log.txt");
		}
		return instance;
	}
	
	
	public static void  log(String message) {
		try {
			FileWriter writer = getInstance().writer;
			writer.write(message);
			writer.flush();
		} catch (Exception e) {}
		
	}
	
	public static void close() {
		try { getInstance().writer.close(); } catch (Exception e) { }
	}

}
