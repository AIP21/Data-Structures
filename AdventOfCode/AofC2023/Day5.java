import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;

import utils.*;
import utils.Utils.Range;

public class Day5 extends AbstractDay {
    public static void main(String[] args) {
        new Day5();
    }

    public Day5() {
        super(true, 2023);
    }

    private class Map {
        public long source;
        public long destination;
        public long range;

        public Map(long s, long d, long r) {
            source = s;
            destination = d;
            range = r;
        }
    }

    private class SeedMap {
        public int index = 0;
        public ArrayList<Map> maps = new ArrayList<>();

        public SeedMap(int index) {
            this.index = index;
        }

        public void addMap(Long[] inMap) {
            long destination = inMap[0];
            long source = inMap[1];
            long range = inMap[2];

            // Add the range of numbers being mapped to this map
            maps.add(new Map(source, destination, range));
        }

        public long getMapped(long input) {
            // Iterate through the maps
            for (Map map : maps) {
                // If the input is in the range of the map entry
                if (input >= map.source && input < map.source + map.range) {
                    // Return the mapped value
                    return map.destination + (input - map.source);
                }
            }

            // If the input is not in the range of any map entry, return the input
            return input;
        }

        public ArrayList<Range> getMappedRanges(Range inputRange) {
            // To map one range to another, we need to split up
            // a range based on what sections fall into the seedmap's ranges.
            // We then remap the ranges that fall into the seedmap's ranges
            // and simply pass along the ranges that don't.

            ArrayList<Range> remappedRanges = new ArrayList<>();

            // Iterate through the maps
            for (Map currentMap : maps) {
                // Get the subrange of the inputRange that overlaps with this map's range
                long subStart = Math.max(inputRange.start(), currentMap.source);
                long subEnd = Math.min(inputRange.end(), currentMap.source + currentMap.range);

                // First check if any part of the input falls on this map's range
                if (subStart <= subEnd) {
                    // Determine the subsection of the input range that falls
                    // within this map's source range
                    Range subRange = new Range(subStart, subEnd - subStart);

                    // Add the subrange to the subrange list
                    // subRanges.add(subRange);

                    // Add the left remainder of the input range to the subrange list
                    if (inputRange.start() < subStart) {
                        Range leftSubRange = new Range(inputRange.start(), subStart - inputRange.start());

                        // Add the subRange to the list of remapped ranges
                        remappedRanges.add(leftSubRange);
                    }

                    // Add the right remainder of the input range to the subrange list
                    if (inputRange.end() > subEnd) {
                        Range rightSubRange = new Range(subEnd, inputRange.end() - subEnd);

                        // Add the subRange to the list of remapped ranges
                        remappedRanges.add(rightSubRange);
                    }

                    // Map this subrange to this map's destination range
                    Range remappedRange = new Range(currentMap.destination + (subRange.start() - currentMap.source),
                            subRange.length());

                    // Add this remapped subrange to the list of remapped ranges
                    remappedRanges.add(remappedRange);
                }
            }

            if (remappedRanges.size() == 0) {
                remappedRanges.add(inputRange);
            }

            return remappedRanges;
        }

        public String toString() {
            // Print out the map
            String m = "";

            for (Map map : maps) {
                m += map.source + " - " + map.destination + " > " + map.range + "\n";
            }

            return "Map " + index + ":\n" + m;
        }
    }

