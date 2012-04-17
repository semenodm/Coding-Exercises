package org.sdo.algorythms.graph

class Vertex {
	
	def vertexLabel
	def childVertices = []
	boolean visited = false 
	def finishedTimes
	
	void addChildVertex(Vertex vertex){
		childVertices << vertex
	}

	boolean hasChildren(){
		return !childVertices.isEmpty()
	}
	
	boolean hasUnvisitedChildren(){
		boolean result = true
		childVertices.each {
			result = result && it.visited
		}
		!result
	}
	
	@Override
	public String toString() {
		return "Vertex [vertexLabel=" + vertexLabel + ", visited=" + visited + ", finishedTimes=" + finishedTimes + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((vertexLabel == null) ? 0 : vertexLabel.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this.is(obj))
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vertex other = (Vertex) obj;
		if (vertexLabel == null) {
			if (other.vertexLabel != null)
				return false;
		} else if (!vertexLabel.equals(other.vertexLabel))
			return false;
		return true;
	}
}
