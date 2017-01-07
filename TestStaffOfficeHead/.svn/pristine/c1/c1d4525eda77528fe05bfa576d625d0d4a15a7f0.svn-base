package util;

import java.util.Random;


public class TestCaseNumberUtils {
	
	public static int getRandomNumberFrom(int min, int max) {
		Random ran = new Random();
		int randomNumber;

		if (max >= min) {
			randomNumber = ran.nextInt((max + 1) - min) + min;
			return randomNumber;
		} if(max == min){
			return max;
		} else {
			randomNumber = -1;
		}
		return randomNumber;
	}

}