    @Override
    public void part1() {
        // testInput("""
        // seeds: 79 14 55 13

        // seed-to-soil map:
        // 50 98 2
        // 52 50 48

        // soil-to-fertilizer map:
        // 0 15 37
        // 37 52 2
        // 39 0 15

        // fertilizer-to-water map:
        // 49 53 8
        // 0 11 42
        // 42 0 7
        // 57 7 4

        // water-to-light map:
        // 88 18 7
        // 18 25 70

        // light-to-temperature map:
        // 45 77 23
        // 81 45 19
        // 68 64 13

        // temperature-to-humidity map:
        // 0 69 1
        // 1 0 69

        // humidity-to-location map:
        // 60 56 37
        // 56 93 4
        // """);

        // Parse out the seeds
        Long[] seeds = longParser.parseString(lines.remove(0).replace("seeds: ", ""));

        ArrayList<SeedMap> seedMaps = new ArrayList<>();

        LinkedHashMap<Long, Long> mappedSeeds = new LinkedHashMap<>();

        // Add the seeds to mappedSeeds
        for (int i = 0; i < seeds.length; i++) {
            mappedSeeds.put(seeds[i], seeds[i]);
        }

        // Print the seed map
        // print("Seed Map", mappedSeeds);

        boolean trackingMap = false;

        // Keep track of which map is being tracked
        int mapBeingTracked = -1;

        // Parse out the rest of the input
        for (String line : lines) {
            // Stop tracking the map if we reach the empty line at the end of a map
            // definition
            if (line.length() == 0) {
                trackingMap = false;
            }

            // Start tracking a map
            else if (!Utils.isDigit(line.charAt(0))) {
                trackingMap = true;
                mapBeingTracked++;
                seedMaps.add(new SeedMap(mapBeingTracked));
            }

            // Track the entries in that map
            else if (trackingMap) {
                Long[] mapNumbers = longParser.parseString(line);

                seedMaps.get(mapBeingTracked).addMap(mapNumbers);
            }
        }

        // Print out the overall map
        // for (SeedMap seedMap : seedMaps) {
        // print(seedMap);
        // }

        // Map seeds from one seedmap to another
        for (SeedMap seedMap : seedMaps) {
            for (long seed : mappedSeeds.keySet()) {
                // Map and update the value
                mappedSeeds.put(seed, seedMap.getMapped(mappedSeeds.get(seed)));
            }

            // print("Map iteration " + seedMap.index, mappedSeeds);
        }

        // Find the lowest location of the seeds
        long lowestLocation = Long.MAX_VALUE;

        for (long seed : mappedSeeds.keySet()) {
            // print("Seed " + seed, "Location " + mappedSeeds.get(seed));

            if (mappedSeeds.get(seed) < lowestLocation) {
                lowestLocation = mappedSeeds.get(seed);
            }
        }

        print("Lowest location", lowestLocation);
    }

    @Override
    public void part2() {
        // testInput("""
        // seeds: 79 14 55 13

        // seed-to-soil map:
        // 50 98 2
        // 52 50 48

        // soil-to-fertilizer map:
        // 0 15 37
        // 37 52 2
        // 39 0 15

        // fertilizer-to-water map:
        // 49 53 8
        // 0 11 42
        // 42 0 7
        // 57 7 4

        // water-to-light map:
        // 88 18 7
        // 18 25 70

        // light-to-temperature map:
        // 45 77 23
        // 81 45 19
        // 68 64 13

        // temperature-to-humidity map:
        // 0 69 1
        // 1 0 69

        // humidity-to-location map:
        // 60 56 37
        // 56 93 4
        // """);

        // Parse out the seeds
        Long[] seeds = longParser.parseString(lines.remove(0).replace("seeds: ", ""));

        ArrayList<SeedMap> seedMaps = new ArrayList<>();

        LinkedHashSet<Range> mappedSeeds = new LinkedHashSet<>();

        shouldPrint(false);

        // Add the seeds to mappedSeeds
        for (int i = 0; i < seeds.length - 1; i += 2) {
            mappedSeeds.add(new Range(seeds[i], seeds[i + 1] - 1));
        }

        // Print the seed map
        print("Seed Map", mappedSeeds);
        print("");

        boolean trackingMap = false;

        // Keep track of which map is being tracked
        int mapBeingTracked = -1;

        // Parse out the rest of the input
        for (String line : lines) {
            // Stop tracking the map if we reach the empty line at the end of a map
            // definition
            if (line.length() == 0) {
                trackingMap = false;
            }

            // Start tracking a map
            else if (!Utils.isDigit(line.charAt(0))) {
                trackingMap = true;
                mapBeingTracked++;
                seedMaps.add(new SeedMap(mapBeingTracked));
            }

            // Track the entries in that map
            else if (trackingMap) {
                Long[] mapNumbers = longParser.parseString(line);

                seedMaps.get(mapBeingTracked).addMap(mapNumbers);
            }
        }

        // Map seeds from one seedmap to another
        for (SeedMap seedMap : seedMaps) {
            // Print out the overall map
            print(seedMap);

            // Remap the seeds
            ArrayList<Range> remappedSeeds = new ArrayList<>();

            for (Range seedRange : mappedSeeds) {
                ArrayList<Range> remappedRanges = seedMap.getMappedRanges(seedRange);

                remappedSeeds.addAll(remappedRanges);
            }

            // Update the mapped seeds
            mappedSeeds.clear();
            mappedSeeds.addAll(remappedSeeds);

            print("Map iteration " + seedMap.index, Utils.toString(mappedSeeds));
            print("");
        }

        // Find the lowest seed location
        int lowestIndex = 0;
        long lowestLocation = Long.MAX_VALUE;

        int i = 0;
        for (Range range : mappedSeeds) {
            // print("Seed Range #" + i, "Location " + mappedSeeds.get(i));

            if (range.start() < lowestLocation && range.start() != 0) {
                lowestLocation = range.start();
                lowestIndex = i;
            }

            i++;
        }

        print("Lowest index", lowestIndex);
        print("Lowest location", lowestLocation);
    }
}