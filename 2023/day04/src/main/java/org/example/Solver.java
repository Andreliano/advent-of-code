package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Solver {

    public Map<Integer, String> getAllCardsFromFile(String filename) {
        Map<Integer, String> cards = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line = reader.readLine();
            String[] gameParts;
            String[] gameIndex;
            int i;
            while (line != null) {
                i = 1;
                gameParts = line.split(":");
                gameIndex = gameParts[0].split(" ");

                while (gameIndex[i].equals("")) {
                    i++;
                }
                cards.put(Integer.parseInt(gameIndex[i].trim()), gameParts[1].trim());
                line = reader.readLine();
            }
        }
        catch (IOException ex) {
            return new HashMap<>();
        }

        return cards;
    }

    public double getTotalPointsFromAllCards(Map<Integer, String> cards) {
        double pointsPerGame;
        double totalPoints = 0;
        Map<Integer, Boolean> winningNumbersMap;
        String[] numbers;
        String[] winningNumbers;
        String[] receivedNumbers;
        int receivedNumberInt;
        int exponent;
        for (String card : cards.values()) {
            pointsPerGame = 0;
            exponent = 0;
            numbers = card.split("\\|");
            winningNumbers = numbers[0].split(" ");
            receivedNumbers = numbers[1].split(" ");
            winningNumbersMap = new HashMap<>();
            for (String winningNumber : winningNumbers) {
                if(!winningNumber.isEmpty()) {
                    winningNumbersMap.put(Integer.parseInt(winningNumber.trim()), true);
                }
            }
            for (String receivedNumber : receivedNumbers) {
                if (!receivedNumber.isEmpty()) {
                    receivedNumberInt = Integer.parseInt(receivedNumber);
                    if (Boolean.TRUE.equals(winningNumbersMap.get(receivedNumberInt))) {
                        pointsPerGame = Math.pow(2, exponent);
                        exponent++;
                    }
                }
            }
            totalPoints += pointsPerGame;
        }

        return totalPoints;
    }

}
