package org.sdo.alg.other

import org.scalatest.{Matchers, FlatSpec}

/**
  * Created by dsemenov
  * Date: 1/7/16.
  */
class WaterCollectTest extends FlatSpec with Matchers with WaterCollect{

  behavior of "WaterCollectTest"

  it should "collectWater" in {
    collectWater(List(5,3,7,2,6,4,5,9,1,2)) should be(14)

  }

}
