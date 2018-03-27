package edu.upenn.cis350.hwk4;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Bracket extends Observer {
	
	private Map<Integer, Game> games;
	private GameSimulator simulator;
	private ElofileReader efr;
	
	public Bracket(Map<Integer, Game> games, GameSimulator gs, ElofileReader efr) {
		this.games = games;
		this.simulator = gs;
		this.efr = efr;
	}
	
	public String simulateTournament() {
		for (Map.Entry<Integer, Game> game : games.entrySet()) {
			if (!game.getValue().wasPlayed()) {
				simulate(game.getValue());
			}
		}
		
		return getResults();
	}
	
	private String getResults() {
		StringBuilder result = new StringBuilder();
		result.append("=====Team Results===== \n");
		List<String> teams = new ArrayList<String>(efr.getAllTeams());
		Collections.sort(teams);
		for (String team : teams) {
			Team t = efr.getTeam(team);
			result.append(String.format(team + ": %sW - %sL; Elo: %.2f \n",
					t.getNumWins(), t.getNumLosses(), t.getElo()));
		}
		result.append("\n \n=====Game Results===== \n");
		
		for (Map.Entry<Integer, Game> game : games.entrySet()) {
			Game g = game.getValue();
			
			result.append("Game #" + game.getKey());
			result.append(": " + g.getTeam1() + " vs. " + g.getTeam2());
			result.append(". The winner was " + g.getWinner());
			result.append("\n");
		}
		return result.toString();
	}

	@Override
	public void update(Subject s) {
		Game g = (Game) s;
		if (g.isChampionship()) {
			return;
		} else {
			String winner = g.getWinner();
			Game next = games.get(g.getNextGame());
			if (next.hasTeam1()) {
				next.setTeam2(winner);
			} else {
				next.setTeam1(winner);
			}
		}
		
	}

	private void simulate(Game game) {
		Team t1 = efr.getTeam(game.getTeam1());
		Team t2 = efr.getTeam(game.getTeam2());
		game.attach(t1);
		game.attach(t2);
		game.attach(this);
		
		Team winner = simulator.simulate(t1, t2);
		
		//calls notify when winner is set
		game.setWinner(winner.getName().toLowerCase());
		
		game.detach(t1);
		game.detach(t2);
		game.detach(this);
	}
	
	
	public void reset() {
		for (String team : efr.getAllTeams()) {
			Team t = efr.getTeam(team);
			t.resetWins();
		}
		for (Map.Entry<Integer, Game> game : games.entrySet()) {
			game.getValue().reset();
		}
	}
}



