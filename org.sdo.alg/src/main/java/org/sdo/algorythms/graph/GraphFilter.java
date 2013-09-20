package org.sdo.algorythms.graph;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: dsemenov
 * Date: 9/19/13
 * Time: 10:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class GraphFilter {
    private Node graphToFilter;
    private Predicate<Node> filterPredicate;

    public GraphFilter filter(Node root) {
        this.graphToFilter = root;
        return this;
    }

    public GraphFilter with(Predicate<Node> predicate) {
        this.filterPredicate = predicate;
        return this;
    }

    public Node doIt() {
        return dfs(graphToFilter);
    }

    private Node dfs(Node incoming){
        if(filterPredicate.apply(incoming)){
            return new Node(incoming.getCode(), incoming.getValue());
        }
        List<Node> nodes = new ArrayList<Node>();
        for(Node node : incoming.getChildren()){
             Node childNode = dfs(node);
            if(childNode != null)
                nodes.add(childNode);
        }
        if(!nodes.isEmpty()){
            Node newNode = new Node(incoming.getCode(), 0);
            newNode.addAllChildren(nodes);
            return newNode;
        }
        return null;
    }
}
