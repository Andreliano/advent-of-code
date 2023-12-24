package org.example;

import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Solver solver = new Solver();
        Map<Integer, String> cards = solver.getAllCardsFromFile("2023/day04/src/main/resources/input");
        System.out.println(solver.getTotalPointsFromAllCards(cards));
    }
}