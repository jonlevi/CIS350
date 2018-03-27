package edu.upenn.cis350.hwk4;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GamesCSVReader extends GamesReader {

	private Map<Integer, Game> games;
	
	public GamesCSVReader(String filename) {
		games = new HashMap<Integer, Game>();
		try {
			readFile(filename);
		} catch (IOException e) {
			System.err.println("Problem with Games File. System Exiting...");
			Logger.getInstance().log("Problem with Games File. System Exiting...");
		}
	}
	
	private void readFile(String filename) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(filename));
		String header = br.readLine();
		String line = null;
		    while ((line = br.readLine()) != null) {
		        String [] gameInfo = line.split(",");
		        int gameNum = Integer.parseInt(gameInfo[0]);
		        String team1 = gameInfo[1];
		        String team2 = gameInfo[2];
		        int nextGame = Integer.parseInt(gameInfo[3]);
		        
		        Game g = new Game(team1.toLowerCase(), team2.toLowerCase(), gameNum, nextGame);
		        games.put(gameNum, g);
		    }
		    
		br.close();
	}
	
	@Override
	public Map<Integer, Game> getGames() {
		return new HashMap<Integer, Game>(games);
	}

}
