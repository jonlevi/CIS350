package edu.upenn.cis350.hwk6;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.awt.Color;
import java.util.concurrent.Callable;

public class MultiBlur {
	
	private int [] [] image;
	
	
	public MultiBlur(int [] [] image) {
		this.image = image;
	}
	
	
	public int [][] blurImage(int numThreads, int blurSize) throws InterruptedException {
		
		int [] [] copy = new int[image.length][image[0].length];
	
		Thread [] threads = new Thread[numThreads];
		int size = image.length;
		int stepSize = size/numThreads;
		int count = 0;
		boolean exit = false;
		for (int i = 0; i < size && !exit; i+=stepSize) {
			int end = -1;
			if (i + 2*stepSize >= size && size % numThreads != 0) {
				end = size;
				exit = true;
			} else {
				end = Math.min(i+stepSize, size);
			}
			Thread t = blurPart(copy, i, end, blurSize);
			t.start();
			threads[count++] = t;
		}

		for (int x = 0; x < numThreads; x++) {
			threads[x].join();
		}
		return copy;

	}
	
	
	public Thread blurPart(int [] [] image, int start, int end,  int blurSize) {
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

	private Color blurPixel(int row, int col, int blurSize) {
		
		if (blurSize >= Math.min(image.length, image[0].length)) {
			blurSize = Math.min(image.length-1, image[0].length-1);
		}
		
		int left = Math.max(col-blurSize,0);
		int right = Math.min(col+blurSize+1, image[0].length);
		int bottom = Math.min(row+blurSize+1, image.length);
		int top = Math.max(row-blurSize,0);
		
		int redSum = 0;
		int blueSum = 0;
		int greenSum = 0;
		int count = 0;
		
		for (int j = top; j < bottom; j++) {
			for (int i = left; i < right; i++) {
				Color c = new Color(image[j][i]);
				redSum += c.getRed();
				greenSum += c.getGreen();
				blueSum += c.getBlue();
				count++;
			}
		}
		
		int blue = blueSum/count;
		int green = greenSum/count;
		int red = redSum/count;
		return new Color(red, green, blue);
	}

}
