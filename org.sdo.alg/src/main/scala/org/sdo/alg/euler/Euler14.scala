package org.sdo.alg.euler

/**
 * Created by dsemenov on 5/17/14.
 */
object Euler14 {

  object Even {
    def unapply(x:Long) = if(x%2 == 0) Some(x) else None
  }

  object Odd {
    def unapply(x:Long) = if(x%2 == 1) Some(x) else None
  }

  object One {
    def unapply(x:Long) = if(x==1) Some(x) else None
  }

  def collatzSeq(sead : Long) : Long = {
     sead match {
       case 1 => 1
       case Odd(x) => 1 + collatzSeq(3 * sead + 1)
       case Even(x) => 1 + collatzSeq(sead/2)
     }
  }

  def solution(n : Long) : Stream[Long] = {
    collatzSeq(n) #:: solution(n+1)
  }

  def main(args: Array[String]) {
    println(collatzSeq(13))

    println(solution(1).take(1000000).zipWithIndex.maxBy(_._1)._2 + 1)
  }
}
