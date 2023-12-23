package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Solver {

    private static final String SYMBOL_REGEX = "^[^0-9.]$";

    public List<List<Character>> getAllCharactersFromFile(String filename) {
        List<List<Character>> allCharacters = new ArrayList<>();
        List<Character> charactersPerLine;
        int i;
        int lineLength;

        try(BufferedReader reader = new BufferedReader(new FileReader(filename))){
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
        }
        catch (IOException ex) {
            return new ArrayList<>();
        }
    }

    public int getSumOfAllNumbersThatHaveAdjacentCharacters(List<List<Character>> characters) {
        int i;
        int j;
        int totalLines = characters.size();
        int totalColumns;
        int builtNumber;
        boolean isASymbolOfTheAboveLineAndTheSameColumn;
        boolean isASymbolOfTheBelowLineAndTheSameColumn;
        boolean isASymbolOfTheSameLineAndThePreviousColumn;
        boolean isASymbolOfTheSameLineAndTheNextColumn;
        boolean isASymbolOfTheAboveLineAndOnTheMainDiagonal;
        boolean isASymbolOfTheBelowLineAndOnTheMainDiagonal;
        boolean isASymbolOfTheAboveLineAndOnTheSecondaryDiagonal;
        boolean isASymbolOfTheBelowLineAndOnTheSecondaryDiagonal;
        boolean symbolFound;
        int sumOfAllNumbersThatHaveAdjacentCharacters = 0;

        for (i = 0; i < totalLines; i++) {
            totalColumns = characters.get(i).size();
            builtNumber = 0;
            symbolFound = false;
            for (j = 0; j < totalColumns; j++) {
                if (Character.isDigit(characters.get(i).get(j))) {
                    builtNumber = builtNumber * 10 + (characters.get(i).get(j) - '0');
                    if(!symbolFound) {
                        isASymbolOfTheAboveLineAndTheSameColumn = i - 1 >= 0 && String.valueOf(characters.get(i - 1).get(j)).matches(SYMBOL_REGEX);
                        isASymbolOfTheBelowLineAndTheSameColumn = i + 1 < totalLines && String.valueOf(characters.get(i + 1).get(j)).matches(SYMBOL_REGEX);
                        isASymbolOfTheSameLineAndThePreviousColumn = j - 1 >= 0 && String.valueOf(characters.get(i).get(j - 1)).matches(SYMBOL_REGEX);
                        isASymbolOfTheSameLineAndTheNextColumn = j + 1 < totalColumns && String.valueOf(characters.get(i).get(j + 1)).matches(SYMBOL_REGEX);
                        isASymbolOfTheAboveLineAndOnTheMainDiagonal = i - 1 >= 0 && j - 1 >= 0 && String.valueOf(characters.get(i - 1).get(j - 1)).matches(SYMBOL_REGEX);
                        isASymbolOfTheBelowLineAndOnTheMainDiagonal = i + 1 < totalLines && j + 1 < totalColumns && String.valueOf(characters.get(i + 1).get(j + 1)).matches(SYMBOL_REGEX);
                        isASymbolOfTheAboveLineAndOnTheSecondaryDiagonal = i - 1 >= 0 && j + 1 < totalColumns && String.valueOf(characters.get(i - 1).get(j + 1)).matches(SYMBOL_REGEX);
                        isASymbolOfTheBelowLineAndOnTheSecondaryDiagonal = i + 1 < totalLines && j - 1 >= 0 && String.valueOf(characters.get(i + 1).get(j - 1)).matches(SYMBOL_REGEX);
                        if (isASymbolOfTheAboveLineAndTheSameColumn || isASymbolOfTheBelowLineAndTheSameColumn
                                || isASymbolOfTheSameLineAndThePreviousColumn || isASymbolOfTheSameLineAndTheNextColumn
                                || isASymbolOfTheAboveLineAndOnTheMainDiagonal || isASymbolOfTheBelowLineAndOnTheMainDiagonal
                                || isASymbolOfTheAboveLineAndOnTheSecondaryDiagonal || isASymbolOfTheBelowLineAndOnTheSecondaryDiagonal) {
                            symbolFound = true;
                        }
                    }
                }
                else {
                    if(symbolFound) {
                        sumOfAllNumbersThatHaveAdjacentCharacters += builtNumber;
                        symbolFound = false;
                    }
                    builtNumber = 0;
                }
            }

            if(symbolFound) {
                sumOfAllNumbersThatHaveAdjacentCharacters += builtNumber;
            }
        }

        return sumOfAllNumbersThatHaveAdjacentCharacters;
    }

}
