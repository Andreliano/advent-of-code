package org.example;

public class Main {
    public static void main(String[] args) {
        Solver solver = new Solver();
        solver.readAllInformationFromFile("2023/day05/src/main/resources/input");
        System.out.println(solver.getMinLocationValue());
    }
}