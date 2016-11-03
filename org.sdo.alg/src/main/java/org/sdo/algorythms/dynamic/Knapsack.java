package org.sdo.algorythms.dynamic;

import java.util.Scanner;

public class Knapsack {


  public static void main(String[] args) {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT. Your class should be named Solution. */
    Scanner scanner = new Scanner(System.in);
    int tests = scanner.nextInt();
    while (tests-- > 0) {
      int arraySize = scanner.nextInt();
      int k = scanner.nextInt();
      int[] array = new int[arraySize];
      for (int i = 0; i < arraySize; i++) {
        array[i] = scanner.nextInt();
      }
      int result = solve(array, 0, k, 0);
      System.out.println(result);
    }
  }

  private static int solve(int[] array, int index, int k, int curSum) {
    if (k == curSum) {
      return curSum;
    }
    if(index >= array.length){
      return curSum;
    }
    int maxSum = Integer.MIN_VALUE;
    while (curSum <= k) {
      int res = solve(array, index + 1, k, curSum);
      if(maxSum < res){
        maxSum = res;
      }
      curSum += array[index];
    }
    return maxSum > curSum - array[index] ? maxSum : curSum - array[index];
  }


}