package org.example;

import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Solver solver = new Solver();
        List<List<Character>> characters = solver.getAllCharactersFromFile("2023/day03/src/main/resources/input");
        Map<AsteriskCoordinate, List<Integer>> allPartNumbers = solver.getAllPartNumbers(characters);
        System.out.println(solver.getSumOfAllNumbersThatHaveAdjacentCharacters(characters));
        System.out.println(solver.getSumOfAllTheGearRatios(allPartNumbers));
    }
}