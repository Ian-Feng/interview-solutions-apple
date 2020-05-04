package com.apple.interview.day4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * --- Day 4: Security Through Obscurity ---
 * Finally, you come across an information kiosk with a list of rooms. Of course, the list is encrypted and full of decoy data, but the instructions to decode the list are barely hidden nearby. Better remove the decoy data first.
 *
 * Each room consists of an encrypted name (lowercase letters separated by dashes) followed by a dash, a sector ID, and a checksum in square brackets.
 *
 * A room is real (not a decoy) if the checksum is the five most common letters in the encrypted name, in order, with ties broken by alphabetization. For example:
 *
 * aaaaa-bbb-z-y-x-123[abxyz] is a real room because the most common letters are a (5), b (3), and then a tie between x, y, and z, which are listed alphabetically.
 * a-b-c-d-e-f-g-h-987[abcde] is a real room because although the letters are all tied (1 of each), the first five are listed alphabetically.
 * not-a-real-room-404[oarel] is a real room.
 * totally-real-room-200[decoy] is not.
 * Of the real rooms from the list above, the sum of their sector IDs is 1514.
 *
 * What is the sum of the sector IDs of the real rooms?
 *
 * Your puzzle answer was 185371.
 *
 * --- Part Two ---
 * With all the decoy data out of the way, it's time to decrypt this list and get moving.
 *
 * The room names are encrypted by a state-of-the-art shift cipher, which is nearly unbreakable without the right software. However, the information kiosk designers at Easter Bunny HQ were not expecting to deal with a master cryptographer like yourself.
 *
 * To decrypt a room name, rotate each letter forward through the alphabet a number of times equal to the room's sector ID. A becomes B, B becomes C, Z becomes A, and so on. Dashes become spaces.
 *
 * For example, the real name for qzmt-zixmtkozy-ivhz-343 is very encrypted name.
 *
 * What is the sector ID of the room where North Pole objects are stored?
 *
 * Your puzzle answer was 984.
 */
public class Day4 {
    public static void main(String[] args) throws FileNotFoundException {
        Day4 d4 = new Day4();
        Pattern p = Pattern.compile("(.*)-(\\d+)\\[(.*)\\]");
        List<RoomInput> roomInputs = new ArrayList<>();
        Scanner sc = null;
        List<String> rotated = new ArrayList<>();

        sc = new Scanner(new File("./inputs/day4"));
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            Matcher m = p.matcher(line);
            m.matches();

            roomInputs.add(new RoomInput(m.group(1), m.group(2), m.group(3)));
            rotated.add(d4.decrypt(m.group(1), m.group(2)));
            if (d4.decrypt(m.group(1), m.group(2)).contains("northpole object")) {
                System.out.println(m.group(2));
            }
        }

        System.out.println(d4.idSumOfRealRooms(roomInputs));
        System.out.println(rotated);
        sc.close();
    }

    public int idSumOfRealRooms(List<RoomInput> rooms) {
        int sum = 0;
        for (RoomInput room: rooms) {
            if (checkRoom(room)) {
                sum += Integer.parseInt(room.getSectorId());
            }
        }

        return sum;
    }

    private boolean checkRoom(RoomInput room) {
        String[] segments = room.getEncodedName().split("-");
        Map<Character, Frequency> charToFrequency = new HashMap<>();

        // collect the frequency of each character
        for (String segment: segments) {
            for (int i = 0; i < segment.length(); i++) {
                char tempChar = segment.charAt(i);
                if (!charToFrequency.containsKey(tempChar)) {
                    charToFrequency.put(tempChar, new Frequency(0, tempChar));
                }

                Frequency curr = charToFrequency.get(tempChar);
                curr.setFrequency(curr.getFrequency() + 1);
            }
        }

        // sort the characters by frequency and alphabetical order
        List<Character> sortedChars = charToFrequency.values()
            .stream()
            .sorted((Frequency a, Frequency b) ->
                {
                   if (a.getFrequency() != b.getFrequency()) {
                       return b.getFrequency() - a.getFrequency();
                   }
                   return a.getValue() - b.getValue();
        }).map(Frequency::getValue).collect(Collectors.toList());

        // generate the checkSum for comparison
        String generatedCheckSum = sortedChars.stream()
                                    .map(String::valueOf)
                                    .collect(Collectors.joining()).substring(0, 5);

        return room.getCheckSum().equals(generatedCheckSum);
    }

    public String decrypt(String decoded, String sectorId) {
        int rotateTimes = Integer.parseInt(sectorId);
        StringBuilder builder = new StringBuilder();

        for (char c: decoded.toCharArray()) {
            if (c == '-') {
                c = ' ';
            } else {
                c = (char) ((c - 'a' + rotateTimes) % 26 + 'a');
            }
            builder.append(c);
        }

        return builder.toString();
    }

    private static class Frequency {
        private int frequency;
        private char value;

        public Frequency(int frequency, char value) {
            this.frequency = frequency;
            this.value = value;
        }

        public int getFrequency() {
            return frequency;
        }

        public void setFrequency(int frequency) {
            this.frequency = frequency;
        }

        public char getValue() {
            return value;
        }

        public void setValue(char value) {
            this.value = value;
        }
    }
}
