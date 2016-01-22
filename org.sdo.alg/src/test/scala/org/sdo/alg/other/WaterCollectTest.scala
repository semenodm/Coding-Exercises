package org.sdo.alg.other

import org.scalatest.{Matchers, FlatSpec}

/**
  * Created by dsemenov
  * Date: 1/7/16.
  */
class WaterCollectTest extends FlatSpec with Matchers with WaterCollect{

  behavior of "WaterCollectTest"

  it should "collectWater" in {
    waterCollect2(List(5,3,7,2,6,4,5,9,1,2)) should be(14)

  }

  it should "collect water in pit" in {
    waterCollect2(List(5,0,0,0,0,0,0,0,0,2)) should be(16)

  }

  it should "collect water in pit with non flat bottom" in {
    waterCollect2(List(5,0,0,1,0,1,0,1,0,2)) should be(13)

  }

  it should "collect water in pit with non flat bottom and islands" in {
    waterCollect2(List(5,0,0,2,0,2,0,1,0,2)) should be(11)

  }

  it should "collect water in pit with non flat bottom and islands and mountains" in {
    waterCollect2(List(5,0,0,2,0,3,0,1,0,2)) should be(15)

  }

  it should "collect water in pit with non flat bottom and islands and mountains1" in {
    waterCollect2(List(2,1,0,6,1,0,0,1,0,5)) should be(26)

  }

}
