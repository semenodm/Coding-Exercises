package org.sdo.alg.euler

/**
 * Created with IntelliJ IDEA.
 * User: dsemenov
 * Date: 5/12/14
 * Time: 6:14 PM
 */
object Euler25 {

  def fibonacciStream(penultimate: BigInt, last: BigInt): Stream[BigInt] = {
    val next = penultimate + last
    next #:: fibonacciStream(last, next)
  }

  def main(args: Array[String]) {
    println( 1 + (1 #:: 1 #:: fibonacciStream(1, 1) takeWhile (_.toString().size <= 999 )  size) )
  }
}
