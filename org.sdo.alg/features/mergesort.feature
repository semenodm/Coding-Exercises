Feature: Merge Sort Algorithm Feature
        In order to ensure that my algorithm works
        As a Developer
        I want to run a quick Cuke4Duke test

        Scenario Outline: Merge Sort Algorithm Scenario
                Given The input array to sort <input array>
                When sort array
                Then The sorted array is <sorted array>.
                
        Examples:
        |input array							|sorted array	|
        |5, 10, -3, 17, 12, 1, -2, 13, -12		|-12, -3, -2, 1, 5, 10, 12, 13, 17		|
        |5, 8, 13, 5, 21, 6, 3, 7, -2, 4, 8, 12	|-2, 3, 4, 5, 5, 6, 7, 8, 8, 12, 13, 21	|
        |2, 1	|1,2	|
        |2|2	|