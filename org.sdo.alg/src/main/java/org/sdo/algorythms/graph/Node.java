package org.sdo.algorythms.graph;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: dsemenov
 * Date: 9/19/13
 * Time: 9:56 PM
 * To change this template use File | Settings | File Templates.
 */
public class Node {
    private int value;
    private List<Node> children = new ArrayList<Node>();
    private String code;

    public Node(int value) {
        this.value = value;
    }

    public Node(String code, int value) {
        this(value);
        this.code = code;
    }

    public void addChild(Node node) {
        this.children.add(node);
    }

    public int getValue() {
        return value;
    }

    public String getCode() {
        return code;
    }

    public boolean isLeaf() {
        return this.children.isEmpty();
    }


    public List<Node> getChildren() {
        return children;
    }

    public void addAllChildren(List<Node> nodes) {
        for(Node node : nodes){
            value+=node.value;
            children.add(node);
        }
    }
}
