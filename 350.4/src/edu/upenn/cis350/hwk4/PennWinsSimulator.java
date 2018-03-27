package edu.upenn.cis350.hwk4;

public class PennWinsSimulator implements GameSimulator {

	@Override
	public Team simulate(Team team1, Team team2) {
		if (team1.getName().equalsIgnoreCase("penn")) {
			return team1;
		} else if (team2.getName().equalsIgnoreCase("penn")) {
			return team2;
		}
		else {
			GameSimulator g = new EloSimulator();
			return g.simulate(team1, team2);
		}
	}

}
