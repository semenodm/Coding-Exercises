package org.sdo.algorythms.domino;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: dsemenov
 * Date: 2/15/13
 * Time: 9:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class Domino {
    private int right;
    private int left;

    @Override
    public String toString() {
        return "(" + left + "," + right + ")";
    }

    private enum Side {
        LEFT, RIGHT
    }

    public Domino(int one, int two) {
        this.right = two;
        this.left = one;
    }

    public static Deque<Domino> calcLongestChain(List<Domino> inputSetOfDomino) {
        Map<Integer, Deque<Domino>> results = new HashMap<>();
        for (Domino domino : inputSetOfDomino) {
            List<Domino> localDominos = getLocalSetOfDomino(inputSetOfDomino, domino);
            Deque<Domino> dominos = calcLongestChain(localDominos);
            if (dominos.isEmpty()) {
                dominos.push(domino);
            } else if (matches(Side.LEFT, dominos.peekFirst(), domino)) {
                dominos.push(domino);
            } else if (matches(Side.RIGHT, dominos.peekLast(), domino)) {
                dominos.addLast(domino);
            }

            if (localDominos != null) {
                results.put(dominos.size(), dominos);
            }
        }

        int max = Integer.MIN_VALUE;
        for (Integer size : results.keySet()) {
            if (size > max) max = size;
        }
        return max != Integer.MIN_VALUE ? results.get(max) : new LinkedList<Domino>();
    }

    private static List<Domino> getLocalSetOfDomino(List<Domino> inputSetOfDomino, Domino domino) {
        ArrayList<Domino> localDominos = new ArrayList<>(inputSetOfDomino);
        localDominos.remove(domino);
        return localDominos;
    }

    private static boolean matches(Side side, Domino first, Domino second) {
        if (side == Side.LEFT) {
            if (first.left == second.left) {
                second.swap();
                return true;
            } else if (first.left == second.right) {
                return true;
            }
            return false;
        } else if (side == Side.RIGHT) {
            if (first.right == second.right) {
                second.swap();
                return true;
            } else if (first.right == second.left) {
                return true;
            }
            return false;
        } else {
            return false;
        }
    }

    private void swap() {
        int temp = left;
        left = right;
        right = temp;
    }
}
