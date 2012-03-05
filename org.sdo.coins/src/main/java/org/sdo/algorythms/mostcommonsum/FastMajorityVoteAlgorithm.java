package org.sdo.algorythms.mostcommonsum;

public class FastMajorityVoteAlgorithm implements Algorithm{
	// This is an implementation of Fast Majority Vote Algorithm
	public int extractCommonSum(int[] input) {
		// Assume the first element is most common
		int possibleCommonInteger = input[0];
		int votes = 1;

		// drill down the array and vote
		for (int i = 1; i < input.length; i++) {
			// if 0 votes choose new candidate
			if (votes == 0) {
				possibleCommonInteger = input[i];
				votes++;
				// vote for candidate if it's repeated
			} else if (input[i] == possibleCommonInteger)
				votes++;
			else {
				// decrement votes if current is not candidate
				votes--;
			}
		}

		votes = 0;

		// calc number of common integer
		for (int i = 0; i < input.length; i++) {
			if (input[i] == possibleCommonInteger) {
				votes++;
			}
		}
		// return product
		return votes * possibleCommonInteger;
	}
}
