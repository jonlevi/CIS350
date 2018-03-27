package edu.upenn.cis350.hwk4;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class EloCSVReader extends EloFileReader {
		
	public EloCSVReader(String filename) {
		try {
			readFile(filename);
		} catch (IOException e) {
			log("Error opening EloFile. Exiting Program");
			System.err.println("Error opening EloFile. Exiting Program...");
			System.exit(-1);
		}
	}
	
	private void readFile(String filename) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(filename));
		String header = br.readLine();
		
		String line = null;
		    while ((line = br.readLine()) != null) {
		        String [] teamInfo = line.split(",");
		        String name = teamInfo[0];
		        String region = teamInfo[1];
		        int seed = Integer.parseInt(teamInfo[2]);
		        double elo = Double.parseDouble(teamInfo[3]);
		        nameToTeam.put(name.toLowerCase(), new Team(name.toLowerCase(), region, seed, elo));
		    }
		    
		br.close();
	}
}
