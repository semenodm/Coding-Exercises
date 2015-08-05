package org.sdo.search

import akka.actor.{Actor, ActorRef, Props, ActorSystem}
import akka.testkit.TestKit
import org.scalatest._
import org.sdo.search.Aggregator.{Engines, Results, Criteria}

import scala.collection.mutable
import scala.concurrent.duration.FiniteDuration
import scala.concurrent.duration._

class AggregatorTest extends TestKit(ActorSystem("SearchAggregator")) with FunSpecLike {

  describe("Aggregator") {

    it("aggregates results from all engines") {
      val aggregatorRef = system.actorOf(Props(Aggregator()))
      aggregatorRef ! Criteria(List("Photos", "Videos"), "akka", testActor)
      expectMsg(Results(mutable.Map("Photos" -> "Photos results for akka",
        "Videos" -> "Videos results for akka")))
    }

    it("aggregates results concurrently") {
      val aggregatorRef = system.actorOf(Props(Aggregator()))
      aggregatorRef ! Criteria(List("Photos", "Videos"), "akka", testActor)
      expectMsg(300 millis, Results(mutable.Map("Photos" -> "Photos results for akka",
        "Videos" -> "Videos results for akka")))
    }

    it("has timeout and aggregates only ready results"){
      val aggregatorRef = system.actorOf(Props(Aggregator()))
      aggregatorRef ! Criteria(List("Photos", "Videos", "Text"), "akka", testActor)
      expectMsg(300 millis, Results(mutable.Map("Photos" -> "Photos results for akka",
        "Videos" -> "Videos results for akka")))
    }
  }
}
