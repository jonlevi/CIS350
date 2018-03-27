package edu.upenn.cis350.hwk4;

import java.util.InputMismatchException;
import java.util.Scanner;

public class CommandLineInterface extends UserInterface {
	
	private String mainMenu = "Welcome to the NCAA Tournament Program \n Please Choose an Option by Entering the Number of Your Choice \n" +
			"1) Simulate a single hypothetical game \n" + 
			"2) Simulate the entire tournament \n" + 
			"3) Change a single team's elo rating \n" +
			"4) Exit";
	private String simulationMessage = "Please Choose a Simulation Method by Entering the Number of Your Choice \n" +
			"1) Coin flip -- every game is 50/50 odds \n" + 
			"2) Elo -- every game is randomly decided but considering Elo \n" + 
			"3) Favorite wins -- The team with the higher Elo rating always wins. \n" +
			"4) Penn always wins -- Penn Always Wins (Every other game is determined by Elo as in option 2)";
	
	
	private Scanner scanner;
	private DataController dc;
	
	public CommandLineInterface(String elofile, String gamesfile) {
		scanner = new Scanner(System.in);
		dc = new DataController(elofile, gamesfile);
	}

	@Override
	public void run() {
		int choice;
		boolean isExit = false;
		
		do {
			display(mainMenu);

			choice = getMenuChoice();
			
			if (choice == 1) {
				simulateGame();
			} else if (choice == 2) {
				simulateTournament(getSimulationChoice());
			} else if (choice == 3) {
				changeElo();
			} else if (choice == 4) {
				display("Thanks for stopping by!");
				log("User chose to exit. System exiting");
				isExit = true;
			} 
		} while (!isExit);
	}
	
	private void simulateTournament(int choice) {
		String result = dc.simulateTournament(choice);
		log("Simulated Tournament: \n" + result);
		display(result);
	}

	private void changeElo() {
		display("Please enter the name of the Team");
		String team = getTeamChoice();
		
		display("Please enter the new Elo Score");
		double elo = getDoubleInput();
		dc.setElo(team, elo);
		log("User changed Elo for " + team + " to " + elo);
	}
	
	private void simulateGame() {
		display("Please enter the name of Team 1");
		String team1 = getTeamChoice();
		display("Please enter the name of Team 2");
		
		boolean isDifferent = false;
		String team2 = "";
		while (!isDifferent) {
			team2 = getTeamChoice();
			if (team2.toLowerCase().equals(team1.toLowerCase())) {
				display("Please enter a different team than you entered above");
			} else {
				isDifferent = true;
			}
		}
		
		display(dc.simulateGame(team1, team2));
		log(team1 + " beat " + team2 + " by simulation");
		display("Now returning to Main Menu...");
	}
	
	private String getTeamChoice() {
		boolean exists = false;
		String team;
		do {
			team = getStringInput();
			if (dc.hasTeam(team)) {
				exists = true;
			} else {
				if (!team.isEmpty()) {
					display("EloFile does not have that team. Please enter a team that exists in the input file");
					log("User entered a team that wasn't in the elofile");
				}
			}
			
		} while (!exists);		
		return team;
	}
	
	
	private int getMenuChoice() {
		boolean validChoice = false;
		int choice;
		do {
		  choice = getIntInput();
		  if (choice >= 1 || choice <= 4) {
		    validChoice = true;
		  } else {
		    display("Invalid Choice: Please enter a Number 1-4");
		  }
		  
		} while (!validChoice);
		
		return choice;
	}
	
	private int getSimulationChoice() {
		display(simulationMessage);
		int choice = getMenuChoice();
		log("User chose simulation option " + choice);
		return choice;
	}

	@Override
	protected int getIntInput() {
		int x;
		while (true) {
			try {
				x = scanner.nextInt();
				log("User entered " + x);
				return x;
			} catch (InputMismatchException e) {
				System.out.println("Invalid input. Please enter a number");
				log("user entered an invalid item" + scanner.nextLine());
			}
		}
	}
	
	@Override
	protected double getDoubleInput() {
		double x;
		while (true) {
			try {
				x = scanner.nextDouble();
				log("User entered " + x);
				return x;
			} catch (InputMismatchException e) {
				System.out.println("Invalid input. Please enter a number");
				log("user entered an invalid item" + scanner.nextLine());
			}
		}
	}

	@Override
	public String getStringInput() {
		String input = "";
		while (true) {
			
			try {
				input = scanner.nextLine();
				log("User entered " + input);
				return input;
			} catch (InputMismatchException e) {
				System.out.println("Invalid input");
				log("User entered something that wasn't recognized as a string");
			}
		}
	}
	
	@Override
	public void display(String s) {
		System.out.println(s);
	}
}
