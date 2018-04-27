package edu.upenn.cis350.hwk6;


public class Main {

	public static void main(String[] args) {
		//inputFile.png outputFile.png blurSize threadCount
		
		if (args.length < 4) {
			System.err.println("Not Enough Args");
			System.exit(-1);
		}

		
		int [][] inputPic = null;
		
		inputPic = ImageData.load(args[0]);
		if (inputPic.length == 0) {
			System.err.println("Couldn't Read Image. Exiting...");
			System.exit(-1);
		}
				
		int threadCount = -1;
		try {
			threadCount = Integer.parseInt(args[3]);
			if (threadCount < 1) {
				System.err.println("Please insert a valid # (>1) for Thread Count. Exiting..");
				System.exit(-1);
			}
		} catch (NumberFormatException e) {
			System.err.println("Please insert a # for Thread Count. Exiting..");
			System.exit(-1);
		}
		
		int blurSize = -1;
		try {
			blurSize = Integer.parseInt(args[2]);
			if (blurSize < 1) {
				System.err.println("Please insert a valid # (>=1) for Blur Size. Exiting..");
				System.exit(-1);
			}
		} catch (NumberFormatException e) {
			System.err.println("Please insert a # for Blur Size. Exiting..");
			System.exit(-1);
		}
		
		
		MultiBlur m = new MultiBlur(inputPic);
		try {
			int [] [] blurredPic = m.blurImage(threadCount, blurSize);
			try {
				ImageData.save(blurredPic, args[1]);
			} catch (Exception e) {
				System.err.println("Error Saving to specified filename. Exiting...");
				System.exit(-1);
			}
		} catch (InterruptedException e) {
			System.err.println("Something went wrong...");
			System.exit(-1);
		}
		
		

	}

}
