import spock.lang.Specification

import static org.junit.Assert.*;

import org.sdo.algorythms.sqrtsecomposition.Algorithm
import org.sdo.algorythms.sqrtsecomposition.SqrtDecompositionSum

import spock.lang.Specification;


class SqrtSumAlgSpecTest extends Specification {
	Algorithm alg
	def "Sqrt sums scenarios"(){
		when:
		alg = new SqrtDecompositionSum(input.toArray(new int[0]))
		then:
		outputSumm == alg.calcSummBetween(leftIndex, rightIndex)
		where:
		input 										| leftIndex | rightIndex 	| outputSumm
		[5, 10, -3, 17, 12, 1, -2, 13, -12]			|2			|5				|27
		[5, 8, 13, 5, 21, 6, 3, 7, -2, 4, 8, 12]	|3			|10				|52
	}

}
