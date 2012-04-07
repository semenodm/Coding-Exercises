package org.sdo.algorythms.graph

public class MinCut {
	Graph graph

	public MinCut(Graph graph) {
		super();
		this.graph = graph;
	}

	public void breakLink(def pair) {
		def numberOfLinks = graph.graphContainer.get(pair)
		def nodeToRemove = Math.max(pair.one, pair.two)
		def nodeToRemain = Math.min(pair.one, pair.two)
		def indexToRemain = nodeToRemain == pair.one ? 0 : 1

		def toSubstitute = [:]
		graph.graphContainer.each {entry ->
			def key = entry.key
			def value = entry.value
			if(key.one == nodeToRemove){
				toSubstitute[key] = new Pair(nodeToRemain, key.two)
			}
			if(key.two == nodeToRemove){
				toSubstitute[key] = new Pair(key.one, nodeToRemain)
			}
		}
		toSubstitute.each {entry ->
			def v = graph.graphContainer.remove(entry.key)
			def newValue = graph.graphContainer.get(entry.value)
			if(newValue){
				graph.graphContainer.remove(entry.value)
				newValue+=v
				graph.graphContainer.put(entry.value, newValue)
			}else{
				graph.graphContainer.put(entry.value, v)
			}
		}

		def toRemove = [
			pair,
			new Pair(pair.two, pair.one)
		]
		graph.graphContainer.each {entry ->
			if(entry.key.one == entry.key.two){
				toRemove << entry.key
			}
		}
		toRemove.each {
			graph.graphContainer.remove(it)
		}
	}

	def calcMinCut(){

		def random = new Random();

		while(graph.graphContainer.size() > 2){
			int index = random.nextInt(graph.graphContainer.size())
			def link = null
			graph.graphContainer.keySet().eachWithIndex {key, i ->
				if(i == index){
					link = key
				}
			}
			println "$link with index $index chosen"
			breakLink(link)
		}
		graph.graphContainer.values().iterator().next()
	}

	static def calcMinCutN(def graphDesc, def numberOfAttempts){


		//def numberOfAttempts = (graph.nodeSize * graph.nodeSize * Math.log(graph.nodeSize)) as Integer

		def results = []

		while(numberOfAttempts--) {

			println('----------------------------------New iteration of min cut algo--------------------------------------------------')
			def graph = new Graph()
			graph.parsGraph(graphDesc)
			def alg = new MinCut(graph)
			results << alg.calcMinCut()
		} 



		println "results are ${results.dump()}"
		Collections.min(results)
	}
}