package BirthdayParadox;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;

import utils.CE;
import utils.RandomUtils;

/**
 * Birthday Paradox.
 * Takes in one argument. It can be "h" or "help" to print a help menu that
 * lists the possible commands, or an integer representing the test size.
 * If an integer is supplied (or if no arguments are given), it runs a test of
 * that size (or the default size of 1000 if no argument was given).
 * Once the test is finished, it prints out the resulting percentage of
 * overlapping birthdays.
 * 
 * @author Alexander Irausquin-Petit (September, 2023)
 */
public class BirthdayParadox extends CE {

    // Performs a birthday paradox test with a default size of 1000 people if no
    // argument for test size is given.
    public static void main(String[] args) {
        // Parse and deal with arguments
        handleInput(args);
    }

    // Handle inputs
    private static void handleInput(String[] input) {
        if (input.length == 0) {
            print("Testing with default size of 1000 people with 10 trials...");
            // Run the test and print it out
            new BirthdayParadox(1000, 10);
        } else if (input.length == 1) {
            try {
                int testSize = Integer.parseInt(input[0]);
                print("Testing with size of " + testSize + " people and 10 trials...");
                new BirthdayParadox(testSize, 10);
            } catch (NumberFormatException e) {
                try {
                    if (input[0].toLowerCase() == "h" || input[0].toLowerCase() == "help") {
                        printHelp();
                    }
                } catch (Exception r) {
                    printHelp();
                }
            }
        } else if (input.length == 2) {
            try {
                int testSize = Integer.parseInt(input[0]);
                int testTrials = Integer.parseInt(input[1]);

                print("Testing with size of " + testSize + " people and with " + testTrials + " trials...");

                new BirthdayParadox(testSize, testTrials);
            } catch (NumberFormatException e) {
                printHelp();
            }
        }
    }

    // Print the help information
    private static void printHelp() {
        print("Incorrect argument.\n\nPossible arguments:\nhelp  - Show the help info.\ntestSize<Integer>  - The size of the test to perform, if left blank then then a test is run with 1000 people.");
    }

    // Create a birthday paradox object
    public BirthdayParadox(int peopleToTest) {
        // Run the test and print it out
        test(peopleToTest, 10);
    }

    // Create a birthday paradox object
    public BirthdayParadox(int peopleToTest, int trials) {
        // Run the test and print it out
        test(peopleToTest, trials);
    }

    // Run a given number of tests to find matching birthdays in a group of people
    // of the provided
    // size.
    // Prints the probability of a match.
    private void test(int people, int trials) {
        // Create a boolean array to track which birthdays have already been generated
        boolean[] hasBirthday = new boolean[365];

        // Count the number of experiments in which two or more people share a common
        // birthday
        int matches = 0;

        // Run the experiments
        for (int t = 0; t < people; t++) {
            for (int tr = 0; tr < trials; tr++) {
                // Generate a random birthday
                int d = RandomUtils.randomInt(0, 365);

                // If the birthday has already been generated and added to the array, then two
                // people share a common
                // birthdayp0
                if (hasBirthday[d] == true) {
                    matches++;
                } else {
                    hasBirthday[d] = true;
                }
            }
        }

        // Calculate the probability of two or more people sharing a common birthday
        double prob = (double) matches / trials;

        // Format and print the results
        StringBuilder builder = new StringBuilder();
        builder.append("Probability of having a matching birthday in a group of ");
        builder.append(people);
        builder.append(" people is: ");
        builder.append(prob * 100);
        builder.append("%");

        print(builder.toString());

        print("\nProvide two integers, one for the next test size and another for the number of trials to perform. Type q to quit.");

        // Await the next command
        Scanner in = new Scanner(System.in);

        String str = in.nextLine();

        if (str.toLowerCase() == "q" || str.toLowerCase() == "quit") {
            in.close();
            System.exit(0);
        } else {
            handleInput(str.split(" "));
        }

        in.close();
    }
}

/*
 * Pseudo code:
 * Initialize:
 * Set the number of people in the room, n, to the user input.
 * Set the number of experiments to run, trials, to the user input.
 * Create a boolean array hasBirthday of size n, initialized to all false.
 * 
 * Run the experiments:
 * For each experiment t from 0 to trials - 1:
 * Generate a random integer d between 0 and 365, representing a day of a year.
 * If hasBirthday[d] is true, then two people in the room have the same
 * birthday. Increment the counter of matches.
 * Otherwise, set hasBirthday[d] to true.
 * 
 * Calculate the probability:
 * Calculate the probability of two or more people sharing a common birthday as
 * matches / trials.
 * 
 * Output the results:
 * Print the empirically-determined probability that two or more people share a
 * common birthday for each value of n from 5 to 100.
 */