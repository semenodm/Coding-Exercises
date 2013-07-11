import scala.collection.immutable.TreeMap

/**
 * Created with IntelliJ IDEA.
 * User: dsemenov
 * Date: 7/3/13
 * Time: 10:40 PM
 * To change this template use File | Settings | File Templates.
 */
def primeProgression(seed: Int, prime: Int): Stream[Int] =
  seed #:: primeProgression(seed + prime, prime)
primeProgression(9, 3) take 5 takeRight 1 foreach println

type Progressions = Set[Stream[Int]]

var map = TreeMap(4 -> Set(primeProgression(4, 2)), 9 -> Set(primeProgression(9, 3)))

primeProgression(4, 2).tail


def applyStream(stream: Stream[Int], map: TreeMap[Int, Progressions]): TreeMap[Int, Progressions] = {
  map.get(stream.head) match {
    case None => map + (stream.head -> Set(stream))
    case Some(progressions) => map - stream.head + (stream.head -> (progressions + stream))
  }
}



applyStream(primeProgression(4, 2).tail, map) - 4


def applyStreams(progressions: Progressions, map: TreeMap[Int, Progressions]): TreeMap[Int, Progressions] = {
  if (progressions.isEmpty)
    map
  else
    applyStream(progressions.head.tail, applyStreams(progressions.tail, map))
}



val s = Set(primeProgression(12, 2), primeProgression(12, 3))

val m = TreeMap(12 -> Set(primeProgression(12, 2), primeProgression(12, 3)), 14 -> Set(primeProgression(14, 8)))


applyStreams(s, m) - 12

def primeStream(prime: Int, map: TreeMap[Int, Progressions]): Stream[Int] = {
  if (!map.isEmpty) {
    (map.head, map.tail) match {
      case (head, tail) =>
        if (prime < head._1) {
          prime #:: primeStream(prime + 1, map + (prime * prime -> Set(primeProgression(prime * prime, prime))))
        } else {
          val progressions = head._2
          primeStream(prime + 1, applyStreams(progressions, map) - head._1)
        }
    }
  } else {
    prime #:: primeStream(prime + 1, map + (prime * prime -> Set(primeProgression(prime * prime, prime))))
  }
}



primeStream(2, TreeMap.empty[Int, Progressions]) take 10001 takeRight 1 foreach println






























































println(TreeMap(3 -> "three", 1 -> "one", 2 -> "two").tail)


//TreeMap.empty[Int, String].head

