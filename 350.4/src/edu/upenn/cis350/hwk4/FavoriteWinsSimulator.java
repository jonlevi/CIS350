package edu.upenn.cis350.hwk4;

public class FavoriteWinsSimulator implements GameSimulator {

	@Override
	public Team simulate(Team team1, Team team2) {
		if (team1.getElo() > team2.getElo())
			return team1;
		else if (team1.getElo() < team2.getElo() || Math.random() < 0.5) 
			return team2;
		return team1;
	}

}
