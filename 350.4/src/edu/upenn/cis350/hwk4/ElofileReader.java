package edu.upenn.cis350.hwk4;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 
 * @author jonathanlevine
 *	Children Classes are responsible for Populating the nameToElo Map from their given filenames
 */
public abstract class EloFileReader {
	
	protected Map<String, Team> nameToTeam = new HashMap<String, Team>();
	
	public double getElo(String team) {
		return nameToTeam.get(team.toLowerCase()).getElo();
	}
	
	public Team getTeam(String team) {
		return nameToTeam.get(team.toLowerCase());
	}
	
	public Set<String> getAllTeams() {
		return nameToTeam.keySet();
	}
	
	public void setElo(String team, double elo) {
		nameToTeam.get(team.toLowerCase()).setElo(elo);
	}
	public boolean hasTeam(String team) {
		return nameToTeam.containsKey(team.toLowerCase());
	}
	
	public void log(String message) {
		Logger.getInstance().log(message);
	}
}
