package org.sdo.algorythms.domino;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;


/**
 * Created with IntelliJ IDEA.
 * User: dsemenov
 * Date: 2/15/13
 * Time: 9:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class DominoTest {
    @Test
    public void test_whether_i_can_find_longest_chain() {
        List<Domino> inputSetOfDomino = new ArrayList<>();
        inputSetOfDomino.add(new Domino(1, 5));
        inputSetOfDomino.add(new Domino(3, 4));
        inputSetOfDomino.add(new Domino(5, 3));
        inputSetOfDomino.add(new Domino(6, 2));
        inputSetOfDomino.add(new Domino(5, 5));
        System.out.println(Domino.calcLongestChain(inputSetOfDomino));
        assertThat(Domino.calcLongestChain(inputSetOfDomino).size(), is(4));
    }


}
