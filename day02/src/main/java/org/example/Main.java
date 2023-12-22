package org.example;

import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Solver solver = new Solver();
        Map<Integer, List<List<CubeInformation>>> games = solver.getAllLinesFromFile("day02/src/main/resources/input");
        System.out.println(solver.getSumOfAllPossibleGamesIdsCorrespondingWithExistingCubes(12,
                13, 14,
                games));
    }
}