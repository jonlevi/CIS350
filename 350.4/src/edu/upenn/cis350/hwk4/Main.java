package edu.upenn.cis350.hwk4;

public class Main {

	public static void main(String[] args) {
		if (args.length <3) {
			System.err.println("Not enough Input Arguments: Please input Elofile, GamesFile and LogFile");
			System.exit(-1);
		}
		Logger.setFile(args[2]);
		Logger.getInstance().log("System starting up with args " + args[0] + " " + args[1] + " " + args[2]);
		CommandLineInterface cli = new CommandLineInterface(args[0], args[1]);
		cli.run();
		Logger.getInstance().close();
	}

}
