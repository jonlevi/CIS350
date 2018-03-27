package edu.upenn.cis350.hwk4;

public class ElofileFactory {
	
	private ElofileReader reader;

	public ElofileFactory(String filename) {
		
		if (filename.endsWith("csv")) {
			reader = new EloCSVReader(filename);
		} else if (filename.endsWith(".JSON")) {
			reader = new EloJSONReader(filename);
		}
		
	}
	
	public ElofileReader getReader() {
		return reader;
	}

}
