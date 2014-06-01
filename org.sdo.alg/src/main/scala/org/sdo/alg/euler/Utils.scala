package org.sdo.alg.euler

/**
 * Created by dsemenov on 5/23/14.
 */
object Utils {
  def time[A](f: => A) = {
    val s = System.nanoTime
    val ret = f
    println("time: "+(System.nanoTime-s)/1e6+"ms")
    ret
  }
}
