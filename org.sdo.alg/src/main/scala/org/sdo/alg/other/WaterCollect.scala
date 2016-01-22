package org.sdo.alg.other

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

  def waterCollect2(towers: List[Int]): Int = towers match {
    case Nil => 0
    case head :: Nil => 0
    case head :: tail =>

      val leftTower = head
      val rightTower = tail.last
      val waterLevel = math.min(leftTower, rightTower)

      val water = (towers.length - 2) * waterLevel
      val (nextTowers, leftOrRight) = if (leftTower < rightTower) (towers.drop(1), Left(leftTower)) else (towers.dropRight(1), Right(rightTower))
      water + waterCollectInternal(nextTowers, waterLevel, leftOrRight)
  }

  def waterCollectInternal(towers: List[Int], waterLevel: Int, leftOrRight: Either[Int, Int]): Int = towers match {
    case Nil => 0
    case head :: Nil => 0
    case head :: tail =>
      val leftTower = head
      val rightTower = tail.last
      val newWaterLevel = math.max(math.min(leftTower, rightTower), waterLevel)
      val currentTowerAdjustment = leftOrRight match {
        case Left(_) if newWaterLevel < leftTower => -waterLevel
        case Left(_) => -math.min(leftTower, waterLevel)
        case Right(_) if newWaterLevel < rightTower => -waterLevel
        case Right(_) => -math.min(rightTower,waterLevel)
      }

      val waterAdjustment = currentTowerAdjustment + (towers.length - 2) * (newWaterLevel - waterLevel)
      val (nextTowers, newLeftOrRight) = if (leftTower < rightTower) (towers.drop(1), Left(leftTower)) else (towers.dropRight(1), Right(rightTower))
      waterAdjustment + waterCollectInternal(nextTowers, newWaterLevel, newLeftOrRight)
  }
}
