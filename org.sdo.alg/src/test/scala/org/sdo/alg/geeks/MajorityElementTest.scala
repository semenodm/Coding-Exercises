package org.sdo.alg.geeks

import org.scalatest.{FlatSpec, Matchers}

import scala.None

/**
  * Created by dsemenov
  * Date: 12/30/15.
  */
class MajorityElementTest extends FlatSpec with Matchers with MajorityElement[Int]{
  it should "no majority element in 2,2,2,3,3,3,3,5,6" in {
    findMajorityElement(List(2, 2, 2, 3, 3, 3, 3, 5, 6)) should be(None)
  }
  it should "2 is a majority element in 2, 2, 3, 5, 2, 2, 6" in {
    findMajorityElement(List(2, 2, 3, 5, 2, 2, 6)) should be(Some(2))
  }

  it should "A is a majority element in AbAcAdA" in {
     class MyClass extends MajorityElement[String]
    val m = new MyClass
    m.findMajorityElement(List("A","b","A","c","A","d","A"))
  }
}
