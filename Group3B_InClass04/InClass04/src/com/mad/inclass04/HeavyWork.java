/**
 * 
 * ITCS5180 - InClass04
 * Group3B_InClass04
 * 1. Naga Bijesh roy Raya
 * 2. James Budday
 * 3. Shyam Mohan Raman
 */
package com.mad.inclass04;
import java.util.Random;

public class HeavyWork {
	static final int COUNT = 900000;
	static double getNumber(){
		double num = 0;
		Random rand = new Random();
		for(int i=0;i<COUNT; i++){
			num = num + rand.nextDouble();
		}
		return num / ((double) COUNT);
	}
}