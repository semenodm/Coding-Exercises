package org.sdo.alg.other

import scala.math

/**
  * Created by dsemenov
  * Date: 1/7/16.
  */
trait WaterCollect {
  def collectWater(towers: List[Int]): Int = {
    def walkMax(towers: List[Int]): List[Int] = {
      var tempMax = 0
      towers.map {
        elem =>
          if (tempMax < elem) {
            tempMax = elem
            tempMax
          }
          else
            tempMax
      }
    }

    val lefMax = walkMax(towers)
    val rigthMax = walkMax(towers.reverse).reverse

    towers.foldLeft((0, lefMax, rigthMax)) { (context, elem) =>
      context match {
        case (volume, leftM, rightM) =>
          val left = leftM.head
          val right = rightM.head
          val min = math.min(left, right)
          (volume + min - elem, leftM.tail, rightM.tail)
      }
    } _1
  }

}
