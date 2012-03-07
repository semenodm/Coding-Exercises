import org.sdo.algorythms.sqrtsecomposition.Algorithm
import org.sdo.algorythms.sqrtsecomposition.SqrtDecompositionSum

description "This story is about sqrt optimisation algorithm"

narrative "this shows sqrt optimisation", {
	as a "java developer"
	i want "to know how sqrt optimisation works"
	so that "that I can pass google interview"
}

before "init input and expected result",{
}

where "complete scenarios data",{
	input = [[5, 10, -3, 17, 12, 1, -2, 13, -12], [5, 8, 13, 5, 21, 6, 3, 7, -2, 4, 8, 12]]
	leftIndex = [2,3]
	rightIndex = [5,10]
	expectedSumm = [27,51]
}

scenario "find summ within two indexes #leftIndex and #rightIndex of the array #input",{
	given "An Sqrt algorithm implementation",{
		alg = new SqrtDecompositionSum(input.toArray(new int[0]))
	}

	when "calc sum between two indexes", {
		actualSum = alg.calcSummBetween(leftIndex, rightIndex)
	}

	then "summ should be equal expected #expectedSumm", {
		actualSum.shouldBe expectedSumm
	}
}