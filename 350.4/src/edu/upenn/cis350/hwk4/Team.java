package edu.upenn.cis350.hwk4;

public class Team extends Observer {
	
	private String name;
	private String region;
	private int seed;
	private double elo;
	private int numWins = 0;
	private int numLosses = 0;
	
	public Team(String name, String region, int seed, double elo) {
		this.name = name;
		this.region = region;
		this.seed = seed;
		this.elo = elo;
	}
	
	public void resetWins() {
		numWins = 0;
		numLosses = 0;
	}

	/**
	 * @return the name
	 */
	String getName() {
		return name;
	}


	/**
	 * @return the region
	 */
	String getRegion() {
		return region;
	}


	/**
	 * @return the seed
	 */
	int getSeed() {
		return seed;
	}


	/**
	 * @return the elo
	 */
	double getElo() {
		return elo;
	}

	/**
	 * @param elo the elo to set
	 */
	void setElo(double elo) {
		this.elo = elo;
	}

	@Override
	public void update(Subject s) {
		Game g = (Game) s;
		if (g.getWinner().equalsIgnoreCase(this.name)) numWins++;
		else numLosses++;
	}

	/**
	 * @return the numWins
	 */
	int getNumWins() {
		return numWins;
	}

	/**
	 * @return the numLosses
	 */
	int getNumLosses() {
		return numLosses;
	}
}
