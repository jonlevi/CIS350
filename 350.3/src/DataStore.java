import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class DataStore {
	
	private ArrayList<SuperBowl> list;
	public HashMap<String, String> cache; // holds the results of previous searches
	public Scanner in;
	private int cacheHits = 0, cacheMisses = 0; // for cache statistics
	public File dataFile;
	
	public DataStore(File file) throws IOException {
		list = new ArrayList<SuperBowl>();
		cache = new HashMap<String, String>();
		dataFile = file;
		in = new Scanner(dataFile);
		readDataFile();
	}
		
	public ArrayList<SuperBowl> allInstances() { 
		return list; 
	}
	
	private void readDataFile() {
		log("populating ArrayList");
		
		try {
			// since it's a comma-separated file
			in.useDelimiter(",");
			
			// read each line of the file one at a time
			while (in.hasNext()) {
				
				// create a WorldSeriesInstance
				SuperBowl wsi = createInstance();
				
				// add it to the ArrayList
				list.add(wsi);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			
		}
		
	}
	
	public SuperBowl createInstance() {
		
		// here's the first line
		//Feb 1 2015,XLIX (49),New England Patriots,28,Seattle Seahawks,24,Tom Brady,University of Phoenix Stadium,Glendale,Arizona

		String date = in.next(); // Feb 1 2015
		int year = Integer.parseInt(date.split(" ")[2]);
		
		String title = in.next(); // XLIX (49)
		String number = title.split(" ")[0];
		
		String winner = in.next();
		int winnerScore = in.nextInt();
		String loser = in.next();
		int loserScore = in.nextInt();
		
		String mvp = in.next(); // not used
		String stadium = in.next(); // not used
		String city = in.next();
		String state = in.nextLine();
		
		return new SuperBowl(year, winner, loser, winnerScore, loserScore, number, city, state);

	}

	
	protected void log(String event) {
		Logger.log(System.currentTimeMillis() + " (DataStore): " + event +"\n");
	}
	
	public String getFromCache(String key) {
		log("Looking in cache for key: " + key);
		if (cache.containsKey(key)) {
			cacheHits++;
			return cache.get(key); 
		}
		else {
			cacheMisses++;
			return null;
		}
	}
	
	public void addToCache(String key, String value) {
		log("Adding to cache for key: " + key + " and value:" + value);
		cache.put(key, value);
	}
	
}
