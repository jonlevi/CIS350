package edu.upenn.cis350.hwk4;

public class Game extends Subject {
	private String team1;
	private String team2;
	
	//used for resetting
	private String originalTeam1;
	private String originalTeam2;
	
	private int number;
	private int nextGame; //-1 if is championship game
	private String winner; //null if not played yet
	
	public Game(String team1, String team2, int number, int nextGame) {
		this.team1 = team1;
		originalTeam1 = team1;
		originalTeam2 = team2;
		this.team2 = team2;
		this.number = number;
		this.nextGame = nextGame;
	}
	
	public void reset() {
		this.team1 = originalTeam1;
		this.team2 = originalTeam2;
		this.winner = null;
	}	
	
	public String getWinner() {
		return winner;
	}
	
	public boolean wasPlayed() {
		return winner != null;
	}
	
	public void setWinner(String winner) {
		this.winner = winner;
		notifyAllObservers(); //game is over, notify bracket and teams
	}
	
	
	public boolean hasTeam1() {
		return (!team1.isEmpty());
	}
	
	public boolean hasTeam2() {
		return (!team2.isEmpty());
	}
	
	public void setTeam1(String name) {
		team1 = name;
	}
	
	public void setTeam2(String name) {
		team2 = name;
	}
	
	public boolean isChampionship() {
		return (nextGame == -1);
	}

	/**
	 * @return the team1
	 */
	public String getTeam1() {
		return team1;
	}

	/**
	 * @return the team2
	 */
	public String getTeam2() {
		return team2;
	}

	/**
	 * @return the number
	 */
	public int getNumber() {
		return number;
	}

	/**
	 * @return the nextGame
	 */
	public int getNextGame() {
		return nextGame;
	}
}
