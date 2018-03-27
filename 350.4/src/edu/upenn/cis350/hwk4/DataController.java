package edu.upenn.cis350.hwk4;


public class DataController {
	
	EloFileReader eloReader;
	GamesReader gamesReader;


	public DataController(String elofile, String gamesfile) {
		EloFileFactory ef = new EloFileFactory(elofile);
		eloReader = ef.getReader();
		if (eloReader == null) {
			Logger.getInstance().log("There was a Problem with the Elo File. System exiting..");
			System.err.println("There was a Problem with the Elo File. System exiting..");
		}
		gamesReader = new GamesCSVReader(gamesfile);
	}
	
	public boolean hasTeam(String team) {
		return eloReader.hasTeam(team);
	}
	
	public void setElo(String team, double elo) {
		eloReader.setElo(team, elo);
	}
	
	public String simulateGame(String team1, String team2) {
		if (!(hasTeam(team1) && hasTeam(team2))) {
			return null; //should never happen based on condition checking
		}
		
		double elo1 = eloReader.getElo(team1);
		double elo2 = eloReader.getElo(team2);
		double diff = elo1 - elo2;
		double percentage = 1 / (Math.pow(10, -diff/400) + 1);
		
		String result = String.format(" %s has a %f chance of beating %s", team1, percentage*100, team2);
		
		if (Math.random() < percentage) {
			result += String.format("\n %s wins!", team1);
		} else {
			result += String.format("\n %s wins!", team2);
		}
		
		return result;
	}
	
	

	public String simulateTournament(int choice) {
		GameSimulator simulator;
		if (choice == 1) {
			simulator = new CoinFlipSimulator();
		} else if (choice == 2) {
			simulator = new EloSimulator();
		} else if (choice == 3) {
			simulator = new FavoriteWinsSimulator();
		} else simulator = new PennWinsSimulator();
		
		Bracket tournament = new Bracket(gamesReader.getGames(), simulator, eloReader);
		tournament.reset();
		return tournament.simulateTournament();
	}
}
