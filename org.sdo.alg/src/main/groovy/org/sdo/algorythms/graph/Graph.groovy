package org.sdo.algorythms.graph

import java.io.File
import java.util.HashMap

class Graph {
	def graphContainer = [:]
	def nodeSize = 0
	def vertices = [:] as HashMap
	def reversedVertices = [:] as HashMap
	def leaderSize
	def edges = []
	
	public Graph(List adjacencyList){
		adjacencyList.each { vertexConnections ->
			def vertexLabel = vertexConnections.head()
			def connectedVertices = vertexConnections.tail()
			vertices[vertexLabel] = connectedVertices.collect {it[0]} 
			connectedVertices.each{edges << [from : vertexLabel, to : it[0], weight : it[1]]}
		}
	}
			
	def runDijkstra(def from, def to){
		def X = [:]
		X[from] = 0
		runDijkstra(from, to, X)		
	}
	
	def runDijkstra(def from, def to, def accum){
		if(to == from) return accum[from]
		
		def edges = extractCutEdges(accum)
		if(edges.isEmpty()) return -1
		
		def e = edges.min {accum[it.from] + it.weight}
		accum[e.to] = accum[e.from] + e.weight
		
		runDijkstra(e.to, to, accum)
	}
	
	def getVerticeLength(){
		vertices.size()
	}
	
	def getEdgeLength(){
		edges.size()
	}
	
	def vertixReferes(def vertex){		 
		vertices[vertex]
	}
	
	def containsEdges(def ... edgesToExamine){		
		edgesToExamine.every {edges.contains(it)}
	}
	
	def extractCutEdges(def X){		
		edges.findAll { e -> X.containsKey(e.from) && !X.containsKey(e.to)}
	}
	
	void parsGraph(def pathToFile){
		new File(pathToFile).eachLine { line ->
			++nodeSize
			line = line.trim()
			def vertices = line.split(/\s+/).collect()
			vertices.eachWithIndex { vertex, index ->
				if(index > 0){
					def pair = new Pair(vertices[0] as Integer, vertex as Integer)
					if(!graphContainer.containsKey(pair)){
						graphContainer[pair] = 1
					}else{
						graphContainer[pair]++
					}
				}
			}
		}
	}

	void parseDirectGraph(def pathToFile){
		new File(pathToFile).eachLine { line ->
			line = line.trim()
			def nodes = line.split(/\s+/).collect()
			def parentVertex
			nodes.eachWithIndex { v, index ->
				def vertex = v as Integer
				//println "Reading vertex with label $vertex"
				Vertex currentVertex = vertices.get(vertex)
				if(!currentVertex){
					currentVertex = new Vertex(vertexLabel: vertex)
					vertices.put(vertex, currentVertex)
					nodeSize++
				}

				if(index == 0){
					//println "Vertex $currentVertex is parent"
					parentVertex = currentVertex
				}else{
					//println "Vertex $currentVertex is child for $parentVertex vertex"
					parentVertex.addChildVertex(currentVertex)
					def pair = new Pair(parentVertex.vertexLabel, currentVertex.vertexLabel)
					if(!graphContainer.containsKey(pair)){
						graphContainer[pair] = 1
					}else{
						graphContainer[pair]++
					}
				}
			}
		}
	}


	void reverseVertex(Vertex vertex){
		println "reverse $vertex"
		def reversedChild = reversedVertices.get(vertex.finishedTimes)
		if(!reversedChild){
			reversedChild = new Vertex(vertexLabel: vertex.vertexLabel, finishedTimes: vertex.finishedTimes)
			reversedVertices.put(reversedChild.finishedTimes, reversedChild)
		}
		vertex.childVertices.each { v->
			def parent = reversedVertices.get(v.finishedTimes)
			if(!parent){
				parent = new Vertex(vertexLabel: v.vertexLabel, finishedTimes: v.finishedTimes)
				reversedVertices.put(v.finishedTimes, parent)
			}
			parent.addChildVertex(reversedChild)
		}
	}

	void generateReverseGraph(){
		vertices.values().each { v-> reverseVertex(v) }
	}

	def walkGraph = { seedLabel, graphEventHandler ->
		def seed = vertices.get(seedLabel)
		println "visit vertex $seed"
		if(seed.visited){
			return
		}
		graphEventHandler.handleVertexVisited(seed)
		if(seed.hasChildren()){
			seed.childVertices.each { childVertex ->
				walkGraph(childVertex.vertexLabel, graphEventHandler)
			}
		}
		graphEventHandler.handleChildrenDone(seed)
	}

	def walkReverseGraph = { seedLabel, graphEventHandler ->
		def seed = reversedVertices.get(seedLabel)
		println "visit vertex $seed"
		if(seed.visited){
			return
		}
		leaderSize++
		graphEventHandler.handleVertexVisited(seed)

		seed.childVertices.each { childVertex ->
			walkReverseGraph(childVertex.finishedTimes, graphEventHandler)
		}
	}
	
	String toString(){
		vertices1.each {
			println it.key.class
			}
		"""${vertices1.dump()}
		${edges.dump()}"""
	}
}
