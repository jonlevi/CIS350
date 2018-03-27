import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;


public class CommandLineUserInterface implements UserInterface {

	protected PrintStream out; 
	public Scanner in;


	private DataStore store;
	private DataProcessor processor;

	// map of teams to the years they won
	private TreeMap<String, ArrayList<Integer>> winningTeams;

	
	public CommandLineUserInterface() {
		try {	
			store = new DataStore(new File("superbowls.csv"));
		}
		catch (IOException e) {
			log("Could not open datafile!");
		}
		processor = new DataProcessor(store);
		out = System.out;
		in = new Scanner(System.in);
	}

	
	
			
	public void start() {
		
		displayOutput("Welcome to the Super Bowl database!\n");
		log("Starting application");

		String choice = null; // the thing that the user chooses to do

		do {
			showPrompt();
			
			choice = getInputString();
			log("User input: " + choice);
			
			if (choice.equals("1")) { 
				// if they want to search by year
				searchByYear();	
			}
			else if (choice.equals("2")) { 
				// if they want to search by number
				searchByNumber();	
			}
			else if (choice.equals("3")) {
				// search by team
				searchByTeam(askUserForTeamName(), askUserForWinLossOrAll());
			}
			else if (choice.equals("4")) {
				// for a range of years
				searchByRange();
			}
			else if (choice.equals("5")) {
				// show all the teams and the years they won a World Series
				assembleWinnersByTeam();
				processor.displayTeams(winningTeams, this);
			}
			else if (!quit(choice)) {
				displayOutput("That is not a valid selection.\n");
			}
			
		}
		while (!quit(choice));
		displayOutput("Good bye.\n");
	}

	
	protected static boolean quit(String choice) {
		return choice.equals("Q") || choice.equals("q");
	}
	
	
	
	protected void showPrompt() {
		displayOutput("\n");
		displayOutput("Please make your selection:\n");
		displayOutput("1: Search for Super Bowl info by year\n");
		displayOutput("2: Search for Super Bowl info by number\n");
		displayOutput("3: Search for Super Bowl info by team\n");
		displayOutput("4: Show all Super Bowls for a range of years\n");
		displayOutput("5: Show all teams' Super Bowl wins\n");
		displayOutput("Q: Quit\n");
		displayOutput("> ");

	}

	protected void searchByYear() {
		displayOutput("Please enter the year: ");
		try {
			int year = getInputInt();
			log("Trying to search for year: " + year);
			SuperBowl sb = processor.getSuperBowl(year);
			if (sb == null) displayOutput("No Super Bowl held in " + year + "\n");
			else displayOutput(sb.toString());
		}
		catch (Exception e) { 
			displayOutput("That is not a valid year.");
			log("User entered invalid year");
		}
	}

	protected void searchByNumber() {
		displayOutput("Please enter the number: ");
		try {
			String number = getInputString();
			log("Trying to search for number: " + number);
			SuperBowl sb = processor.getSuperBowl(number);
			if (sb == null) displayOutput("No Super Bowl with number " + number + "\n");
			else displayOutput(sb.toString());
		}
		catch (Exception e) { 
			displayOutput("That is not a valid year.");
			log("User entered invalid year");
		}
	}

	
	protected String askUserForWinLossOrAll() {
		displayOutput("Do you want to see Super Bowls the team has (W)on, (L)ost, or (A)ll? ");
		return getInputString();

	}
	
	protected String askUserForTeamName() {
		displayOutput("Please enter the team name: ");
		return getInputString();
	}

	protected void searchByTeam(String team, String which) {
		log("Trying to search for: team=" + team + ", which=" + which);

		if (which.equalsIgnoreCase("W"))
			displayOutput(processor.getDataForTeamWins(team) + "\n");
		else if (which.equalsIgnoreCase("L"))
			displayOutput(processor.getDataForTeamLosses(team) + "\n");
		else if (which.equalsIgnoreCase("A"))
			displayOutput(processor.getDataForTeamAll(team) + "\n");
		else
			displayOutput(which + " is not a valid selection.\n");
	}
	
	protected void searchByRange() {
		int startYear, endYear;
		displayOutput("Please enter the starting year: ");
		try {
			startYear = getInputInt();
			log("Trying to search for range starting with: " + startYear);
		}
		catch (Exception e) { 
			displayOutput("That is not a valid year.\n");
			log("User entered invalid year");
			return;
		}
		displayOutput("Please enter the ending year: ");
		try {
			endYear = getInputInt();
			log("Trying to search for range starting with: " + endYear);
		}
		catch (Exception e) { 
			displayOutput("That is not a valid year.\n");
			log("User entered invalid year");
			return;
		}
		String yearData = processor.getDataForRange(startYear, endYear);
		displayOutput(yearData);
	}
	
	
	protected void assembleWinnersByTeam() {
		log("Trying to assemble winners by team");

		ArrayList<SuperBowl> list = store.allInstances();

		winningTeams = new TreeMap<String, ArrayList<Integer>>();
		
		for (SuperBowl sb : list) {

			// see if the winner is already in the list of teams
			if (winningTeams.containsKey(sb.getWinner())) {
				winningTeams.get(sb.getWinner()).add(sb.getYear());
			}
			else {
				// create an entry in the wins list
				ArrayList<Integer> newEntry = new ArrayList<Integer>();
				newEntry.add(sb.getYear());
				winningTeams.put(sb.getWinner(), newEntry);
			}
		}
				
	}
	
	public void displayOutput(String data) {
		out.print(data);
	}
	
	public String getInputString() {
		return in.nextLine();
	}

	public int getInputInt() {
		return in.nextInt();
	}
	
	protected void log(String event) {		
		Logger.log(System.currentTimeMillis() + " (CommandLineUserInterface): " + event +"\n");
	}
	
	// NOTE: you do not need to put this in the class model
	public static void main(String[] args) {
		UserInterface ui = new CommandLineUserInterface();
		ui.start();
	}
	
}
