package org.sdo.search

import java.util.UUID
import java.util.concurrent.TimeUnit

import akka.actor.{Actor, ActorRef, Props}
import akka.routing.RoundRobinPool
import org.sdo.search.Aggregator._

import scala.concurrent.duration._

/**
 * Created by dsemenov
 * Date: 8/3/15.
 */
object Aggregator {

  case class Criteria(searchTypes: List[String], search: String, requestor: ActorRef)

  case class Results(res: collection.mutable.Map[String, String])

  def apply() = new Aggregator() with ProdEngines

  trait Engines {
    def createEngines(): ActorRef
  }

  trait ProdEngines extends Engines {
    this: Actor =>
    override def createEngines(): ActorRef = context.actorOf(RoundRobinPool(2).props(Props[Engine]), "search_engines")
  }

  case class EngineCriteria(taskId: UUID, searchType: String, searchCriteria: String)

  case class EngineResult(taskId: UUID, searchType: String, searchResult: String)

  case class TaskState(requestor: ActorRef, taskId: UUID, enginesResults: collection.mutable.Map[String, String], var numberOfTasks: Int = 0)

}

class Engine extends Actor {
  override def receive: Actor.Receive = {
    case EngineCriteria(id, searchType, searchCriteria) =>
      TimeUnit.MILLISECONDS.sleep(200)
      sender() ! EngineResult(id, searchType, s"$searchType results for $searchCriteria")
  }
}

class Aggregator extends Actor {
  this: Engines =>

  var engines: ActorRef = _

  var state: Map[UUID, TaskState] = _

  var requestor: ActorRef = _

  @throws[Exception](classOf[Exception])
  override def preStart(): Unit = {
    super.preStart()
    engines = createEngines()
    state = Map()
  }

  def generateTaskId() = UUID.randomUUID()

  override def receive: Receive = {
    case Criteria(searchTypes, criteria, requestor) =>
      val id: UUID = generateTaskId()
      state += id -> TaskState(requestor, id, collection.mutable.Map(), searchTypes.size)
      searchTypes.foreach {
        engineType => engines ! EngineCriteria(id, engineType, criteria)
      }
      implicit val ec = context.dispatcher
      context.system.scheduler.scheduleOnce(250 milliseconds, self, id)
    case EngineResult(taskId, engineType, engineResult) =>
      state.get(taskId).foreach { taskState =>
        taskState.numberOfTasks = taskState.numberOfTasks - 1
        taskState.enginesResults(engineType) = engineResult
        if (taskState.numberOfTasks == 0) {
          taskState.requestor ! Results(taskState.enginesResults)
          state -= taskId
        }
      }
    case id: UUID =>
      state.get(id).foreach { taskState =>
        taskState.requestor ! Results(taskState.enginesResults)
        state -= id

      }
  }
}
