package org.example;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        Solver solver = new Solver();
        List<List<Character>> characters = solver.getAllCharactersFromFile("2023/day03/src/main/resources/input");
        System.out.println(solver.getSumOfAllNumbersThatHaveAdjacentCharacters(characters));
    }
}