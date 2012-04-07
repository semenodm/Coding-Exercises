Feature: Sqrt Sums Algorithm Feature
        In order to ensure that my algorithm works
        As a Developer
        I want to run a quick Cucumber test

        Scenario Outline: Sqrt Sums Alg Scenario
                Given The input array <input array>
                When The calc sum between <Left index>, <Right index>
                Then The summ is <output summ>.
                
        Examples:
        |input array							|Left index	|Right index|output summ|
        |5, 10, -3, 17, 12, 1, -2, 13, -12		|2			|5			|27			|
        |5, 8, 13, 5, 21, 6, 3, 7, -2, 4, 8, 12	|3			|10			|52			|