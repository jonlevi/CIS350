package edu.upenn.cis350.hwk1;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Data {
	
	
	private HashSet<State> states = new HashSet<State>();
	private HashMap<State, HashMap<String, Integer>> 
					stateToHashToN = new HashMap<State, HashMap<String, Integer>>();
	private Map<String, List<String>> timeStampToTweets = new TreeMap<String, List<String>>();	

	/**
	 * State Class
	 */
	private class State implements Comparable<State> {
		
		
		String name;
		Location center;
		int numTweets;
		
		/**
		 * 
		 * @param name - name of state
		 * @param l - Location Object for coordinates of center of state
		 */
		public State(String name, Location l) {
			center = l;
			this.name = name.toUpperCase();
			numTweets = 0;
		}
		
		/**
		 * {@inheritDoc}
		 */
		public int hashCode() {
			return name.hashCode();
		}
		
		/**
		 * {@inheritDoc}
		 */
		public boolean equals(Object that) {
			if (that.getClass() != this.getClass()) return false;
			State s = (State) that;
			return this.name.equalsIgnoreCase(s.name);
		}
		
		/**
		 * {@inheritDoc}
		 */
		public int compareTo(State that) {
			return Integer.compare(this.numTweets, that.numTweets);
		}
		
	}
	
	
	private class Location {
		
		double x;
		double y;
		
		/**
		 * 
		 * @param lat - latitude
		 * @param lon - longitude
		 */
		public Location(double lat, double lon) {
			x = lat;
			y = lon;
		}
	}
	
	/**
	 * 
	 * @param tweetFile - tsv txt file containing tweet info
	 * @param stateFile - csv file containing state info
	 * @throws IOException for bad file inputs
	 */
	public Data(String tweetFile, String stateFile) throws IOException {
		readStatesCSV(stateFile);
		readTweetsTSV(tweetFile);
	}
	
	/**
	 * 
	 * @return list of string, num for states by tweets
	 */
	public List<String> statesByTweets() {
		List<State> sortedList = new ArrayList<State>(states);
		Collections.sort(sortedList, Collections.reverseOrder());
		List<String> toPrint = new ArrayList<String>();
		for (State s: sortedList) {
			toPrint.add(s.name + ", " + s.numTweets);
		}
		return toPrint;
	}
	
	/**
	 * 
	 * @param state - query state
	 * @return map from hashtag to frequency used
	 */
	public Map<String, Integer> hashTagsByStates(String state) {
		state = state.toUpperCase();
		//dummy location, just used to create object for finding in map
		State matcher = new State(state, new Location(0, 0));
		if (!states.contains(matcher)) return null;
		Map<String, Integer> hashtagsToFreq = stateToHashToN.get(matcher);
		return new HashMap<String, Integer>(hashtagsToFreq);
	}
	
	/**
	 * 
	 * @param pattern - search phrase used to search tweets
	 * @return map from timestamp to num of times seach phrase found in timestamp
	 */
	public Map<String, Integer>getMentionsPerTimeStamp(String pattern) {
		if (pattern == null || pattern.equals("")) return null;
		Map<String, Integer> timeToFreq = new TreeMap<String, Integer>();
		for (Map.Entry<String, List<String>> mp : timeStampToTweets.entrySet()) {
			int count = 0;
			for (String tweet: mp.getValue()) {
				if (tweet.contains(pattern)) {
					count++;
				}
			}
			if (count > 0) {
				timeToFreq.put(mp.getKey(), count);
			}
		}
		
		return timeToFreq;
	}
	
	/**
	 * 
	 * @param timeStamp - timeStamp to add
	 * @param tweet - tweet to add
	 */
	private void addTimeStamp(String timeStamp, String tweet) {
		timeStampToTweets.putIfAbsent(timeStamp, new ArrayList<String>());
		timeStampToTweets.get(timeStamp).add(tweet);
	}
	
	/**
	 * 
	 * @param filename - states CSV data
	 * @throws IOException - error reading file
	 */
	private void readStatesCSV(String filename) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(filename));
		 String line = null;
		    while ((line = br.readLine()) != null) {
		        String [] info = line.split(",");
		        String name = info[0];
		        double lat = Double.parseDouble(info[1]);
		        double lon = Double.parseDouble(info[2]);
		        Location l = new Location(lat, lon);
		        State s = new State(name, l);
		        states.add(s);
		        stateToHashToN.put(s, new HashMap<String, Integer>());
		    }
	}
	
	/**
	 * 
	 * @param filename - tweet data TSV
	 * @throws IOException - error reading file
	 */
	private void readTweetsTSV(String filename) throws IOException {
		
		 BufferedReader br = new BufferedReader(new FileReader(filename));
		 String line = null;
		    while ((line = br.readLine()) != null) {
		        String [] tweet = line.split("\t");
		        String [] coords = tweet[0].split(",");
		        double lat = Double.parseDouble(coords[0].replaceAll("[^-\\d.]", ""));
		        double lon = Double.parseDouble(coords[1].replaceAll("[^-\\d.]", ""));
		        Location loc = new Location(lat, lon);
		        State closest = stateClosestToPoint(loc);
		        //increment tweet count for state closest to location
		        closest.numTweets = closest.numTweets + 1;
		        readHashTags(tweet[3], closest);
		        addTimeStamp(tweet[2].substring(0, 13), tweet[3]);
		    }
		
	}
	
	/**
	 * Method to sort out tweets by state and hashtag
	 * @param tweet - string to search
	 * @param closest - closest State geographically to tweet
	 */
	private void readHashTags(String tweet, State closest) {
		List<String> tags = getHashTags(tweet);
		for (String hash: tags) {
			hash = hash.toUpperCase();
			//if state has not seen that hashtag yet,
			//start counter at 0.
			stateToHashToN.get(closest).putIfAbsent(hash, 0);
			//for the state closest, increment number of
			//times hashtag is used by 1
			stateToHashToN.get(closest).put(hash, 
					stateToHashToN.get(closest).get(hash) + 1);
		}
	}
	
	/**
	 * Utility Method for parsing a string for hashtags using regex pattern match
	 * @param tweet - string to search in
	 * @return
	 */
	private List<String> getHashTags(String tweet) {
		List<String> tags = new ArrayList<String>();
		Matcher mat = Pattern.compile("#(\\w+)").matcher(tweet);
		while (mat.find()) {
		  tags.add(mat.group(1));
		}
		return tags;
	}
	
	
	/**
	 * @param loc - Query Location
	 * @return closest State in class level set of states to input location
	 * Note: Assumes that the set containing all of the states has already
	 * been initialized!!!
	 **/
	private State stateClosestToPoint(Location loc) {
		State closest = null;
		double closestDist = Double.MAX_VALUE;
		for (State s: states) {
			double dist = distance(loc, s.center);
			if (dist < closestDist) {
				closest = s;
				closestDist = dist;
			}
		}
		return closest;
	}
	
	/**
	 * 
	 * @param l1 - location of first point
	 * @param l2 - location of second point
	 * @return value representing estimate of distance between two points on Earth
	 * (Note that value returned is actually square of dist estimate)
	 */
	private static double distance(Location l1, Location l2) {
		return Math.pow(l1.x-l2.x,2) + Math.pow(l1.y-l2.y,2);
	}

}
