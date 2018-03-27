/*
 * Represents one instance of a Super Bowl
 */


public class SuperBowl {
	
	private int year; // year when Super Bowl was held
	private String winner; // winning team
	private String loser; // losing team
	private int winnerScore; // points scored by winning team
	private int loserScore; // points scored by losing team
	private String number; // Roman numeral representation of Super Bowl number
	private Location location; // city where Super Bowl was held
	
	public SuperBowl(int year, String winner, String loser, int winnerScore, int loserScore, String number, String city, String state) {
		this.year = year;
		this.winner = winner;
		this.loser = loser;
		this.winnerScore = winnerScore;
		this.loserScore = loserScore;
		this.number = number;
		location = new Location(city, state);
	}
	
	public int getYear() { return year; }
	public String getWinner() { return winner; }
	public String getLoser() { return loser; }
	public int getWinnerScore() { return winnerScore; }
	public int getLoserScore() { return loserScore; }
	public String getNumber() { return number; }
	public Location getLocation() { return location; }
	
	public String toString() {
		return "In " + year + " the " + winner + " defeated the " + loser + " by a score of " + winnerScore + "-" + loserScore + " in Super Bowl " + number + " in " + location.getCity() + location.getState();
	}
	
}
