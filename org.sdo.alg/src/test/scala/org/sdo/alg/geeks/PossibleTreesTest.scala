package org.sdo.alg.geeks

import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by dsemenov
  * Date: 1/5/16.
  */
class PossibleTreesTest extends FlatSpec with Matchers with PossibleTrees[Int] {

  it should "be 1 preorder trees for 2" in {
    findPossiblePreOrderTreesFor(List(2)) should be(
      List(
        List(2)
      )
    )
  }

  it should "be 2 different preorder trees for 3, 2" in {
    findPossiblePreOrderTreesFor(List(3, 2)) should be(
      List(
        List(3, 2),
        List(2, 3)
      )
    )
  }

  it should "be 5 different preorder trees for 4, 5, 7" in {
    findPossiblePreOrderTreesFor(List(4, 5, 7)) should be(
      List(
        List(4, 5, 7),
        List(4, 7, 5),
        List(5, 4, 7),
        List(7, 4, 5),
        List(7, 5, 4)
      )
    )
  }


}
