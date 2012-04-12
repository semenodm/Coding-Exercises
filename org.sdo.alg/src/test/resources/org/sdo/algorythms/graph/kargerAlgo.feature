Feature: A Karger's min cut algorithm  
  Scenario Outline: Karger's algo
    Given A undirect <graph>
    When run Karger's algo
    Then min cut should be <min cut>
    
    Examples: set of scenarios data
      |  graph 										| min cut	|
      |  ./src/test/resources/simpleData.txt		|2		 	|
      |  ./src/test/resources/kargerAdj.txt			|3			|
      |  ./src/test/resources/test2.txt				|2			|
      |./src/test/resources/test1.txt				|3			|
      |./src/test/resources/test3.txt				|2			|
      |./src/test/resources/test4.txt				|4			|
      |./src/test/resources/test5.txt				|1			|
      |./src/test/resources/test6.txt				|3			|
      |./src/test/resources/test7.txt				|1			|