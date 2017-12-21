package com.achilles.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomUtil {
	public List<Integer> RandomPickUp(List<Integer> input, int maxSize) {
		List<Integer> result = new ArrayList<Integer>();
		Random r = new Random();
		do {
			result.clear();
			for(int i = 0; i<input.size(); i ++) {
				int flag = r.nextInt(100);
				if(flag < 50 ) 
					result.add(input.get(i));
			}
		}
		while(result.size() > maxSize);
		
		return result;
	}
	
	public boolean ProbabilityGenerator(int percent) {
		boolean result = false;
		Random r = new Random();
		int flag = r.nextInt(100);
		result = flag < percent ? true : false;
		return result;
	}
}
