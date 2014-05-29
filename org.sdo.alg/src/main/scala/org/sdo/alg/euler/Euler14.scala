package org.sdo.alg.euler

import org.sdo.alg.euler.Utils.time
import scala.collection.mutable

/**
 * Created by dsemenov on 5/17/14.
 */
object Euler14 {

  object Even {
    def unapply(x: Int) = if (x % 2 == 0) Some(x) else None
  }

  object Odd {
    def unapply(x: Int) = if (x % 2 == 1) Some(x) else None
  }

  var interimResults = mutable.HashMap[Int, Int]()

  def collatzSeq(sead: Int): Int = {
    interimResults.get(sead) match {
      case None =>
        val result = sead match {
          case 1 => 1
          case Odd(x) => 1 + collatzSeq(3 * sead + 1)
          case Even(x) => 1 + collatzSeq(sead / 2)
        }
        interimResults += sead -> result
        result
      case Some(result) =>
        result
    }

  }

  def solution(n: Int): Stream[Int] = {
    collatzSeq(n) #:: solution(n + 1)
  }

  def main(args: Array[String]) {
    interimResults = mutable.HashMap[Int, Int]()
    println(time {
      solution(1).take(100000).zipWithIndex.maxBy(_._1)._2 + 1
    })
  }
}
