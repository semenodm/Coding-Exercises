package org.sdo.algorythms.hanoi;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: dsemenov
 * Date: 2/19/13
 * Time: 8:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class Hanoi {
    static List<List<Deque<Layer>>> visited = new ArrayList<>();

    public static Deque<List<Deque<Layer>>> runAlgo(List<Deque<Layer>> towers) {
        if (isEndOfGame(towers)) {
            return new LinkedList<>();
        }
        List<List<Deque<Layer>>> futureMoves = calcPossibleMoves(towers);
        visited.add(towers);
        Map<Integer, Deque<List<Deque<Layer>>>> temp = new HashMap<>();
        for (List<Deque<Layer>> move : futureMoves) {
            Deque<List<Deque<Layer>>> sequenceOfMoves = runAlgo(move);
            if (sequenceOfMoves != null) {
                sequenceOfMoves.push(move);
                temp.put(sequenceOfMoves.size(), sequenceOfMoves);
            }
        }

        Integer min = Integer.MAX_VALUE;
        for (Integer i : temp.keySet()) {
            if (i < min) min = i;
        }
        return temp.get(min);
    }

    public static List<List<Deque<Layer>>> calcPossibleMoves(List<Deque<Layer>> towers) {

        List<List<Deque<Layer>>> result = new ArrayList<>();
        if (visited.contains(towers)) {
            return result;
        }
        for (int i = 0; i < towers.size(); i++) {
            Deque<Layer> deck = towers.get(i);
            if (!deck.isEmpty()) {
                Layer moveCandidate = deck.peek();
                for (int j = 0; j < towers.size(); j++) {
                    if (i != j && /*!(deck.size() == 1 && towers.get(j).size() == 0) &&*/ canBePlaced(moveCandidate, towers.get(j))) {
                        List<Deque<Layer>> possibleMove = copy(towers);
                        possibleMove.get(j).push(possibleMove.get(i).poll());
                        result.add(possibleMove);
                    }
                }
            }
        }
        System.out.println("possible moves from " + towers + " are: " + result);
        return result;
    }

    private static List<Deque<Layer>> copy(List<Deque<Layer>> towers) {
        List<Deque<Layer>> result = new ArrayList<>();

        for (Deque<Layer> deck : towers) {
            LinkedList<Layer> copyDeck = new LinkedList<>();
            copyDeck.addAll(deck);
            result.add(copyDeck);
        }
        return result;
    }

    public static boolean canBePlaced(Layer moveCandidate, Deque<Layer> layers) {
        if (layers.isEmpty()) {
            return true;
        }
        Layer deckTopBlock = layers.peek();
        if (Layer.LARGE == deckTopBlock) {
            return true;
        }
        if (Layer.MEDIUM == deckTopBlock) {
            if (Layer.LARGE == moveCandidate) {
                return false;
            } else if (Layer.SMALL == moveCandidate) {
                return true;
            } else
                throw new IllegalStateException("cannot be so that we place " + moveCandidate + " on " + deckTopBlock);
        }
        if (Layer.SMALL == deckTopBlock) {
            return false;
        }

        throw new IllegalStateException("cannot be so that we place " + moveCandidate + " on " + deckTopBlock);
    }

    public static boolean isEndOfGame(List<Deque<Layer>> towers) {
        if (!towers.get(0).isEmpty()) {
            return false;
        }
        if (!towers.get(1).isEmpty()) {
            return false;
        }

        Deque<Layer> lastDeck = towers.get(2);
        if (lastDeck.size() != 3) {
            return false;
        }
        Iterator<Layer> iterator = lastDeck.iterator();
        if (iterator.next() != Layer.SMALL) {
            return false;
        }
        if (iterator.next() != Layer.MEDIUM) {
            return false;
        }
        if (iterator.next() != Layer.LARGE) {
            return false;
        }

        return true;
    }
}
