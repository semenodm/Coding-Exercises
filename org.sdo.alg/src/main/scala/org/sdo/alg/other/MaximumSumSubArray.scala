package org.sdo.alg.other

import org.sdo.alg.other.MaximumSumSubArray.Context

/**
  * Created by dsemenov
  * Date: 1/21/16.
  */

object MaximumSumSubArray {

  case class Context(runningSum: Int,
                     arrayBounds: (Int, Int),
                     globalMaxSum: Int,
                     lastKnown: (Int, (Int, Int)) = (Int.MinValue, (0, 0))
                    )

}

trait MaximumSumSubArray {

  def slidingSum(input: List[Int]): List[Int] = {
    input.zipWithIndex.foldLeft(Context(runningSum = 0, arrayBounds = (0, 0), globalMaxSum = 0)) {
      case (Context(runningSum, (offset, length), globalMaxSum, lastKnown@(lastKnownMax, _)), (element, idx)) =>
        val newSum = if (runningSum + element > 0) runningSum + element else 0
        val last = if (runningSum > 0 && newSum <= 0 && lastKnownMax < globalMaxSum) (globalMaxSum, (offset, length)) else lastKnown
        val (leftBound, r) = if (newSum > 0 && runningSum <= 0)
          (idx, 1)
        else (offset, length)

        val (newGlobalMaxSum, rightBound) = if (newSum > globalMaxSum)
          (newSum, idx - leftBound + 1)
        else
          (globalMaxSum, r)
        Context(newSum, (leftBound, rightBound), newGlobalMaxSum, last)
    } match {
      case Context(_, (o, l), latestKnownMax, (lastKnownMax, (offset, length))) =>
        if (latestKnownMax > lastKnownMax) input.slice(o, o + l) else input.slice(offset, offset + length)
    }
  }
}
