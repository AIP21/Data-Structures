package BirthdayParadox;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;

import utils.CE;

public class BirthdayParadox extends CE {

    // Performs a birthday paradox test with a default size of 1000 people if no
    // argument for test size is given.
    public static void main(String[] args) {
        if (args.length > 0) {
            // Parse and deal with arguments
            handleInput(args[0]);
        } else {
            handleInput("");
        }
    }

    // Handle inputs
    private static void handleInput(String input) {
        if (input != "") {
            try {
                int testSize = Integer.parseInt(input);
                print("Testing with default size of " + testSize + " people...");
                new BirthdayParadox(testSize);
            } catch (NumberFormatException e) {
                try {
                    if (input.toLowerCase() == "h" || input.toLowerCase() == "help") {
                        printHelp();
                    }
                } catch (Exception r) {
                    printHelp();
                }
            }
        } else {
            print("Testing with default size of 1000 people...");
            new BirthdayParadox(1000);
        }
    }

    // Print the help information
    private static void printHelp() {
        print("Incorrect argument.\n\nPossible arguments:\nhelp  - Show the help info.\ntestSize<Integer>  - The size of the test to perform, if left blank then then a test is run with 1000 people.");
    }

    // Create a birthday paradox object
    public BirthdayParadox(int peopleToTest) {
        // Run the test and print it out
        test(peopleToTest);
    }

    // Run a test to find matching birthdays in a group of people of the provided
    // size.
    // Prints the probability of a match.
    private void test(int people) {
        // Array of peoples' birthdays
        Date[] birthdays = new Date[people];

        // Populate the list with random dates
        for (int i = 0; i < people; i++) {
            birthdays[i] = randomDate();
        }

        // Create a list to store duplicate dates
        ArrayList<Date> duplicateDates = new ArrayList<>();

        // Check if there is a duplicate date
        for (int i = 1; i < people; i++) {
            Date date = birthdays[i];

            for (int e = 0; e < people && e != i; e++) {
                Date date2 = birthdays[e];

                // Check if both dates are the same
                if (date.equals(date2)) {

                    // Check if we don't already have this duplicate
                    if (!duplicateDates.contains(date)) {
                        duplicateDates.add(date);
                    }
                }
            }
        }

        // Calculate the percentage of matches
        float prob = duplicateDates.size() / (float) people;

        StringBuilder builder = new StringBuilder();
        builder.append("Probability of having a matching birthday in a group of ");
        builder.append(people);
        builder.append(" people is: ");
        builder.append(prob);
        builder.append("%");

        print(builder.toString());

        print("\nType another integer to perform another test with that size. Type q to quit.");

        // Await the next command
        Scanner in = new Scanner(System.in);

        String str = in.nextLine();

        if (str.toLowerCase() == "q" || str.toLowerCase() == "quit") {
            in.close();
            System.exit(0);
        } else {
            handleInput(str);
        }

        in.close();
    }

    // Returns a Date object with random values for the month and day
    private Date randomDate() {
        Random rand = new Random();
        int month = rand.nextInt(12) + 1;
        int day = rand.nextInt(31) + 1;

        Calendar cal = Calendar.getInstance();
        cal.set(0, month, day);

        return cal.getTime();
    }
}

/*
 * Pseudo code:
 * 1. Initialize an array of Date objects called "birthdays" with a size of
 * "people."
 * 
 * 2. Populate the "birthdays" array with random dates using a loop:
 * for i = 0 to people - 1:
 * birthdays[i] = randomDate()
 * 
 * 3. Create an ArrayList of Date objects called "duplicateDates" to store
 * duplicate dates.
 * 
 * 4. Check for duplicate dates using nested loops:
 * for i = 0 to people - 1:
 * date = birthdays[i]
 * for e = 0 to people - 1:
 * date2 = birthdays[e]
 * if e != i and date equals date2:
 * if !duplicateDates.contains(date):
 * duplicateDates.add(date)
 * 
 * 5. Calculate the probability of duplicate dates:
 * prob = size of duplicateDates / (float) people
 * 
 * 6. Print the probability:
 * print(prob)
 */