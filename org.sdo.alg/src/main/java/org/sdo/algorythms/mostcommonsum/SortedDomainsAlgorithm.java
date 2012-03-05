package org.sdo.algorythms.mostcommonsum;

import java.util.Arrays;

public class SortedDomainsAlgorithm implements Algorithm{

	public int extractCommonSum(int[] input) {
		Arrays.sort(input);
		int startEndix=0;		
		int element = input[0];
		int count = 0;
		for (int i = 1; i < input.length; i++) {
			if(input[i] != input[i-1]){
				int tempCount = (i - startEndix);
				if(tempCount >= count){
					element = input[i-1];					
					count = tempCount;
				}
				startEndix = i;				
			}
			if(i == input.length-1){				
				int tempCount = (i - startEndix)+1;
				if(tempCount >= count){
					element = input[i-1];					
					count = tempCount;
				}
				startEndix = i;
			}
			
		}
		return element * count;
	}
	
}
