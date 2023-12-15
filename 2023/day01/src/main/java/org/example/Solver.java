package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Solver {

    public List<String> getAllLinesFromFile(String filename) {
        BufferedReader reader;
        List<String> lines = new ArrayList<>();
        try {
            reader = new BufferedReader(new FileReader(filename));
            String line = reader.readLine();

            while (line != null) {
                lines.add(line);
                line = reader.readLine();
            }

            return lines;
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int getSumOfCalibrationValuesFirstPart(List<String> lines) {
        int sum = 0;
        int length;
        int i;
        int j;
        int firstDigit;
        int lastDigit;
        for (String line : lines) {
            length = line.length();
            firstDigit = -1;
            lastDigit = -1;
            for (i = 0; i < length; i++) {
                j = length - (i + 1);
                if (Character.isDigit(line.charAt(i)) && firstDigit == -1) {
                    firstDigit = Integer.parseInt(String.valueOf(line.charAt(i)));
                }
                if (Character.isDigit(line.charAt(j)) && lastDigit == -1) {
                    lastDigit = Integer.parseInt(String.valueOf(line.charAt(j)));
                }

                if (firstDigit != -1 && lastDigit != -1) {
                    break;
                }

            }

            if (firstDigit > 0 && lastDigit > 0) {
                sum += (firstDigit * 10 + lastDigit);
            }
        }

        return sum;
    }

    public int getSumOfCalibrationValuesSecondPart(List<String> lines) {
        Map<String, Integer> digitsMap = this.buildDigitsMap();
        int firstDigit;
        int lastDigit;
        int length;
        int i, j;
        int sum = 0;
        for (String line : lines) {
            List<DigitWithPosition> parts = getPartsFromLine(line);
            length = parts.size();
            firstDigit = -1;
            lastDigit = -1;
            for (i = 0; i < length; i++) {
                j = length - (i + 1);
                firstDigit = getDigit(digitsMap, firstDigit, i, parts);

                lastDigit = getDigit(digitsMap, lastDigit, j, parts);

                if (firstDigit != -1 && lastDigit != -1) {
                    break;
                }
            }

            if (firstDigit > 0 && lastDigit > 0) {
                sum += (firstDigit * 10 + lastDigit);
            }
        }

        return sum;
    }

    private int getDigit(Map<String, Integer> digitsMap, int digit, int j, List<DigitWithPosition> parts) {
        boolean isIntegerDigit;
        boolean isStringDigit;
        isIntegerDigit = parts.get(j).getDigit().matches("^[0-9]$");
        isStringDigit = digitsMap.containsKey(parts.get(j).getDigit());
        if ((isIntegerDigit || isStringDigit) && digit == -1) {
            if (isIntegerDigit) {
                digit = Integer.parseInt(parts.get(j).getDigit());
            }
            else {
                digit = digitsMap.get(parts.get(j).getDigit());
            }
        }
        return digit;
    }

    private List<DigitWithPosition> getPartsFromLine(String line) {
        List<DigitWithPosition> parts = new ArrayList<>();
        String[] allDigits = new String[18];
        allDigits[0] = "one";
        allDigits[1] = "two";
        allDigits[2] = "three";
        allDigits[3] = "four";
        allDigits[4] = "five";
        allDigits[5] = "six";
        allDigits[6] = "seven";
        allDigits[7] = "eight";
        allDigits[8] = "nine";
        allDigits[9] = "1";
        allDigits[10] = "2";
        allDigits[11] = "3";
        allDigits[12] = "4";
        allDigits[13] = "5";
        allDigits[14] = "6";
        allDigits[15] = "7";
        allDigits[16] = "8";
        allDigits[17] = "9";

        Matcher matcher;
        Comparator<DigitWithPosition> comparator = Comparator.comparingInt(DigitWithPosition::getStartPosition);
        for (String digit : allDigits) {
            matcher = Pattern.compile(digit).matcher(line);
            while (matcher.find()) {
                DigitWithPosition digitWithPosition = new DigitWithPosition();
                digitWithPosition.setStartPosition(matcher.start());
                digitWithPosition.setDigit(matcher.group());
                parts.add(digitWithPosition);
            }
        }

        parts.sort(comparator);
        return parts;
    }

    private Map<String, Integer> buildDigitsMap() {
        Map<String, Integer> digits = new HashMap<>();
        digits.put("one", 1);
        digits.put("two", 2);
        digits.put("three", 3);
        digits.put("four", 4);
        digits.put("five", 5);
        digits.put("six", 6);
        digits.put("seven", 7);
        digits.put("eight", 8);
        digits.put("nine", 9);
        return digits;
    }

}
