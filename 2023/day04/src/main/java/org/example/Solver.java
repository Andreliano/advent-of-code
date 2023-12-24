package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
        } catch (IOException ex) {
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
                if (!winningNumber.isEmpty()) {
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

    public Map<Integer, List<Integer>> buildFirstCopiesMap(Map<Integer, String> cards) {
        Map<Integer, Boolean> winningNumbersMap;
        String[] numbers;
        String[] winningNumbers;
        String[] receivedNumbers;
        int receivedNumberInt;
        int copyId;
        List<Integer> firstCardsCopies;
        Map<Integer, List<Integer>> firstCopiesMap = new HashMap<>();
        for (Map.Entry<Integer, String> card : cards.entrySet()) {
            numbers = card.getValue().split("\\|");
            winningNumbers = numbers[0].split(" ");
            receivedNumbers = numbers[1].split(" ");
            winningNumbersMap = new HashMap<>();
            firstCardsCopies = new ArrayList<>();
            for (String winningNumber : winningNumbers) {
                if (!winningNumber.isEmpty()) {
                    winningNumbersMap.put(Integer.parseInt(winningNumber.trim()), true);
                }
            }
            copyId = card.getKey();
            for (String receivedNumber : receivedNumbers) {
                if (!receivedNumber.isEmpty()) {
                    receivedNumberInt = Integer.parseInt(receivedNumber);
                    if (Boolean.TRUE.equals(winningNumbersMap.get(receivedNumberInt))) {
                        copyId++;
                        firstCardsCopies.add(copyId);
                    }
                }
            }
            firstCopiesMap.put(card.getKey(), firstCardsCopies);
        }

        return firstCopiesMap;
    }

    public int getSumOfAllCardsAppearances(Map<Integer, List<Integer>> firstCopies,
                                           Map<Integer, String> cards) {
        List<Integer> cardsIds = new ArrayList<>(cards.keySet());
        Map<Integer, Integer> cardsAppearances = new HashMap<>();
        int sumOfAllCardsAppearances = 0;
        for (Integer cardId : cardsIds) {
            cardsAppearances.put(cardId, 0);
        }

        int i;
        for(i = 0; i < cardsIds.size(); i++) {
            cardsAppearances.put(cardsIds.get(i), cardsAppearances.get(cardsIds.get(i)) + 1);
            cardsIds.addAll(firstCopies.get(cardsIds.get(i)));
        }

        for(Integer cardAppearances : cardsAppearances.values()) {
            sumOfAllCardsAppearances += cardAppearances;
        }

        return sumOfAllCardsAppearances;
    }

}
