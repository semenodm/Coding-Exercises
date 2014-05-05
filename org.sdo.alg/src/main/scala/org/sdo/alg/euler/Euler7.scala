package org.sdo.alg.euler

import scala.collection.immutable.TreeMap


/**
 * Created with IntelliJ IDEA.
 * User: dsemenov
 * Date: 5/1/14
 * Time: 2:21 PM
 */
object Euler7 {
  type PrimeProgression = Stream[Long]

  def longStream(seed: Long, step: Long): PrimeProgression = seed #:: longStream(seed + step, step)

  def applyStream(primeProgression: PrimeProgression) = {
    map.get(primeProgression.head) match {
      case None => primeProgression.head -> List(primeProgression)
      case Some(progression) => primeProgression.head -> (primeProgression :: progression)
    }
  }

  var map = TreeMap.empty[Long, List[PrimeProgression]] + (2l -> List(longStream(4l, 2l)))

  def primeStream(prime: Long): PrimeProgression = {
    map.headOption match {
      case Some((primeMultiple, progressions)) =>
        if (prime < primeMultiple) {
          map += prime * prime -> List(longStream(prime * prime, prime))
          prime #:: primeStream(prime + 1)
        } else {
          map = map - primeMultiple ++ progressions.map {
            progression => applyStream(progression.tail)
          }
          primeStream(prime + 1)
        }
    }
  }

  def main(args: Array[String]) {

    time {primeStream(2) take 10001 takeRight 1 foreach println }
  }

  def time[A](f: => A) = {
    val s = System.nanoTime
    val ret = f
    println("time: "+(System.nanoTime-s)/1e6+"ms")
    ret
  }
}
