package org.sdo.akka

import akka.actor.SupervisorStrategy.{Decider, Restart, Stop}
import akka.actor._
import akka.testkit.{DefaultTimeout, ImplicitSender, TestKit}
import org.scalatest.{Matchers, WordSpecLike}
import org.sdo.akka.HandlingTerminationMessage._

import scala.concurrent.duration._

/**
 * Created by dsemenov
 * Date: 1/13/15.
 */
object HandlingTerminationMessage {

  case object GetYourRouter

  case object GiveMeWatchee

  case class ImWatching(watchee: ActorRef)

  case class ActorTerminated(actor: ActorRef)

  class RouteeActor extends Actor {
    def receive = {
      case _ ⇒
    }
  }

  trait StrategyProvider {
    def provideDecider: Decider
  }

  class SimpleWatcher(testActor: ActorRef) extends Actor {
    this : StrategyProvider =>
    var watchee: ActorRef = _

    override val supervisorStrategy = new OneForOneStrategy()(provideDecider)

    @throws[Exception](classOf[Exception])
    override def preStart(): Unit = {
      super.preStart()
      watchee = context.actorOf(Props[RouteeActor], "watchee")
      context.watch(watchee)
    }

    override def receive: Receive = {
      case GiveMeWatchee => sender() ! ImWatching(watchee)
      case Terminated(actor) => testActor ! ActorTerminated(actor)
    }
  }
}

class HandlingTerminationMessage
  extends TestKit(ActorSystem("ActorSystem"))
  with WordSpecLike
  with Matchers
  with DefaultTimeout
  with ImplicitSender {

  "Watcher" should {
    "receive termination event when superuser stops child" in {
      val watcher = system.actorOf(Props(new SimpleWatcher(testActor) with StrategyProvider {
        override def provideDecider: Decider = {case _ => Stop}
      }), "watcher_stoper")

      watcher ! GiveMeWatchee
      val watchee = expectMsgType[ImWatching].watchee

      watchee ! Kill

      println(s"actor terminated ${expectMsgType[ActorTerminated].actor}")
    }
  }

  "Watcher" should {
    "receive termination event when superuser restarts child" in {
      val watcher = system.actorOf(Props(new SimpleWatcher(testActor) with StrategyProvider {
        override def provideDecider: Decider = {case _ => Restart}
      }), "watcher_restarter")

      watcher ! GiveMeWatchee
      val watchee = expectMsgType[ImWatching].watchee

      watchee ! Kill

      println(s"actor terminated ${expectMsgType[ActorTerminated].actor}")
    }
  }
}
