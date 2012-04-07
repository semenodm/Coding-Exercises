package org.sdo.algorythms.graph

class Graph {
	def graphContainer = [:]
	def nodeSize = 0
	
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
}
