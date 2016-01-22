package org.sdo.alg.other

import org.scalatest.{Matchers, FlatSpec}

/**
  * Created by dsemenov
  * Date: 1/21/16.
  */
class MaximumSumSubArrayTest extends FlatSpec with Matchers with MaximumSumSubArray {
  behavior of "MaximumSumSubArray"
  it should "find sub-array in simple case" in {
    slidingSum(List(-2, 1, -3, 4, -1, 2, 1, -5, 4)) should be(List(4, -1, 2, 1))
  }

  it should "find sub-array in case when sliding maximum fluctuates in negative way" in {
    slidingSum(List(-2, 1, -3, 4, -1, 2, 1, -5, 7)) should be(List(4, -1, 2, 1, -5, 7))
  }

  it should "find sub-array in case when first sub-array has sum more then following" in {
    slidingSum(List(-2, 1, -3, 4, -1, 2, 1, -15, 4, 1, -9)) should be(List(4, -1, 2, 1))
  }

  it should "find sub-array in all negative array" in {
    slidingSum(List(-2, -3, -1, -15, -9)) should be(List(-1))
  }
  it should "find sub-array in all positive array" in {
    slidingSum(List(2, 3, 1, 15, 9)) should be(List(2, 3, 1, 15, 9))
  }
}
