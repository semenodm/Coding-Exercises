package org.sdo.alg.euler

/**
 * Created with IntelliJ IDEA.
 * User: dsemenov
 * Date: 3/30/14
 * Time: 12:57 AM
 */
object Euler31 {
  def coinSums(coins: List[Int], sum: Int): Int = {
    coins match {
      case List() => 0
      case coin :: tail => {
        (for (partialSum <- 0 to sum by coin) yield if (partialSum == sum) 1 else coinSums(tail, sum - partialSum)).sum
      }
    }
  }


  def main(args: Array[String]) {
    println(coinSums(List(1, 2, 5, 10, 20, 50, 100, 200), 200))
  }
}
