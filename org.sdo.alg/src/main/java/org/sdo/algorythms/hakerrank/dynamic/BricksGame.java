package org.sdo.algorythms.hakerrank.dynamic;

import java.util.Scanner;

/**
 * Created by dsemenov
 * Date: 10/28/16.
 */
public class BricksGame {
  private final int n;
  private final int[] bricks;
  int result = 0;

  private BricksGame(int n) {
    this.n = n;
    this.bricks = new int[n];
  }

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    int T = scanner.nextInt();

    while (T-- > 0) {
      int n = scanner.nextInt();
      BricksGame bricksGame = new BricksGame(n);
      for (int i = 0; i < n; i++) {
        bricksGame.add(scanner.nextInt(), i);
      }
      System.out.println(bricksGame.solve());
    }

  }

  private void add(int brick, int i) {
    bricks[i] = brick;
  }

  private int solve() {
    return solveInternal(0);
  }

  private int solveInternal(int offset){
    if (offset >= n) return 0;

    //int distToEnd = n - offset;
    //n   offset  distToEnd
    //5   2       3
    //if (distToEnd <=3) return distToEnd;

    int max = 0;
    int total = 0;
    for (int i = 0; i < 3; i++) {
      if(offset + i >= n) return total;
      total += bricks[offset + i];
      for (int j = 0; j < 3; j++) {
         int t = total + solveInternal(offset + i + 2 + j);
        if(t > max){
          max = t;
        }
      }
    }
    return max;
  }
  //0 1 1 1 999
}
