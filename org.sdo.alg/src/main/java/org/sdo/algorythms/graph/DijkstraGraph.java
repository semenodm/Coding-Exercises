package org.sdo.algorythms.graph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DijkstraGraph {
	
	private Set<String>  vertices  = new HashSet<String>();
	private Set<Edge> edges = new HashSet<Edge>();
	

	public DijkstraGraph(Edge[] adjList) {
		for(Edge e : adjList){
			vertices.add(e.getFrom());
			vertices.add(e.getTo());
			edges.add(e);
		}
	}

	public Integer runDijkstra(String c, String d) {
		Map<String, Integer> exploredVertices = new HashMap<String, Integer>();
		exploredVertices.put(c, 0);
		while(!exploredVertices.containsKey(d)) {
			Edge nextCandidate =  runGreedy(exploredVertices);
			if (nextCandidate == null) {
				break;
			}
			exploredVertices.put(nextCandidate.getTo(), exploredVertices.get(nextCandidate.getFrom())+nextCandidate.getWeight());
		}
		return exploredVertices.get(d);
	}

	public Set<String> getVertixes() {
		return vertices;
	}

	public Set<Edge> getEdges() {
		return edges;
	}

	public Set<Edge> getEdgesFromVertex(String vertex) {
		Set<Edge> result = new HashSet<Edge>();
		for(Edge e : edges) {
			if(e.getFrom().equals(vertex)) result.add(e);
		}
		return result;
		
	}

	public Set<Edge> getOutgoingEdsgesFrom(Set<String> x) {
		Set<Edge> outgoingEdges = new HashSet<Edge>();
		for (String vertexFrom : x) {
			for (Edge edge : getEdgesFromVertex(vertexFrom)) {
				if(!x.contains(edge.getTo())){
					outgoingEdges.add(edge);
				}				
			}
		}
		return outgoingEdges;
	}

	public Edge runGreedy(Map<String, Integer> x) {
		Set<Edge> edges = getOutgoingEdsgesFrom(x.keySet());
		Integer min = Integer.MAX_VALUE;
		Edge minEdge = null;
		for(Edge edge: edges){
			Integer cost = x.get(edge.getFrom()) + edge.getWeight();
			if(min > cost){
				minEdge = edge;
				min = cost;
			}
		}
		return minEdge;
	}

}
