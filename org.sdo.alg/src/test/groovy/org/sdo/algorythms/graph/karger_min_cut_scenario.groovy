package org.sdo.algorythms.graph

import static groovy.util.GroovyTestCase.assertEquals
import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

this.metaClass.mixin(cucumber.runtime.groovy.Hooks)
this.metaClass.mixin(cucumber.runtime.groovy.EN)

def numberOfAttempts
def minCut
def graphPath
Given(~/^A undirect ([\.\w\/]+)$/) { String graph ->
	def verticesNum = 0
	graphPath = graph
	new File(graphPath).eachLine{verticesNum++}
	numberOfAttempts = (verticesNum * verticesNum * Math.log(verticesNum)) as Integer
}

When(~'^run Karger\'s algo$'){
	->
	minCut = MinCut.calcMinCutN(graphPath, numberOfAttempts)
}

Then(~/^min cut should be (\d+)$/) { int expectedMinCut ->
	assertThat(minCut, is(expectedMinCut))
}
