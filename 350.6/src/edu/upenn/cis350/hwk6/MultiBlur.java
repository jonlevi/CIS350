package edu.upenn.cis350.hwk6;

import java.awt.Color;


public class MultiBlur {
	
	private int [] [] image;
	

	public MultiBlur(int [] [] image) {
		this.image = image;
	}
	

	public int [][] blurImage(int numThreads, int blurSize) throws InterruptedException {
		
		int [] [] copy = new int[image.length][image[0].length];
		int size = image.length;
		if (numThreads > size) {
			numThreads = size;
		}
		
		Thread [] threads = new Thread[numThreads];
		
		int stepSize = size/numThreads;
		int count = 0;
		boolean exit = false;
		
		//Divide image into sections for each thread
		for (int i = 0; i < size && !exit; i+=stepSize) {
			int end = -1;
			
			// Deals with size not divisible by numThreads
			if (i + 2*stepSize >= size && size % numThreads != 0) {
				end = size;
				exit = true;
			} else {
				end = Math.min(i+stepSize, size);
			}
			//Give this section to a thread, and start it 
			Thread t = blurPart(copy, i, end, blurSize);
			t.start();
			threads[count++] = t;
		}
		
		//join each thread back to main thread
		for (int x = 0; x < numThreads; x++) {
			threads[x].join();
		}
		return copy;
	}
	
	// Returns a handle to a thread with a runnable to blur 
	// using a blur radius of blurSize
	// the section of the image on the range [start:end)
	private Thread blurPart(int [] [] image, int start, int end,  int blurSize) {
		Thread t = new Thread(new Runnable() {
		
			@Override
			public void run() {
				for (int i  = start; i < end; i++) {
					for (int j = 0; j <image[0].length; j++) {
						Color blurred = blurPixel(i, j, blurSize);
						image[i][j] = blurred.getRGB();
					}
				}
				
			}
		});
		
		return t;
	}

	// Returns the blurred color of the pixel at row, col of 
	// using the class level image. Does not change the actual
	// pixel, just returns the new value as a Color object
	private Color blurPixel(int row, int col, int blurSize) {
		
		if (blurSize >= Math.min(image.length, image[0].length)) {
			blurSize = Math.min(image.length-1, image[0].length-1);
		}
		
		int left = Math.max(col-blurSize,0);
		int right = Math.min(col+blurSize+1, image[0].length);
		int bottom = Math.min(row+blurSize+1, image.length);
		int top = Math.max(row-blurSize,0);
		
		int redSum = 0, blueSum = 0, greenSum = 0, count = 0;
		
		for (int j = top; j < bottom; j++) {
			for (int i = left; i < right; i++) {
				Color c = new Color(image[j][i]);
				redSum += c.getRed();
				greenSum += c.getGreen();
				blueSum += c.getBlue();
				count++;
			}
		}
		return new Color(redSum/count, greenSum/count, blueSum/count);
	}
}
