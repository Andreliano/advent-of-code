package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Solver {

    private static final String SYMBOL_REGEX = "^[^0-9.]$";

    private static final String ASTERISK_REGEX = "^\\*$";

    public List<List<Character>> getAllCharactersFromFile(String filename) {
        List<List<Character>> allCharacters = new ArrayList<>();
        List<Character> charactersPerLine;
        int i;
        int lineLength;

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line = reader.readLine();
            while (line != null) {
                lineLength = line.length();
                charactersPerLine = new ArrayList<>();

                for (i = 0; i < lineLength; i++) {
                    charactersPerLine.add(line.charAt(i));
                }

                allCharacters.add(charactersPerLine);
                line = reader.readLine();
            }

            return allCharacters;
        } catch (IOException ex) {
            return new ArrayList<>();
        }
    }

    public int getSumOfAllNumbersThatHaveAdjacentCharacters(List<List<Character>> characters) {
        int i;
        int j;
        int totalLines = characters.size();
        int totalColumns;
        int builtNumber;
        boolean symbolFound;
        int sumOfAllNumbersThatHaveAdjacentCharacters = 0;

        for (i = 0; i < totalLines; i++) {
            totalColumns = characters.get(i).size();
            builtNumber = 0;
            symbolFound = false;
            for (j = 0; j < totalColumns; j++) {
                if (Character.isDigit(characters.get(i).get(j))) {
                    builtNumber = builtNumber * 10 + (characters.get(i).get(j) - '0');
                    symbolFound = existSymbolAdjacentWithCurrentDigit(i, j, totalLines, totalColumns, characters, symbolFound);
                } else {
                    if (symbolFound) {
                        sumOfAllNumbersThatHaveAdjacentCharacters += builtNumber;
                        symbolFound = false;
                    }
                    builtNumber = 0;
                }
            }

            if (symbolFound) {
                sumOfAllNumbersThatHaveAdjacentCharacters += builtNumber;
            }
        }

        return sumOfAllNumbersThatHaveAdjacentCharacters;
    }

    public Map<AsteriskCoordinate, List<Integer>> getAllPartNumbers(List<List<Character>> characters) {
        int i;
        int j;
        int totalLines = characters.size();
        int totalColumns;
        int builtNumber;
        List<AsteriskCoordinate> asteriskCoordinates;
        Map<AsteriskCoordinate, List<Integer>> allPartNumbers = buildAllAsteriskCoordinatesWithPartNumbers(characters);

        for (i = 0; i < totalLines; i++) {
            totalColumns = characters.get(i).size();
            builtNumber = 0;
            asteriskCoordinates = new ArrayList<>();
            for (j = 0; j < totalColumns; j++) {
                if (Character.isDigit(characters.get(i).get(j))) {
                    builtNumber = builtNumber * 10 + (characters.get(i).get(j) - '0');
                    if (asteriskCoordinates.isEmpty()) {
                        asteriskCoordinates = getAllAsteriskCoordinatesByCurrentDigitCoordinates(i, j, totalLines, totalColumns, characters);
                    }
                } else {
                    buildPartNumbersListByAsteriskCoordinates(asteriskCoordinates, allPartNumbers, builtNumber);
                    if (!asteriskCoordinates.isEmpty()) {
                        asteriskCoordinates = new ArrayList<>();
                    }
                    builtNumber = 0;
                }
            }

            buildPartNumbersListByAsteriskCoordinates(asteriskCoordinates, allPartNumbers, builtNumber);
        }

        return allPartNumbers;
    }

    private boolean existSymbolAdjacentWithCurrentDigit(int i,
                                                        int j,
                                                        int totalLines,
                                                        int totalColumns,
                                                        List<List<Character>> characters,
                                                        boolean symbolFound) {

        if (!symbolFound) {
            boolean isASymbolOfTheAboveLineAndTheSameColumn;
            boolean isASymbolOfTheBelowLineAndTheSameColumn;
            boolean isASymbolOfTheSameLineAndThePreviousColumn;
            boolean isASymbolOfTheSameLineAndTheNextColumn;
            boolean isASymbolOfTheAboveLineAndOnTheMainDiagonal;
            boolean isASymbolOfTheBelowLineAndOnTheMainDiagonal;
            boolean isASymbolOfTheAboveLineAndOnTheSecondaryDiagonal;
            boolean isASymbolOfTheBelowLineAndOnTheSecondaryDiagonal;

            isASymbolOfTheAboveLineAndTheSameColumn = i - 1 >= 0 && String.valueOf(characters.get(i - 1).get(j)).matches(SYMBOL_REGEX);
            isASymbolOfTheBelowLineAndTheSameColumn = i + 1 < totalLines && String.valueOf(characters.get(i + 1).get(j)).matches(SYMBOL_REGEX);
            isASymbolOfTheSameLineAndThePreviousColumn = j - 1 >= 0 && String.valueOf(characters.get(i).get(j - 1)).matches(SYMBOL_REGEX);
            isASymbolOfTheSameLineAndTheNextColumn = j + 1 < totalColumns && String.valueOf(characters.get(i).get(j + 1)).matches(SYMBOL_REGEX);
            isASymbolOfTheAboveLineAndOnTheMainDiagonal = i - 1 >= 0 && j - 1 >= 0 && String.valueOf(characters.get(i - 1).get(j - 1)).matches(SYMBOL_REGEX);
            isASymbolOfTheBelowLineAndOnTheMainDiagonal = i + 1 < totalLines && j + 1 < totalColumns && String.valueOf(characters.get(i + 1).get(j + 1)).matches(SYMBOL_REGEX);
            isASymbolOfTheAboveLineAndOnTheSecondaryDiagonal = i - 1 >= 0 && j + 1 < totalColumns && String.valueOf(characters.get(i - 1).get(j + 1)).matches(SYMBOL_REGEX);
            isASymbolOfTheBelowLineAndOnTheSecondaryDiagonal = i + 1 < totalLines && j - 1 >= 0 && String.valueOf(characters.get(i + 1).get(j - 1)).matches(SYMBOL_REGEX);
            return isASymbolOfTheAboveLineAndTheSameColumn || isASymbolOfTheBelowLineAndTheSameColumn
                    || isASymbolOfTheSameLineAndThePreviousColumn || isASymbolOfTheSameLineAndTheNextColumn
                    || isASymbolOfTheAboveLineAndOnTheMainDiagonal || isASymbolOfTheBelowLineAndOnTheMainDiagonal
                    || isASymbolOfTheAboveLineAndOnTheSecondaryDiagonal || isASymbolOfTheBelowLineAndOnTheSecondaryDiagonal;
        }
        return true;
    }

    private List<AsteriskCoordinate> getAllAsteriskCoordinatesByCurrentDigitCoordinates(int i,
                                                                                        int j,
                                                                                        int totalLines,
                                                                                        int totalColumns,
                                                                                        List<List<Character>> characters) {
        boolean isAnAsteriskOfTheAboveLineAndTheSameColumn;
        boolean isAnAsteriskOfTheBelowLineAndTheSameColumn;
        boolean isAnAsteriskOfTheSameLineAndThePreviousColumn;
        boolean isAnAsteriskOfTheSameLineAndTheNextColumn;
        boolean isAnAsteriskOfTheAboveLineAndOnTheMainDiagonal;
        boolean isAnAsteriskOfTheBelowLineAndOnTheMainDiagonal;
        boolean isAnAsteriskOfTheAboveLineAndOnTheSecondaryDiagonal;
        boolean isAnAsteriskOfTheBelowLineAndOnTheSecondaryDiagonal;

        List<AsteriskCoordinate> asteriskCoordinates = new ArrayList<>();

        isAnAsteriskOfTheAboveLineAndTheSameColumn = i - 1 >= 0 && String.valueOf(characters.get(i - 1).get(j)).matches(ASTERISK_REGEX);
        isAnAsteriskOfTheBelowLineAndTheSameColumn = i + 1 < totalLines && String.valueOf(characters.get(i + 1).get(j)).matches(ASTERISK_REGEX);
        isAnAsteriskOfTheSameLineAndThePreviousColumn = j - 1 >= 0 && String.valueOf(characters.get(i).get(j - 1)).matches(ASTERISK_REGEX);
        isAnAsteriskOfTheSameLineAndTheNextColumn = j + 1 < totalColumns && String.valueOf(characters.get(i).get(j + 1)).matches(ASTERISK_REGEX);
        isAnAsteriskOfTheAboveLineAndOnTheMainDiagonal = i - 1 >= 0 && j - 1 >= 0 && String.valueOf(characters.get(i - 1).get(j - 1)).matches(ASTERISK_REGEX);
        isAnAsteriskOfTheBelowLineAndOnTheMainDiagonal = i + 1 < totalLines && j + 1 < totalColumns && String.valueOf(characters.get(i + 1).get(j + 1)).matches(ASTERISK_REGEX);
        isAnAsteriskOfTheAboveLineAndOnTheSecondaryDiagonal = i - 1 >= 0 && j + 1 < totalColumns && String.valueOf(characters.get(i - 1).get(j + 1)).matches(ASTERISK_REGEX);
        isAnAsteriskOfTheBelowLineAndOnTheSecondaryDiagonal = i + 1 < totalLines && j - 1 >= 0 && String.valueOf(characters.get(i + 1).get(j - 1)).matches(ASTERISK_REGEX);

        addAsteriskCoordinateInList(asteriskCoordinates, i - 1, j, isAnAsteriskOfTheAboveLineAndTheSameColumn);
        addAsteriskCoordinateInList(asteriskCoordinates, i + 1, j, isAnAsteriskOfTheBelowLineAndTheSameColumn);
        addAsteriskCoordinateInList(asteriskCoordinates, i, j - 1, isAnAsteriskOfTheSameLineAndThePreviousColumn);
        addAsteriskCoordinateInList(asteriskCoordinates, i, j + 1, isAnAsteriskOfTheSameLineAndTheNextColumn);
        addAsteriskCoordinateInList(asteriskCoordinates, i - 1, j - 1, isAnAsteriskOfTheAboveLineAndOnTheMainDiagonal);
        addAsteriskCoordinateInList(asteriskCoordinates, i + 1, j + 1, isAnAsteriskOfTheBelowLineAndOnTheMainDiagonal);
        addAsteriskCoordinateInList(asteriskCoordinates, i - 1, j + 1, isAnAsteriskOfTheAboveLineAndOnTheSecondaryDiagonal);
        addAsteriskCoordinateInList(asteriskCoordinates, i + 1, j - 1, isAnAsteriskOfTheBelowLineAndOnTheSecondaryDiagonal);

        return asteriskCoordinates;
    }

    private void addAsteriskCoordinateInList(List<AsteriskCoordinate> asteriskCoordinates,
                                             int x,
                                             int y,
                                             boolean isAnAsterisk) {
        if (isAnAsterisk) {
            AsteriskCoordinate coordinate;
            coordinate = new AsteriskCoordinate();
            coordinate.setX(x);
            coordinate.setY(y);
            asteriskCoordinates.add(coordinate);
        }
    }

    private Map<AsteriskCoordinate, List<Integer>> buildAllAsteriskCoordinatesWithPartNumbers(List<List<Character>> characters) {
        int i;
        int j;
        int totalLines = characters.size();
        Map<AsteriskCoordinate, List<Integer>> allAsteriskCoordinates = new HashMap<>();
        AsteriskCoordinate asteriskCoordinate;
        List<Integer> partNumbers;
        int totalColumns;
        for (i = 0; i < totalLines; i++) {
            totalColumns = characters.get(i).size();
            for (j = 0; j < totalColumns; j++) {
                if (characters.get(i).get(j).equals('*')) {
                    asteriskCoordinate = new AsteriskCoordinate();
                    asteriskCoordinate.setX(i);
                    asteriskCoordinate.setY(j);
                    partNumbers = new ArrayList<>();
                    allAsteriskCoordinates.put(asteriskCoordinate, partNumbers);
                }
            }
        }

        return allAsteriskCoordinates;

    }

    private void buildPartNumbersListByAsteriskCoordinates(List<AsteriskCoordinate> asteriskCoordinates,
                                                           Map<AsteriskCoordinate, List<Integer>> allPartNumbers,
                                                           int builtNumber) {
        if (!asteriskCoordinates.isEmpty()) {
            for (AsteriskCoordinate coordinate : asteriskCoordinates) {
                if (allPartNumbers.get(coordinate) != null) {
                    if (allPartNumbers.get(coordinate).size() == 2) {
                        allPartNumbers.remove(coordinate);
                    } else {
                        allPartNumbers.get(coordinate).add(builtNumber);
                    }
                }
            }
        }
    }

    public int getSumOfAllTheGearRatios(Map<AsteriskCoordinate, List<Integer>> allPartNumbers) {
        int sumOfAllTheGearsRatios = 0;
        for (List<Integer> partNumbers : allPartNumbers.values()) {
            if (partNumbers.size() == 2) {
                sumOfAllTheGearsRatios += (partNumbers.get(0) * partNumbers.get(1));
            }
        }

        return sumOfAllTheGearsRatios;
    }

}
