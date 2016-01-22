package org.sdo.alg.other

import org.sdo.alg.other.MaximumSumSubArray.Context

/**
  * Created by dsemenov
  * Date: 1/21/16.
  */

object MaximumSumSubArray {

  case class Context(runningSum: Int = 0,
                     arrayBounds: (Int, Int) = (0, 0),
                     globalMaxSum: Int = Int.MinValue,
                     lastKnown: (Int, (Int, Int)) = (Int.MinValue, (0, 0))
                    )

}

trait MaximumSumSubArray {

  def slidingSum(input: List[Int]): List[Int] = {
    input.zipWithIndex.foldLeft(Context()) {
      case (Context(runningSum, (offset, length), globalMaxSum, lastKnown@(lastKnownMax, _)), (element, idx)) =>
        val newSum = if (runningSum + element > 0) runningSum + element else 0

        val last =
          if (runningSum > 0 && newSum <= 0 && lastKnownMax < globalMaxSum)
            (globalMaxSum, (offset, length))
          else if (element <= 0 && element > lastKnownMax)
            (element, (idx, 1))
          else
            lastKnown

        val (leftBound, r) = if (newSum > 0 && runningSum <= 0)
          (idx, 1)
        else (offset, length)

        val (newGlobalMaxSum, rightBound) = if (newSum > globalMaxSum && newSum > 0)
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
