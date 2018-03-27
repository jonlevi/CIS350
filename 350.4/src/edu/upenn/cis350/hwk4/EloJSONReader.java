package edu.upenn.cis350.hwk4;


import java.io.FileReader;
import java.io.IOException;
import org.json.simple.parser.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.JSONException;

public class EloJSONReader extends EloFileReader {
	
	
	public EloJSONReader(String filename) {
		
		try {
			readFile(filename);
		} catch (Exception e) {
			e.printStackTrace();
			log("Error Opening JSON Elo File, System Exiting...");
			System.err.println("Error Opening JSON Elo File, System Exiting...");
			System.exit(-1);
		}

	}
		
	private void readFile(String filename) throws IOException, JSONException, ParseException {

	    JSONParser parser = new JSONParser();
	    JSONArray a = (JSONArray) parser.parse(new FileReader(filename));
	    for (Object o : a) {
	    	JSONObject person = (JSONObject) o;
	    	String team = (String) person.get("Team");
	    	String region = (String) person.get("Region");
	    	int seed = ((Long) person.get("Seed")).intValue();
	    	double elo = (double) ((Long) person.get("Elo")).intValue();
	    	//nameToElo.put(team.toLowerCase(), elo);
	    	nameToTeam.put(team.toLowerCase(), new Team(team.toLowerCase(), region, seed, elo));
	    }
	    
	}
}