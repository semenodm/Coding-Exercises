package org.sdo.algorythms.graph;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: dsemenov
 * Date: 9/19/13
 * Time: 9:53 PM
 * To change this template use File | Settings | File Templates.
 */
public class GraphFilterTest {
    @Test
    public void testGraphFiltering() {
        Node root = new Node(100);
        Node node1 = new Node(50);
        root.addChild(node1);
        Node node2 = new Node(25);
        root.addChild(node2);
        Node node3 = new Node(25);
        root.addChild(node3);
        Node node4 = new Node(25);
        root.addChild(node4);
        node1.addChild(new Node("WTC", 10));
        node1.addChild(new Node("MUTC", 20));
        node1.addChild(new Node("WTF", 20));
        node2.addChild(new Node("MUTC", 10));
        node2.addChild(new Node("DTC", 15));
        node3.addChild(new Node("JFDI", 25));
        node4.addChild(new Node("MUTC", 15));
        node4.addChild(new Node("WTC", 7));
        node4.addChild(new Node("DTC", 3));

        GraphFilter filter = new GraphFilter();
        Node rootOfFiltered = filter.filter(root).with(new Predicate<Node>() {

            public boolean apply(Node node) {
                return node.isLeaf() && "DTC".equals(node.getCode());
            }
        }).doIt();
        MatcherAssert.assertThat(rootOfFiltered.getValue(), CoreMatchers.is(30));

    }
}
