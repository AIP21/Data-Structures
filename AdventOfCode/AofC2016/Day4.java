import java.util.ArrayList;
import java.util.Arrays; // Import Arrays
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

import utils.AbstractDay;
import utils.Utils;

public class Day4 extends AbstractDay {
    private class Room {
        public String name;
        public int sectorID;
        public String checksum;
        public String decryptedName;

        public Room(String name, int sectorID, String checksum) {
            this.name = name;
            this.sectorID = sectorID;
            this.checksum = checksum;
        }
    }

    private ArrayList<Room> rooms = new ArrayList<>();

    public static void main(String[] args) {
        new Day4();
    }

    public Day4() {
        super(false);
    }

    @Override
    public void part1() {
        rooms = new ArrayList<>();

        // testInput("""
        // aaaaa-bbb-z-y-x-123[abxyz]
        // a-b-c-d-e-f-g-h-987[abcde]
        // not-a-real-room-404[oarel]
        // totally-real-room-200[decoy]
        // """);

        shouldPrint(false);

        int realSectorIDSum = 0;

        // Go through every line in the input
        for (String room : lines) {
            String[] parts = room.split("\\[");

            // Separate out the line
            String name = parts[0].substring(0, parts[0].lastIndexOf('-'));
            int sectorID = Integer.parseInt(parts[0].substring(parts[0].lastIndexOf('-') + 1));

            String checksum = parts[1].replace("]", "");

            // Sort the name by character
            char[] sortedName = name.toCharArray();
            Arrays.sort(sortedName);

            // Get the counts of characters in the name
            TreeMap<Character, Integer> charCounts = new TreeMap<>();

            for (char ch : sortedName) {
                if (ch == '-') {
                    continue;
                }

                if (charCounts.containsKey(ch)) {
                    charCounts.put(ch, charCounts.get(ch) + 1);
                } else {
                    charCounts.put(ch, 1);
                }
            }

            // Sort the map of characters by their counts
            LinkedHashMap<Character, Integer> sortedMap = new LinkedHashMap<>();
            ArrayList<Integer> counts = new ArrayList<>();

            for (Map.Entry<Character, Integer> entry : charCounts.entrySet()) {
                counts.add(entry.getValue());
            }

            Collections.sort(counts);
            Collections.reverse(counts);

            for (int num : counts) {
                for (Map.Entry<Character, Integer> entry : charCounts.entrySet()) {
                    if (entry.getValue().equals(num)) {
                        sortedMap.put(entry.getKey(), num);
                    }
                }
            }

            print(sortedMap);

            // Assemble a checksum out of the name's characters
            String checksumFromName = "";
            int index = 0;
            for (Character ch : sortedMap.keySet()) {
                if (index >= 5) {
                    break;
                }

                checksumFromName += ch;

                index++;
            }

            print("Checksum from name", checksumFromName);

            boolean realRoom = checksum.equals(checksumFromName);

            // Add to the sum of real sector id's (only if this room is real)
            if (realRoom) {
                realSectorIDSum += sectorID;
            }

            // Print some info
            print(name);
            print(sectorID);
            print(checksum);
            print("REAL ROOM", realRoom);
            print("");

            rooms.add(new Room(name, sectorID, checksum));
        }

        shouldPrint(true);

        print("Sum of real sector IDs", realSectorIDSum);
    }

    @Override
    public void part2() {
        // Go through every room.
        // This uses the data from part 1
        for (Room room : rooms) {
            char[] shiftedChars = room.name.toCharArray();

            // Iterate for sectorID times
            for (int i = 0; i < room.sectorID; i++) {
                // Shift every character in the name by one
                for (int e = 0; e < shiftedChars.length; e++) {
                    char ch = shiftedChars[e];
                    // Loop back from z to a
                    if (ch == 'z') {
                        shiftedChars[e] = 'a';
                    } // Replace dashes with spaces
                    else if (ch == '-') {
                        shiftedChars[e] = ' ';
                    } // If this is not a special case, just shift the character by one
                    else {
                        shiftedChars[e] = (char) (ch + 1);
                    }
                }
            }

            // After we finished iterating, reassemble the decrypted name
            String decryptedName = "";
            for (char ch : shiftedChars) {
                decryptedName += ch;
            }

            room.decryptedName = decryptedName;
            // print(room.name, room.decryptedName);

            // If this room is where the North Pole objects are stored
            // then print out its name and sector id
            if (room.decryptedName.contains("north")) {
                print(room.decryptedName);
                print(room.sectorID);
            }
        }
    }
}