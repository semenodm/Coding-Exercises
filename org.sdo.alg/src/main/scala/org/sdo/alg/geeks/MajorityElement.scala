package org.sdo.alg.geeks

/**
  * Created by dsemenov
  * Date: 12/30/15.
  */

trait MajorityElement[E] {
  private[this] def findCandidate(elements: List[E]): Option[E] = elements match {
    case head :: tail =>
      val candidate = tail.foldLeft((head, 1)) { (context, current) =>
        context match {
          case (prev, count) if prev != current =>
            if (count - 1 == 0) (current, 1) else (prev, count - 1)
          case (prev, count) => (prev, count + 1)
        }
      }
      Some(candidate._1)
    case _ => None
  }

  def findMajorityElement(elements: List[E]): Option[E] = {
    for (
      candidate <- findCandidate(elements)
      if elements.count(_ == candidate) >= elements.size / 2
    ) yield candidate
  }
}
