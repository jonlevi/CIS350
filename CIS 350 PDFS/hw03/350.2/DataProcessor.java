import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeMap;


public class DataProcessor {
	
	private DataStore store;
	
	public DataProcessor(DataStore dataStore) {
		store = dataStore;
	}
	

	public SuperBowl getSuperBowl(int year) {
		log("getting data for year: " + year);
		// look through all the instances
		ArrayList<SuperBowl> list = store.allInstances();

		for (SuperBowl sb : list) {
			if (sb.getYear() == year) {
				// found it!
				return sb;
			}
		}
		
		// if we made it here, we didn't find it
		return null;
		
	}
	
	public SuperBowl getSuperBowl(String number) {
		log("getting data for number: " + number);
		ArrayList<SuperBowl> list = store.allInstances();

		for (SuperBowl sb : list) {
			if (sb.getNumber().equalsIgnoreCase(number)) {
				// found it!
				return sb;
			}
		}
		
		// if we get here, we didn't find one for that year
		return null;
		
	}


	
	public String getDataForRange(int start, int end) {
		log("getting data for range: " + start + " to " + end);

		// make sure we have valid data
		if (end < start) {
			return "Invalid year range";
		}
				
		// look in the cache first
		if (store.getFromCache(start+"-"+end) != null) {
			return store.cache.get(start+"-"+end);
		}
		
		StringBuffer result = new StringBuffer();
		
		// this is a counter of how many we've added to the buffer
		int count = 0;
		
		for (int year = start; year <= end; year++) {
			SuperBowl sb = getSuperBowl(year);
			if (sb != null) {
				result.append(sb.toString());
				result.append("\n");
				count++;
			}
		}

		// if we didn't see any results, return that
		if (count == 0) {
			return "No Super Bowls held between " + start + " and " + end;
		}
		else {
			store.addToCache(start+"-"+end, result.toString());
			return result.toString();
		}
		
	}


	public String getDataForTeamWins(String team) {
		log("getting wins for team: " + team);

		
		// look in the cache first
		if (store.getFromCache(team+"-wins") != null) {
			return store.cache.get(team+"-wins");
		}
		
		// to hold the result
		StringBuffer result = new StringBuffer();
	
		// keep track of the number of wins for the team
		int wins = 0;
			
		// look through all the instances
		ArrayList<SuperBowl> list = store.allInstances();
		for (SuperBowl sb : list) {
			// convert to uppercase and use "contains" for partial matching
			if (sb.getWinner().toUpperCase().contains(team.toUpperCase())) {
				// we found an instance when the team won
				result.append(sb.toString());
				result.append("\n");
				wins++;
			}
		}
		// if none found, print a message
		if (wins == 0) {
			result.append("The " + team + " have not won any Super Bowls");
			result.append("\n");
		}
		else {
			result.append("The " + team + " have won " + wins + " Super Bowls");
			result.append("\n");
		}
		
		// put it in the cache
		store.cache.put(team+"-wins", result.toString());

		return result.toString();
	}
	
	public String getDataForTeamLosses(String team) {
		log("getting losses for team: " + team);

		// look in the cache first
		if (store.getFromCache(team+"-losses") != null) {
			return store.cache.get(team+"-losses");
		}
		
		// to hold the result
		StringBuffer result = new StringBuffer();

		// keep track of the number of losses for the team
		int losses = 0;
		
		// look through all the instances
		ArrayList<SuperBowl> list = store.allInstances();
		for (SuperBowl sb : list) {
			// convert to uppercase and use "contains" for partial matching
			if (sb.getLoser().toUpperCase().contains(team.toUpperCase())) {
				result.append("In " + sb.getYear() + " the " + sb.getLoser() + " lost to the " + sb.getWinner() + " by a score of " + sb.getWinnerScore() + "-" + sb.getLoserScore() + " in Super Bowl " + sb.getNumber() + " in " + sb.getLocation().getCity() + sb.getLocation().getState());
				result.append("\n");
				losses++;
			}	
		}
		// if none found, print a message
		if (losses == 0) {
			result.append("The " + team + " have not lost any Super Bowls");
			result.append("\n");
			}
		else {
			result.append("The " + team + " have lost " + losses + " Super Bowls");
			result.append("\n");
		}

		// put it in the cache
		store.addToCache(team+"-losses", result.toString());
		
		return result.toString();

	}
			
	public String getDataForTeamAll(String team) {
		log("getting wins and losses for team: " + team);

		// look in the cache first
		if (store.getFromCache(team+"-all") != null) {
			return store.cache.get(team+"-all");
		}

		// to hold the result
		StringBuffer result = new StringBuffer();

		// keep track of the number of wins and losses for the team
		int wins = 0, losses = 0;
			
		// look through all the instances
		ArrayList<SuperBowl> list = store.allInstances();
		for (SuperBowl sb : list) {
			// convert to uppercase and use "contains" for partial matching
			if (sb.getWinner().toUpperCase().contains(team.toUpperCase())) {
				// we found an instance when the team won
				result.append(sb.toString());
				result.append("\n");
				wins++;
			}
			else if (sb.getLoser().toUpperCase().contains(team.toUpperCase())) {
				result.append("In " + sb.getYear() + " the " + sb.getLoser() + " lost to the " + sb.getWinner() + " by a score of " + sb.getWinnerScore() + "-" + sb.getLoserScore() + " in Super Bowl " + sb.getNumber() + " in " + sb.getLocation().getCity() + sb.getLocation().getState());
				result.append("\n");
				losses++;
			}
		}
		// if none found, print a message
		if (wins + losses == 0) {
			result.append("The " + team + " have not played in any Super Bowls");
			result.append("\n");
		}
		else {
			result.append("The " + team + " have won " + wins + " Super Bowls and lost " + losses);
			result.append("\n");
		}
		
		// put it in the cache
		store.cache.put(team+"-all", result.toString());
		
		return result.toString();
		
	}
	
	
	protected void displayTeams(TreeMap<String, ArrayList<Integer>> teams, UserInterface ui) {
		log("Trying to display all teams");

		Set<String> keys = teams.keySet();
		for (String key : keys) {
			ui.displayOutput(key + ": ");
			ArrayList<Integer> years = teams.get(key);
			for (int i = 0; i < years.size()-1; i++) ui.displayOutput(years.get(i) + ", ");
			ui.displayOutput(years.get(years.size()-1) + "\n");
		}

		
	}


	protected void log(String event) {
		Logger.log(System.currentTimeMillis() + " (Data Processor): " + event +"\n");
	}

}
