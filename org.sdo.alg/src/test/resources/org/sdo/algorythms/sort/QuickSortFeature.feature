Feature: Quick Sort Algorithm Feature
        In order to ensure that my algorithm works
        As a Developer
        I want to run a quick Cucumber test

        Scenario Outline: Quick Sort Algorithm Scenario
                Given The input array to quick sort <input array>
                When sort array with <pivot> pivot
                Then The quick sorted array is <sorted array>.
                
        Examples:
        |input array							|pivot	|sorted array							|
        |5, 10, -3, 17, 12, 1, -2, 13, -12		|MEDIAN	|-12, -3, -2, 1, 5, 10, 12, 13, 17		|
        |5, 8, 13, 5, 21, 6, 3, 7, -2, 4, 8, 12	|MEDIAN	|-2, 3, 4, 5, 5, 6, 7, 8, 8, 12, 13, 21	|
        |3, 8, 2, 5, 1, 4, 7, 6					|FIRST	|1, 2, 3, 4, 5, 6, 7, 8					|
        |3, 8, 1, 8, 2, 5, 4, 1, 4, 7, 2, 8, 6	|LAST	|1, 1, 2, 2, 3, 4, 4, 5, 6, 7, 8, 8, 8	|
        |3, 8, 1, 8, 2, 5, 4, 1, 4, 7, 2, 8, 6	|MEDIAN	|1, 1, 2, 2, 3, 4, 4, 5, 6, 7, 8, 8, 8	|