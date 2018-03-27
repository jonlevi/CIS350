package edu.upenn.cis350.hwk1;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class Main {
	
	
	private static FileWriter log;
	private static String instructions = "1.Rank the states "
			+ "by the number of tweets "
			+ "\n" + "2.Show the most popular hashtags in a "
			+ "given state " + "\n" + "3.Show the number of tweets "
			+ "per hour containing a given term " + "\n"
			+ "4.Quit the program";

	public static void main(String[] args) {
		//log input arguments
		String input = "";
		input+= "program started with args ";
		for (String arg: args) {
			input += arg + " ";
		}
		//check for valid command line args
		
		if (args.length != 3) {
			System.err.println("Not enough input arguments - Exiting...");
			System.exit(1);
		}
		//read in command line
		
		String tweetFile = args[0];
		String mapFile = args[1];
		String logFile = args[2];
		//initliaze log file
		try {
			log = new FileWriter(new File(logFile), true);
		} catch (IOException e) {
			System.err.println("Bad Log File - Exiting...");
			System.exit(1);
		}
		
		//record command line args to log file
		addToLog(input);
		
		//initialize tweet and state data structure
		try {
			Data data = new Data(tweetFile, mapFile);
			//prompt user for decision and run program...
			menu(data);
		} catch (IOException e) {
			addToLog("program exited - error: problem opening one of the files");
			try {
				log.close();
			} catch (IOException e2) {
				System.err.println("Error writing to the log file");
			};
			System.err.println("Bad File - Exiting...");
			System.exit(1);
		}
	}
	
	/**
	 * Adds message to log file specified
	 * at runtime at current system time
	 * @param message - message to add to class level log file
	 */
	private static void addToLog(String message) {
		try {
			DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss:SS");
			Date dateobj = new Date();
			log.write(df.format(dateobj) + ": " + message + "\n");
		} catch (IOException e) {
			System.err.println("Error With the Log File");
			System.err.println("Exiting...");
			System.exit(1);
		};
	}
	
	/**
	 * Utility method to manage user choices after input
	 * Also logs choice to log file specified at runtime
	 * @param choice - number chosen by user
	 * @param data - current object for twitter/state data being used
	 * based on files given in command line
	 */
	private static void chooseMethod(int choice, Data data) {
		addToLog("user chose option #" + choice);
		if (choice == 1) {
			methodOne(data);
		} else if (choice == 2) {
			methodTwo(data);
		} else if (choice == 3) {
			methodThree(data);
		} else {
			System.out.println("Exiting...");
			addToLog("program ended");
			try {
				log.close();
			} catch (Exception e) {
				System.err.println("Error closing the Log File");
				System.err.println("Exiting...");
				System.exit(1);
			}
			System.exit(0);
		}
	}
	
	/**
	 * Utility method to run the first option
	 * Displays states in descending order of number of tweets
	 * @param data - current object for twitter/state data being used
	 * based on files given in command line
	 */
	private static void methodOne(Data data) {
		List<String> statesByTweets = data.statesByTweets();
		for (String s: statesByTweets) {
			System.out.println(s);
		}
	}
	
	/**
	 * Utility method to run the second option
	 * Prompts user for state, and once given valid state
	 * will list top 10 hashtags in that state
	 * Adds decision to logfile
	 * @param data - current object for twitter/state data being used
	 * based on files given in command line
	 */
	private static void methodTwo(Data data) {
		boolean notFound = true;
		Map<String, Integer> tags = null;
		String state = null;
		while (notFound) {
			state = getStateInput();
			addToLog("user entered " + state);
			tags = data.hashTagsByStates(state);
			if (tags != null && state != null) {
				notFound = false;
			}
		}
		//sort the entries by values and put the results in an arraylist
		List<Map.Entry<String, Integer>> sorted = new ArrayList<Map.Entry<String, Integer>>(tags.entrySet());
		Collections.sort(sorted, new Comparator<Map.Entry<String,Integer>>() {
            public int compare(Map.Entry<String, Integer> first,
                    Map.Entry<String, Integer> second) {
                return second.getValue().compareTo(first.getValue());
            }
        });
		int count = 0;
		while (count < 10 && count < sorted.size()) {
			System.out.println(count + 1 + ": " + sorted.get(count++).getKey());
		}
		
		addToLog("user successfully searched for " + state);
	}
	
	/**
	 * Utility method to run the third option
	 * Prompts user for query phrase used to search tweets.
	 * Displays number of tweets per hour containing search term, 
	 * or displays that no results are found.
	 * Also adds decision to logFile
	 * @param data - current object for twitter/state data being used
	 * based on files given in command line
	 */
	private static void methodThree(Data data) {
		boolean notFound = true;
		Map<String, Integer> timesToFreq = null;
		String phrase = null;
		while (notFound) {
			phrase = getPhraseInput();
			timesToFreq = data.getMentionsPerTimeStamp(phrase);
			if (timesToFreq != null && phrase != null) {
				notFound = false;
			}
		}
		if (timesToFreq.isEmpty()) System.out.println("No results found");
		else {
			for (Map.Entry<String, Integer> e : timesToFreq.entrySet()) {
				System.out.println(e.getKey() + ":00 " + e.getValue() + " times");
			}
		}
		addToLog("user searched for " + phrase);
	}
	
	/**
	 * Utility method to get Phrase Input
	 * @see getQuery
	 */
	private static String getPhraseInput() {
		return getQuery("Query Phrase");
	}
	
	/**
	 * Utility method to get State Input
	 * @see getQuery
	 */
	private static String getStateInput() {
		return getQuery("Valid State");
	}
	
	/**
	 * Utility method to get user Input
	 * @return Input given by user from scanner
	 */
	private static String getQuery(String query) {
		Scanner reader = new Scanner(System.in);
		String state = "";
		while (true) {
			System.out.println("Please Type in a " + query + ":");
			try {
				state = reader.nextLine();
				return state;
			} catch (InputMismatchException e) {
				System.out.println("Invalid Input");
			}
		}
	}
	
	/**
	 * Utility method to display menu and get user choice.
	 * All methods run from this top level function
	 * @param data - current object for twitter/state data being used
	 * based on files given in command line
	 * @see chooseMethod
	 * 
	 */
	private static void menu(Data data) {
		Scanner reader = new Scanner(System.in);
		int x = -1;
		while (true) {
			System.out.println("Choose an option from below by "
				+ "typing the corresponding number into the prompt, then pressing enter:");
			System.out.println(instructions);
			try {
				x = reader.nextInt();
				if (x < 1 || x > 4) {
					System.out.println("Invalid Input");
					addToLog("user chose option" + x + ", which is not a valid option");
				}
				else {
					chooseMethod(x, data);
				}
			} catch (InputMismatchException e) {
				System.out.println("Invalid Input");
				addToLog("user entered invalid menu key");
				reader.nextLine();
			}
		}
	}
}