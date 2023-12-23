package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Solver {

    public Map<Integer, List<List<CubeInformation>>> getAllLinesFromFile(String filename) {
        Map<Integer, List<List<CubeInformation>>> games = new HashMap<>();
        List<List<CubeInformation>> allExtractedCubesPerGame;
        List<CubeInformation> extractedCubesPerGame;

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line = reader.readLine();
            String[] gameParts;
            String[] gameIndex;
            String[] cubesSubsets;
            String[] cubes;
            String[] cubeDetails;
            CubeInformation cubeInformation;

            while (line != null) {
                gameParts = line.split(":");
                gameIndex = gameParts[0].split(" ");
                cubesSubsets = gameParts[1].split(";");

                allExtractedCubesPerGame = new ArrayList<>();

                for (String cubesSubset : cubesSubsets) {
                    cubes = cubesSubset.split(",");
                    extractedCubesPerGame = new ArrayList<>();
                    for (String cube : cubes) {
                        cubeDetails = cube.trim().split(" ");
                        cubeInformation = new CubeInformation();
                        cubeInformation.setTotalCubes(Integer.parseInt(cubeDetails[0]));
                        cubeInformation.setColor(cubeDetails[1]);
                        extractedCubesPerGame.add(cubeInformation);
                    }
                    allExtractedCubesPerGame.add(extractedCubesPerGame);
                }

                games.put(Integer.parseInt(gameIndex[1]), allExtractedCubesPerGame);

                line = reader.readLine();
            }

            return games;

        }
        catch (IOException e) {
            return new HashMap<>();
        }
    }

    public int getSumOfAllPossibleGamesIdsCorrespondingWithExistingCubes(int totalRedCubes,
                                                                         int totalGreenCubes,
                                                                         int totalBlueCubes,
                                                                         Map<Integer, List<List<CubeInformation>>> allExtractedCubes) {

        boolean isImpossibleGameVar;
        int sumOfAllPossibleGames = 0;

        for (Map.Entry<Integer, List<List<CubeInformation>>> game : allExtractedCubes.entrySet()) {
            isImpossibleGameVar = false;

            for (List<CubeInformation> cubesInformation : game.getValue()) {
                isImpossibleGameVar = isImpossibleGame(totalRedCubes, totalGreenCubes, totalBlueCubes, cubesInformation);

                if (isImpossibleGameVar) {
                    break;
                }
            }

            if (!isImpossibleGameVar) {
                sumOfAllPossibleGames += game.getKey();
            }
        }

        return sumOfAllPossibleGames;
    }

    public int getSumOfPowersFromAllGames(Map<Integer, List<List<CubeInformation>>> allExtractedCubes) {
        int maxRedCubesFromCurrentGame;
        int maxGreenCubesFromCurrentGame;
        int maxBlueCubesFromCurrentGame;
        int powersSum = 0;
        for (Map.Entry<Integer, List<List<CubeInformation>>> game : allExtractedCubes.entrySet()) {

            maxRedCubesFromCurrentGame = -1;
            maxGreenCubesFromCurrentGame = -1;
            maxBlueCubesFromCurrentGame = -1;
            for (List<CubeInformation> cubesInformation : game.getValue()) {

                for (CubeInformation cubeInformation : cubesInformation) {

                    if ("red".equals(cubeInformation.getColor()) && cubeInformation.getTotalCubes() > maxRedCubesFromCurrentGame) {
                        maxRedCubesFromCurrentGame = cubeInformation.getTotalCubes();
                    } else if ("green".equals(cubeInformation.getColor()) && cubeInformation.getTotalCubes() > maxGreenCubesFromCurrentGame) {
                        maxGreenCubesFromCurrentGame = cubeInformation.getTotalCubes();
                    } else if ("blue".equals(cubeInformation.getColor()) && cubeInformation.getTotalCubes() > maxBlueCubesFromCurrentGame) {
                        maxBlueCubesFromCurrentGame = cubeInformation.getTotalCubes();
                    }

                }
            }

            powersSum = powersSum + (maxRedCubesFromCurrentGame * maxGreenCubesFromCurrentGame * maxBlueCubesFromCurrentGame);
        }

        return powersSum;
    }

    private boolean isImpossibleGame(int totalRedCubes,
                                     int totalGreenCubes,
                                     int totalBlueCubes,
                                     List<CubeInformation> cubesInformation) {
        int totalRedCubesFromSubset = 0;
        int totalGreenCubesFromSubset = 0;
        int totalBlueCubesFromSubset = 0;

        for (CubeInformation cubeInformation : cubesInformation) {
            if ("red".equals(cubeInformation.getColor())) {
                totalRedCubesFromSubset += cubeInformation.getTotalCubes();
            } else if ("green".equals(cubeInformation.getColor())) {
                totalGreenCubesFromSubset += cubeInformation.getTotalCubes();
            } else if ("blue".equals(cubeInformation.getColor())) {
                totalBlueCubesFromSubset += cubeInformation.getTotalCubes();
            }

            if (totalRedCubesFromSubset > totalRedCubes || totalBlueCubesFromSubset > totalBlueCubes
                    || totalGreenCubesFromSubset > totalGreenCubes) {
                return true;
            }
        }

        return false;
    }


}
