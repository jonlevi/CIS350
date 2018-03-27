package edu.upenn.cis350.hwk4;

public class EloFileFactory {
	
	private EloFileReader reader;

	public EloFileFactory(String filename) {
		
		if (filename.endsWith("csv")) {
			reader = new EloCSVReader(filename);
		} else if (filename.endsWith(".JSON")) {
			reader = new EloJSONReader(filename);
		}
		
	}
	
	public EloFileReader getReader() {
		return reader;
	}

}
