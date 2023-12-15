package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Solver {

    public List<String> getAllLinesFromFile(String filename) {
        BufferedReader reader;
        List<String> lines = new ArrayList<String>();
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

    public int getSumOfCalibrationValues(List<String> lines) {
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
            sum += (firstDigit * 10 + lastDigit);
        }

        return sum;
    }


}
