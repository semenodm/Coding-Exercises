package org.sdo.algorythms.sqrtsecomposition;

import static java.lang.Math.*;

public class SqrtDecompositionSum implements Algorithm {

	private int[] input;
	private int[] localSums;
	private int complexity;

	public SqrtDecompositionSum(int[] input) {
		this.input = input;
		calcLocalSums();
	}

	protected int[] calcLocalSums() {
		int localSumsCellLength = (int) sqrt(input.length);
		localSums = new int[localSumsCellLength];
		for (int i = 0; i < localSumsCellLength * localSumsCellLength; i++) {
			localSums[i / localSumsCellLength] += input[i];
			if (i / localSumsCellLength != 0 && i % localSumsCellLength == 0) {
				localSums[i / localSumsCellLength] += localSums[i
						/ localSumsCellLength - 1];
			}

		}
		return localSums;
	}

	public Integer calcSummBetween(int L, int R) {
		complexity = 0;
		int lBrudeIndex = convertRealToSumsIndex(L - 1);
		int rBrudeIndex = convertRealToSumsIndex(R);

		int sum = 0;
		if (R % sqrt(input.length) == 0) {
			sum = calcBrudeSum(rBrudeIndex+1);
		} else {
			sum = calcBrudeSum(rBrudeIndex) + calcDeltaSum(R);
		}
		if (L % sqrt(input.length) == 0) {
			sum -= calcBrudeSum(lBrudeIndex+1);
		} else {
			sum -= calcBrudeSum(lBrudeIndex) + calcDeltaSum(L - 1);
		}
		System.out.println("Complexity is: " + complexity);
		return sum;

	}

	private int calcDeltaSum(int l) {
		int sum = 0;
		for (int i = convertRealToSumsIndex(l) * localSums.length; i <= l; i++) {
			sum += input[i];
			complexity++;
		}
		return sum;
	}

	private int calcBrudeSum(int index) {
		return index == 0 ? 0 : localSums[index - 1];
	}

	public void set(int index, int value) {
		int sumsIndex = convertRealToSumsIndex(index);
		int oldSum = localSums[sumsIndex];
		int oldValue = input[index];
		int newSum = oldSum - oldValue + value;
		localSums[sumsIndex] = newSum;
		input[index] = value;

	}

	private int convertRealToSumsIndex(int index) {
		return index / localSums.length;
	}

	public int getComplexity() {
		return complexity;
	}

}
