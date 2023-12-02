import utils.AbstractDay;
import utils.MathHelper;
import utils.Utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;

public class Day5 extends AbstractDay {
    public static void main(String[] args) {
        new Day5();
    }

    public Day5() {
        super(true, 2016);
    }

    @Override
    public void part1() {
        // testInput("abc");
        input = input.replace("\n", "");

        String password = "";

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");

            for (long i = 0; i < 5000000000L; i++) {
                byte[] messageBytes = (input + i).getBytes("UTF-8");

                byte[] theMD5digest = md.digest(messageBytes);

                String hex = Utils.bytesToHex(theMD5digest, 3);

                if (hex.substring(0, 5).equals("00000")) {
                    print("index", i);
                    print("string", input + i);
                    print("md5 hex", hex);

                    if (password.length() < 8) {
                        password += hex.charAt(5);
                    } else {
                        print("DONE", password);
                        break;
                    }
                }
            }
        } catch (Exception e) {
            print("ERROR");
        }
    }

    @Override
    public void part2() {
        // testInput("abc");
        input = input.replace("\n", "");

        char[] password = new char[8];

        ArrayList<Boolean> stored = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            stored.add(false);
        }

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");

            for (long i = 0; i < 5000000000L; i++) {
                byte[] messageBytes = (input + i).getBytes("UTF-8");

                byte[] theMD5digest = md.digest(messageBytes);

                String hex = Utils.bytesToHex(theMD5digest, 4);

                if (hex.substring(0, 5).equals("00000")) {
                    if (stored.contains(false)) {
                        char ch = hex.charAt(5);

                        // Check if the position char is a digit
                        if (!Character.isDigit(ch)) {
                            continue;
                        }

                        int position = Integer.parseInt(String.valueOf(ch));

                        // Can we set the char at the position
                        // or is it nonexistent or already there?
                        if (position >= 8 || stored.get(position) == true) {
                            continue;
                        }

                        print("index", i);
                        print("string", input + i);
                        print("md5 hex", hex);

                        password[position] = hex.charAt(6);
                        stored.set(position, true);

                        print("Current password", Arrays.toString(password));
                        print("");
                    } else {
                        print("");
                        print("");
                        print("");

                        print("DONE", String.valueOf(password).toLowerCase());
                        break;
                    }
                }
            }
        } catch (Exception e) {
            print("\nERROR", e.getMessage() + "\n---\n" + Arrays.toString(e.getStackTrace()).replace(", ", "\n"));
        }
    }
}