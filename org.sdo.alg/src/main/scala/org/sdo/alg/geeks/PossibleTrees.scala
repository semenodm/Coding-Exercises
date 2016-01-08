package org.sdo.alg.geeks

/**
  * Created by dsemenov
  * Date: 1/5/16.
  */
trait PossibleTrees[E] {
  def findPossiblePreOrderTreesFor(inOrderTree: List[E]): List[List[E]] = inOrderTree match {
    case Nil => Nil
    case head :: Nil => List(List(head))
    case l =>
      (l.indices toList) flatMap { n =>
        val nth = l(n)
        val left = inOrderTree.take(n)
        val right = inOrderTree.takeRight(l.length - n - 1)
        val lef = findPossiblePreOrderTreesFor(left)
        val rig = findPossiblePreOrderTreesFor(right)
        if (lef.isEmpty) {
          rig.map(nth :: _)
        } else if (rig.isEmpty) {
          lef.map(nth :: _)
        } else {
          for (
            l <- lef;
            r <- rig
          ) yield nth :: l ::: r
        }
      }
  }
}
