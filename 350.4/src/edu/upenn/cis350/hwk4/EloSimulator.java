package edu.upenn.cis350.hwk4;

public class EloSimulator implements GameSimulator {

	@Override
	public Team simulate(Team team1, Team team2) {
		double elo1 = team1.getElo();
		double elo2 = team2.getElo();
		double diff = elo1 - elo2;
		double percentage = 1 / (Math.pow(10, -diff/400) + 1);
		if (Math.random() < percentage)
			return team1;
		else return team2;
	}

}
