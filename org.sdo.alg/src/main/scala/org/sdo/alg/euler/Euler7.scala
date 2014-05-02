package org.sdo.alg.euler

import java.util


/**
 * Created with IntelliJ IDEA.
 * User: dsemenov
 * Date: 5/1/14
 * Time: 2:21 PM
 */
object Euler7 {
  type Progressions = List[Stream[Long]]

  def longStream(seed : Long, step :Long): Stream[Long] = seed #:: longStream(seed + step, step)

  def applyStream(stream: Stream[Long]) = {
    val progressions = map.get(stream.head)
    if (progressions != null) {
      map.put(stream.head, stream :: progressions)
    } else {
      map.put(stream.head, List(stream))
    }
  }

  def applyStreams(progressions: Progressions): Unit = {
    if (!progressions.isEmpty) {
      applyStream(progressions.head.tail)
      applyStreams(progressions.tail)
    }
  }

  var map = new util.TreeMap[Long, Progressions]()

  def primeStream(prime: Long): Stream[Long] = {
    if (!map.isEmpty) {

      val head = map.firstEntry()
      if (prime < head.getKey) {
       // println(prime)
        map.put(prime * prime, List(longStream(prime * prime, prime)))
        prime #:: primeStream(prime + 1)
      } else {
        val progressions = head.getValue
        map.pollFirstEntry()
        applyStreams(progressions)
        primeStream(prime + 1)
      }
    } else {
      map.put(prime * prime, List(longStream(prime * prime, prime)))
      prime #:: primeStream(prime + 1)
    }
  }

  def main(args: Array[String]) {
    primeStream(2) take 10001 takeRight 1 foreach println
  }
}
