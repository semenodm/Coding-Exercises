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
      case None => map += primeProgression.head -> List(primeProgression)
      case Some(progression) => map += primeProgression.head -> (primeProgression :: progression)
    }
  }

  var map = new TreeMap[Long, List[PrimeProgression]]()

  def primeStream(prime: Long): PrimeProgression = {

    if (!map.isEmpty) {
      map.head match {
        case (primeMultiple, progressions) =>
          if (prime < primeMultiple) {
            map += prime * prime -> List(longStream(prime * prime, prime))
            prime #:: primeStream(prime + 1)
          } else {
            map -= primeMultiple
            progressions.foreach{ progression => applyStream(progression.tail) }
            primeStream(prime + 1)
          }
      }
    } else {
      map += prime * prime -> List(longStream(prime * prime, prime))
      prime #:: primeStream(prime + 1)
    }
  }

  def main(args: Array[String]) {
    primeStream(2) take 10001 takeRight 1 foreach println
  }
}
