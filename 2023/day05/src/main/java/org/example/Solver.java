package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Solver {

    private List<Long> seeds = new ArrayList<>();

    private final List<Long> seedsCopy = new ArrayList<>();

    private final List<Range> seedToSoil = new ArrayList<>();

    private final List<Range> soilToFertilizer = new ArrayList<>();

    private final List<Range> fertilizerToWater = new ArrayList<>();

    private final List<Range> waterToLight = new ArrayList<>();

    private final List<Range> lightToTemperature = new ArrayList<>();

    private final List<Range> temperatureToHumidity = new ArrayList<>();

    private final List<Range> humidityToLocation = new ArrayList<>();

    private String[] currentLine;

    private boolean readSeedToSoilInformationLines = false;

    private boolean readSoilToFertilizerInformationLines = false;

    private boolean readFertilizerToWaterInformationLines = false;

    private boolean readWaterToLightInformationLines = false;

    private boolean readLightToTemperatureInformationLines = false;

    private boolean readTemperatureToHumidityInformationLines = false;

    private boolean readHumidityToLocationInformationLines = false;

    public void readAllInformationFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;

            while (true) {
                line = reader.readLine();

                if (line == null) {
                    break;
                }

                currentLine = line.split(":");
                changeReadValues();
                currentLine = line.split(" ");
                addRangeInListByReadValue(readSeedToSoilInformationLines,
                        readSoilToFertilizerInformationLines,
                        readFertilizerToWaterInformationLines,
                        readWaterToLightInformationLines,
                        readLightToTemperatureInformationLines,
                        readTemperatureToHumidityInformationLines,
                        readHumidityToLocationInformationLines);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public long getMinLocationValue() {
        int seedsLength;

        seedsLength = seeds.size();
        getSoilsBySeeds(seedsLength);
        getFertilizersBySoils(seedsLength);
        getWatersByFertilizers(seedsLength);
        getLightsByWaters(seedsLength);
        getTemperaturesByLights(seedsLength);
        getHumidityByTemperatures(seedsLength);
        getLocationsByHumidity(seedsLength);

        Optional<Long> isMinPresent = seeds.stream().min(Long::compare);
        if (isMinPresent.isPresent()) {
            return isMinPresent.get();
        }

        return -1;
    }

    public long getLowestLocationFromRangesOfSeeds() {
        List<Long> resultSeeds = new ArrayList<>();
        long min = -1;
        long minTemp;
        int i;
        long j;
        int seedsLength = seeds.size();
        long start;
        long end;
        for (i = 0; i < seedsLength - 1; i++) {
            start = seedsCopy.get(i);
            end = start + seedsCopy.get(i + 1) - 1;
            for (j = start; j < end; j++) {
                resultSeeds.add(j);
                seeds = new ArrayList<>(resultSeeds);
                if (i == 0 && j == start) {
                    min = getMinLocationValue();
                }
                else {
                    minTemp = getMinLocationValue();
                    if (minTemp < min) {
                        min = minTemp;
                    }
                }
                resultSeeds.remove(j);
            }
            i++;
        }

        return min;

    }

    private void addRangeInListByReadValue(boolean readSeedToSoilInformationLines,
                                           boolean readSoilToFertilizerInformationLines,
                                           boolean readFertilizerToWaterInformationLines,
                                           boolean readWaterToLightInformationLines,
                                           boolean readLightToTemperatureInformationLines,
                                           boolean readTemperatureToHumidityInformationLines,
                                           boolean readHumidityToLocationInformationLines) {
        long destinationRangeStart;
        long sourceRangeStart;
        long rangeLength;
        try {
            if (readSeedToSoilInformationLines && !currentLine[0].isEmpty()) {
                destinationRangeStart = Long.parseLong(currentLine[0]);
                sourceRangeStart = Long.parseLong(currentLine[1]);
                rangeLength = Long.parseLong(currentLine[2]);
                addRangeInList(seedToSoil, destinationRangeStart, sourceRangeStart, rangeLength);
            } else if (readSoilToFertilizerInformationLines && !currentLine[0].isEmpty()) {
                destinationRangeStart = Long.parseLong(currentLine[0]);
                sourceRangeStart = Long.parseLong(currentLine[1]);
                rangeLength = Long.parseLong(currentLine[2]);
                addRangeInList(soilToFertilizer, destinationRangeStart, sourceRangeStart, rangeLength);
            } else if (readFertilizerToWaterInformationLines && !currentLine[0].isEmpty()) {
                destinationRangeStart = Long.parseLong(currentLine[0]);
                sourceRangeStart = Long.parseLong(currentLine[1]);
                rangeLength = Long.parseLong(currentLine[2]);
                addRangeInList(fertilizerToWater, destinationRangeStart, sourceRangeStart, rangeLength);
            } else if (readWaterToLightInformationLines && !currentLine[0].isEmpty()) {
                destinationRangeStart = Long.parseLong(currentLine[0]);
                sourceRangeStart = Long.parseLong(currentLine[1]);
                rangeLength = Long.parseLong(currentLine[2]);
                addRangeInList(waterToLight, destinationRangeStart, sourceRangeStart, rangeLength);
            } else if (readLightToTemperatureInformationLines && !currentLine[0].isEmpty()) {
                destinationRangeStart = Long.parseLong(currentLine[0]);
                sourceRangeStart = Long.parseLong(currentLine[1]);
                rangeLength = Long.parseLong(currentLine[2]);
                addRangeInList(lightToTemperature, destinationRangeStart, sourceRangeStart, rangeLength);
            } else if (readTemperatureToHumidityInformationLines && !currentLine[0].isEmpty()) {
                destinationRangeStart = Long.parseLong(currentLine[0]);
                sourceRangeStart = Long.parseLong(currentLine[1]);
                rangeLength = Long.parseLong(currentLine[2]);
                addRangeInList(temperatureToHumidity, destinationRangeStart, sourceRangeStart, rangeLength);
            } else if (readHumidityToLocationInformationLines && !currentLine[0].isEmpty()) {
                destinationRangeStart = Long.parseLong(currentLine[0]);
                sourceRangeStart = Long.parseLong(currentLine[1]);
                rangeLength = Long.parseLong(currentLine[2]);
                addRangeInList(humidityToLocation, destinationRangeStart, sourceRangeStart, rangeLength);
            }
        } catch (Exception ignored) {

        }
    }

    private void addRangeInList(List<Range> ranges,
                                long destinationRangeStart,
                                long sourceRangeStart,
                                long rangeLength) {
        Range range = new Range();
        range.setDestinationRangeStart(destinationRangeStart);
        range.setSourceRangeStart(sourceRangeStart);
        range.setRangeLength(rangeLength);
        ranges.add(range);
    }

    private void changeReadValues() {
        String[] seedsNumbers;
        if ("seeds".equals(currentLine[0])) {
            seedsNumbers = currentLine[1].split(" ");
            for (String seedNumber : seedsNumbers) {
                if (!seedNumber.isEmpty()) {
                    seedsCopy.add(Long.parseLong(seedNumber));
                    seeds.add(Long.parseLong(seedNumber));
                }
            }
        } else if ("seed-to-soil map".equals(currentLine[0])) {
            readSeedToSoilInformationLines = true;
        } else if ("soil-to-fertilizer map".equals(currentLine[0])) {
            readSeedToSoilInformationLines = false;
            readSoilToFertilizerInformationLines = true;
        } else if ("fertilizer-to-water map".equals(currentLine[0])) {
            readSoilToFertilizerInformationLines = false;
            readFertilizerToWaterInformationLines = true;
        } else if ("water-to-light map".equals(currentLine[0])) {
            readFertilizerToWaterInformationLines = false;
            readWaterToLightInformationLines = true;
        } else if ("light-to-temperature map".equals(currentLine[0])) {
            readWaterToLightInformationLines = false;
            readLightToTemperatureInformationLines = true;
        } else if ("temperature-to-humidity map".equals(currentLine[0])) {
            readLightToTemperatureInformationLines = false;
            readTemperatureToHumidityInformationLines = true;
        } else if ("humidity-to-location map".equals(currentLine[0])) {
            readTemperatureToHumidityInformationLines = false;
            readHumidityToLocationInformationLines = true;
        }
    }

    private void getSoilsBySeeds(int seedsLength) {
        setPreviousListToNextList(seedsLength, seedToSoil);
    }

    private void getFertilizersBySoils(int seedsLength) {
        setPreviousListToNextList(seedsLength, soilToFertilizer);
    }

    private void getWatersByFertilizers(int seedsLength) {
        setPreviousListToNextList(seedsLength, fertilizerToWater);
    }

    private void getLightsByWaters(int seedsLength) {
        setPreviousListToNextList(seedsLength, waterToLight);
    }

    private void getTemperaturesByLights(int seedsLength) {
        setPreviousListToNextList(seedsLength, lightToTemperature);
    }

    private void getHumidityByTemperatures(int seedsLength) {
        setPreviousListToNextList(seedsLength, temperatureToHumidity);
    }

    private void getLocationsByHumidity(int seedsLength) {
        setPreviousListToNextList(seedsLength, humidityToLocation);
    }

    private void setPreviousListToNextList(int seedsLength, List<Range> ranges) {
        int i;
        long destinationRangeStart;
        long sourceRangeStart;
        long rangeLength;
        long sourceRangeEnd;
        long destinationRangeEnd;
        for (i = 0; i < seedsLength; i++) {
            for (Range range : ranges) {
                destinationRangeStart = range.getDestinationRangeStart();
                sourceRangeStart = range.getSourceRangeStart();
                rangeLength = range.getRangeLength();
                sourceRangeEnd = sourceRangeStart + rangeLength - 1;
                destinationRangeEnd = destinationRangeStart + rangeLength - 1;
                if (seeds.get(i) >= sourceRangeStart && seeds.get(i) <= sourceRangeEnd) {
                    seeds.set(i, destinationRangeEnd - (sourceRangeEnd - seeds.get(i)));
                    break;
                }
            }
        }
    }

}
