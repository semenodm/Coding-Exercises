package org.sdo.algorythms.graph

class DepthFirstSearch {
	Graph graph	

	int globalT = 1
	def finishedTimesVertices = [:] as HashMap

	void search(def seedLabel, def walker){
		walker(seedLabel, this)
	}

	void handleVertexVisited(Vertex vertex){
		vertex.visited = true
	}

	void handleChildrenDone(Vertex vertex){
		vertex.finishedTimes=globalT++		
	}

	void firstDFSLoop(){
		graph.nodeSize.times{
			println "starting DFS on ${graph.nodeSize - it}"
			search(graph.nodeSize - it, graph.walkGraph)
		}
		println "-------------------------------------reversing graph-------------------------------------------"
		graph.generateReverseGraph()
		println "-------------------------------------reversing graph finished----------------------------------"
	}
	
	def secondDFSLoop(){
		def leaders = [:] as TreeMap
		graph.nodeSize.times{
			println "starting reverse DFS on ${graph.nodeSize - it}"
			def label = graph.nodeSize - it
			def vertex = graph.reversedVertices.get(label)
			if(!vertex.visited){
				println "$vertex is leader"
				graph.leaderSize = 0
				search(label, graph.walkReverseGraph)
				leaders.put(graph.leaderSize,label)				
			}
		}
		leaders
	}
	
	def runCSCAlg(){
		firstDFSLoop()
		secondDFSLoop()
	}
}
