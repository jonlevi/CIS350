package edu.upenn.cis350.hwk4;

public class CoinFlipSimulator implements GameSimulator {

	@Override
	public Team simulate(Team team1, Team team2) {
		if  (Math.random() < 0.5)
			return team1;
		else return team2;
	}
}
