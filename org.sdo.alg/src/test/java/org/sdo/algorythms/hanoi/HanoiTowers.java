package org.sdo.algorythms.hanoi;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.sdo.algorythms.hanoi.Layer.*;

/**
 * Created with IntelliJ IDEA.
 * User: dsemenov
 * Date: 2/19/13
 * Time: 8:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class HanoiTowers {

    @Test
    public void checkHanoiAlgorithm() {

        List<Deque<Layer>> input = getInitialState();

        Deque<List<Deque<Layer>>> path = Hanoi.runAlgo(input);
        System.out.println(path);
        List<Deque<Layer>> towers = path.peekLast();

        assertThat("algorithm implemented incorrect", towers.get(0).isEmpty(), is(true));
        assertThat("algorithm implemented incorrect", towers.get(1).isEmpty(), is(true));
        assertThat("algorithm implemented incorrect", towers.get(2).size(), is(3));
        assertThat("top should be smallest", towers.get(2).pop(), is(SMALL));
        assertThat("top should be medium", towers.get(2).pop(), is(MEDIUM));
        assertThat("top should be large", towers.get(2).pop(), is(LARGE));
    }

    private List<Deque<Layer>> getInitialState() {
        List<Deque<Layer>> input = new ArrayList<>();

        LinkedList<Layer> deck = new LinkedList<>();
        deck.push(LARGE);
        deck.push(MEDIUM);
        deck.push(SMALL);
        input.add(deck);
        input.add(new LinkedList<Layer>());
        input.add(new LinkedList<Layer>());
        return input;
    }

    @Test
    public void checkGameOverMethodWithTowerOnLastDeck() {
        List<Deque<Layer>> input = new ArrayList<>();

        LinkedList<Layer> deck = new LinkedList<>();
        input.add(new LinkedList<Layer>());
        input.add(new LinkedList<Layer>());
        deck.push(LARGE);
        deck.push(MEDIUM);
        deck.push(SMALL);
        input.add(deck);

        assertThat("this is not game over", Hanoi.isEndOfGame(input), is(true));
    }

    @Test
    public void checkGameOverMethodWithTowerOnFirstDeck() {
        List<Deque<Layer>> input = getInitialState();

        assertThat("this is not game over", Hanoi.isEndOfGame(input), is(false));
    }

    @Test
    public void checkGameOverMethodWithIntermediateState() {
        List<Deque<Layer>> input = new ArrayList<>();
        LinkedList<Layer> deck = new LinkedList<>();
        deck.push(LARGE);
        deck.push(SMALL);
        input.add(deck);
        deck = new LinkedList<>();
        deck.push(MEDIUM);
        input.add(deck);
        input.add(new LinkedList<Layer>());

        assertThat("this is not game over", Hanoi.isEndOfGame(input), is(false));
    }

    @Test
    public void testFutureMovesFromInitialState() {
        Hanoi.visited.clear();
        List<Deque<Layer>> initialState = getInitialState();

        List<List<Deque<Layer>>> possibleMoves = Hanoi.calcPossibleMoves(initialState);

        assertThat("possible moves should be exactly two", possibleMoves.size(), is(2));

        //-------------------------------------------------------------------------------------
        List<Deque<Layer>> firstVariant = possibleMoves.get(0);

        assertThat("medium size block should be on top", firstVariant.get(0).pop(), is(MEDIUM));
        assertThat("Large size block should be on bottom", firstVariant.get(0).pop(), is(LARGE));

        assertThat("After moving smallest block to middle size of middle deck should be 1", firstVariant.get(1).size(), is(1));
        assertThat("Small block should be moved on middle deck", firstVariant.get(1).pop(), is(SMALL));
        assertThat("last deck should be  empty", firstVariant.get(2).isEmpty(), is(true));

        //-------------------------------------------------------------------------------------
        List<Deque<Layer>> secondVariant = possibleMoves.get(1);

        assertThat("medium size block should be on top", secondVariant.get(0).pop(), is(MEDIUM));
        assertThat("Large size block should be on bottom", secondVariant.get(0).pop(), is(LARGE));

        assertThat("middle deck should be  empty", secondVariant.get(1).isEmpty(), is(true));

        assertThat("After moving smallest block to last, size of last deck should be 1", secondVariant.get(2).size(), is(1));
        assertThat("Small block should be moved on last deck", secondVariant.get(2).pop(), is(SMALL));

    }

    @Test
    public void testOrderRules() {
        assertThat("We should allow to place Large block in empty Deck", Hanoi.canBePlaced(LARGE, new LinkedList<Layer>()), is(true));
        assertThat("We should allow to place medium block in empty Deck", Hanoi.canBePlaced(MEDIUM, new LinkedList<Layer>()), is(true));
        assertThat("We should allow to place Small block in empty Deck", Hanoi.canBePlaced(SMALL, new LinkedList<Layer>()), is(true));
        LinkedList<Layer> deck = new LinkedList<>();
        deck.push(SMALL);
        assertThat("We shouldn't allow to place LARGE on SMALL block", Hanoi.canBePlaced(LARGE, deck), is(false));
        deck = new LinkedList<>();
        deck.push(SMALL);
        assertThat("We shouldn't allow to place MEDIUM on SMALL block", Hanoi.canBePlaced(MEDIUM, deck), is(false));

        deck = new LinkedList<>();
        deck.push(MEDIUM);
        assertThat("We shouldn't allow to place LARGE on MEDIUM block", Hanoi.canBePlaced(LARGE, deck), is(false));

        deck = new LinkedList<>();
        deck.push(MEDIUM);
        assertThat("We should allow to place SMALL on MEDIUM block", Hanoi.canBePlaced(SMALL, deck), is(true));

        deck = new LinkedList<>();
        deck.push(LARGE);
        assertThat("We should allow to place MEDIUM on LARGE block", Hanoi.canBePlaced(MEDIUM, deck), is(true));

        deck = new LinkedList<>();
        deck.push(LARGE);
        assertThat("We should allow to place SMALL on LARGE block", Hanoi.canBePlaced(SMALL, deck), is(true));

    }
}
