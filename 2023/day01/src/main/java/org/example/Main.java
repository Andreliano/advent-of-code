package org.example;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        Solver solver = new Solver();
        List<String> lines = solver.getAllLinesFromFile("2023/day01/src/main/resources/input");
        System.out.println(solver.getSumOfCalibrationValues(lines));
    }
}