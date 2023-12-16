package utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This is a Day class used for Advent of Code
 */
public abstract class AbstractDay {
    public boolean PRODUCTION = false;

    private final int year;

    private final boolean OVERWRITE_INPUT_FILE = false;

    private PrintStream printFileWriter = null;

    protected String input;
    protected ArrayList<String> lines;

    protected IntParser intParser = new IntParser();
    protected FloatParser floatParser = new FloatParser();
    protected DoubleParser doubleParser = new DoubleParser();
    protected LongParser longParser = new LongParser();
    protected BooleanParser booleanParser = new BooleanParser();

    private boolean skipPart1 = false;

    /**
     * The abstract day for any day in Advent of Code.
     * 
     * @param skipPart1 Whether or not to skip part 1 (in the common case that you
     *                  brute force part 1 and it takes like 5 min to run and now
     *                  you need to test part 2).
     * @param year      The Advent of Code year.
     * @param inputFile The path to the input file for this day. If null or an empty
     *                  string, the input file will automatically be downloaded
     *                  using the session.txt file in the AdventOfCode folder.
     */
    public AbstractDay(boolean skipPart1, int year, String inputFile) {
        this.year = year;
        this.skipPart1 = skipPart1;

        // Get the problem input
        getInput(inputFile);

        // Run both parts of the problem
        runParts();
    }

    /**
     * 
     * The abstract day for any day in Advent of Code.
     * 
     * THIS CONSTRUCTOR SHOULD NOT BE USED ANYMORE!
     * You should always pass the input filename, if empty it'll just auto
     * download/load the input from file.
     * 
     * @param skipPart1 Whether or not to skip part 1 (in the common case that you
     *                  brute force part 1 and it takes like 5 min to run and now
     *                  you need to test part 2).
     * @param year      The advent of code year.
     */
    public AbstractDay(boolean skipPart1, int year) {
        this.year = year;
        this.skipPart1 = skipPart1;

        // Get the problem input
        getInput(null);

        // Run both parts of the problem
        runParts();
    }

    /**
     * Gets the input for the day.
     * 
     * @param inputFile The path to the input file. If null or an empty string, it
     *                  automatically downloads/loads the input file.
     */
    private void getInput(String inputFile) {
        String thisName = this.getClass().toString().replace("class ", "");

        // Determine if this is my laptop or Mr. Iwanski's laptop
        if (!System.getProperty("user.dir").contains("20ani")) {
            this.PRODUCTION = true;
        }

        // If the input file is not null, then just load from the input
        if (inputFile == null || inputFile == "") {
            // Special file loading
            File file = null;

            try {
                // Load from file
                file = new File(inputFile);

                // Load the production input from the input file
                input = "";

                if (file != null) {
                    Scanner reader = new Scanner(file);

                    while (reader.hasNextLine()) {
                        input += reader.nextLine() + "\n";
                    }

                    reader.close();
                }
            } catch (Exception e) {
                System.out.println("Error loading input file: " + Arrays.toString(e.getStackTrace()));
            }
            
        // Read every line in the file and put it into a list
        lines = getLines(input);

        // Calculate log timestamp
        if (!PRODUCTION) {
            setupLogging();
        }

            return;
        }

        // Get the day number for this class (from filename)
        int day = Integer.parseInt(thisName.replace("Day", "").replace("Prob", ""));

        if (!PRODUCTION) {
            // Download and load in the input
            input = Downloader.downloadInput(year, day, OVERWRITE_INPUT_FILE);
        } else {
            // Special file loading
            File file = null;

            try {
                // Load from file
                String filename = "AdventOfCode/AofC" + year + "/data/Input_" + year + "_" + day + ".data";
                file = new File(filename);

                // Load the production input from the input file
                input = "";

                if (file != null) {
                    Scanner reader = new Scanner(file);

                    while (reader.hasNextLine()) {
                        input += reader.nextLine() + "\n";
                    }

                    reader.close();
                }
            } catch (Exception e) {
                System.out.println("Error loading input file: " + Arrays.toString(e.getStackTrace()));
            }
        }

        // Read every line in the file and put it into a list
        lines = getLines(input);

        // Calculate log timestamp
        if (!PRODUCTION) {
            setupLogging();
        }
    }

    /**
     * Runs both parts of the day.
     */
    private void runParts() {
        long start = System.currentTimeMillis();
        if (!skipPart1) {
            print("-------------------<          PART ONE          >-------------------");
            part1();
            shouldPrint(true);
        }

        print("\n-------------------<          PART TWO          >-------------------");
        part2();
        shouldPrint(true);

        print("\nTotal time: " + (System.currentTimeMillis() - start) + "ms");

        log("\n~=~=~=~=~=~=~=~=~=~<         END OF LOG         >~=~=~=~=~=~=~=~=~=~");

        if (printFileWriter != null) {
            try {
                printFileWriter.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Code for part one of this day
     * 
     * @return 0 if failed, 1 if succeeded
     */
    public abstract void part1();

    /**
     * Code for part two of this day
     * 
     * @return 0 if failed, 1 if succeeded
     */
    public abstract void part2();

    // region Printing and Logging
    private boolean isPrinting = true;

    protected void print(Object o) {
        if (isPrinting)
            System.out.println(o);

        log(o);
    }

    protected void print(String label, Object o) {
        if (isPrinting)
            System.out.println(label + ": " + o.toString());

        log(label + ": " + o.toString());
    }

    protected void inprint(Object o) {
        if (isPrinting)
            System.out.print(o);

        logInline(o);
    }

    protected void log(Object o) {
        if (!PRODUCTION)
            printFileWriter.println(o);
    }

    protected void logInline(Object o) {
        if (!PRODUCTION)
            printFileWriter.print(o);
    }

    protected void shouldPrint(boolean toSet) {
        isPrinting = toSet;
    }

    protected void setupLogging() {
        String runDirectory = System.getProperty("user.dir");
        long timestampMillis = System.currentTimeMillis();

        String thisName = this.getClass().toString().replace("class ", "");

        // Download and load in the input
        int day = Integer.parseInt(thisName.replace("Day", "").replace("Prob", ""));

        DateFormat dateTimeFormatter = new SimpleDateFormat("MM-dd-yyyy; HH-mm-ss-SSS");

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestampMillis);
        String logTimestamp = dateTimeFormatter.format(calendar.getTime());

        try {
            String logsDirectory = runDirectory + "/AdventOfCode/AofC" + year + "/files/logs/" + thisName;
            File dir = new File(logsDirectory);

            if (!dir.exists()) {
                dir.mkdirs();
            }

            File file = new File(logsDirectory + "/" + year + "-" + day + " (" + logTimestamp + ").log");
            file.createNewFile();

            printFileWriter = new PrintStream(new BufferedOutputStream(new FileOutputStream(file, true)), true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        print("~=~=~=~=~=~=~=~=~=~< Advent of Code " + year + ": Day " + day + " >~=~=~=~=~=~=~=~=~=~");
        print("-------------------<  " + logTimestamp + "  >-------------------\n");
    }
    // endregion

    // region Input Management
    protected void testInput(String testInput) {
        this.input = testInput;
        this.lines = getLines(this.input);
    }

    private ArrayList<String> getLines(String inputString) {
        return new ArrayList<>(Arrays.asList(inputString.split("\n")));
    }

    protected ArrayList<String> getLinesRange(int start, int end) {
        return new ArrayList<>(lines.subList(start, end));
    }

    protected String getLine(int index) {
        return lines.get(index);
    }

    protected String getLinePart(int lineIndex, int startIndex) {
        return lines.get(lineIndex).substring(startIndex);
    }

    protected String getLinePart(int lineIndex, int startIndex, int endIndex) {
        return lines.get(lineIndex).substring(startIndex, endIndex);
    }

    protected ArrayList<String[]> getLinesSplit(String regex) {
        ArrayList<String[]> splitLines = new ArrayList<>();

        for (String line : lines) {
            splitLines.add(line.split(regex));
        }

        return splitLines;
    }

    protected int getLineCount() {
        return lines.size();
    }

    protected int getLineLength(int index) {
        return lines.get(index).length();
    }

    protected char getChar(int lineIndex, int charIndex) {
        return lines.get(lineIndex).charAt(charIndex);
    }

    // region Number parsing
    protected abstract class AbstractParser<T> {
        protected abstract T parse(String str);

        /**
         * Parses an entire line.
         * The line must not contain any unnecessary characters.
         * 
         * @param lineIndex The line to parse
         * @return The parsed line
         */
        public T parse(int lineIndex) {
            return parse(lines.get(lineIndex));
        }

        /**
         * Parses an entire line.
         * The line must not contain any unnecessary characters.
         * 
         * @param lineIndex  The line to parse
         * @param startIndex The index to start parsing in the line
         * @return The parsed substring of line
         */
        public T parse(int lineIndex, int startIndex) {
            return parse(lines.get(lineIndex).substring(startIndex));
        }

        /**
         * Parses an entire line.
         * The line must not contain any unnecessary characters.
         * 
         * @param lineIndex  The line to parse
         * @param startIndex The index to start parsing in the line
         * @param endIndex   The index to end parsing in the line
         * @return The parsed substring of line
         */
        public T parse(int lineIndex, int startIndex, int endIndex) {
            return parse(lines.get(lineIndex).substring(startIndex, endIndex));
        }

        protected abstract T parseSmart(String str);

        /**
         * Parses an entire line but ignores unnecessary characters.
         * This is more robust, but slightly slower.
         * 
         * @param lineIndex The line to parse
         * @return The parsed value
         */
        public T parseSmart(int lineIndex) {
            return parseSmart(lines.get(lineIndex));
        }

        /**
         * Parses an entire line but ignores unnecessary characters.
         * This is more robust, but slightly slower.
         * 
         * @param lineIndex
         * @return The parsed value
         */
        public T parseSmart(int lineIndex, int startIndex) {
            return parseSmart(lines.get(lineIndex).substring(startIndex));
        }

        /**
         * Parses an entire line but ignores unnecessary characters.
         * This is more robust, but slightly slower.
         * 
         * @param lineIndex  The line to parse
         * @param startIndex The index to start parsing in the line
         * @param endIndex   The index to end parsing in the line
         * @return The parsed substring of line
         */
        public T parseSmart(int lineIndex, int startIndex, int endIndex) {
            return parseSmart(lines.get(lineIndex).substring(startIndex, endIndex));
        }

        /**
         * Parses an entire string as a list, ignoring uncessary characters.
         * 
         * @param toParse The string to parse
         * @return Every parsed value in the string
         */
        public abstract T[] parseString(String toParse);

        /**
         * Parses an entire line as a list, ignoring uncessary characters.
         * 
         * @param lineIndex The line to parse
         * @return Every parsed value in the line
         */
        public T[] parseLine(int lineIndex) {
            return parseString(lines.get(lineIndex));
        }
    }

    // int parser
    protected class IntParser extends AbstractParser<Integer> {
        @Override
        protected Integer parse(String toParse) {
            return Integer.parseInt(toParse);
        }

        @Override
        protected Integer parseSmart(String toParse) {
            Matcher matcher = Pattern.compile("\\d+").matcher(toParse);
            matcher.find();

            return Integer.parseInt(matcher.group());
        }

        @Override
        public Integer[] parseString(String toParse) {
            // Split the input string by spaces
            String[] tokens = toParse.trim().split("\\s+");

            // Create an array to store the integers
            Integer[] result = new Integer[tokens.length];

            // Parse each substring into an integer
            for (int i = 0; i < tokens.length; i++) {
                result[i] = Integer.parseInt(tokens[i]);
            }

            return result;
        }
    }

    // float parser
    protected class FloatParser extends AbstractParser<Float> {
        @Override
        protected Float parse(String toParse) {
            return Float.parseFloat(toParse);
        }

        @Override
        public Float parseSmart(String toParse) {
            Matcher matcher = Pattern.compile("([0-9,.]+)").matcher(toParse);
            matcher.find();

            return Float.parseFloat(matcher.group());
        }

        @Override
        public Float[] parseString(String toParse) {
            // Split the input string by spaces
            String[] tokens = toParse.trim().split("\\s+");

            // Create an array to store the integers
            Float[] result = new Float[tokens.length];

            // Parse each substring into an integer
            for (int i = 0; i < tokens.length; i++) {
                result[i] = Float.parseFloat(tokens[i]);
            }

            return result;
        }
    }

    // double parser
    protected class DoubleParser extends AbstractParser<Double> {
        @Override
        protected Double parse(String toParse) {
            return Double.parseDouble(toParse);
        }

        @Override
        public Double parseSmart(String toParse) {
            Matcher matcher = Pattern.compile("([0-9,.]+)").matcher(toParse);
            matcher.find();

            return Double.parseDouble(matcher.group());
        }

        @Override
        public Double[] parseString(String toParse) {
            // Split the input string by spaces
            String[] tokens = toParse.trim().split("\\s+");

            // Create an array to store the integers
            Double[] result = new Double[tokens.length];

            // Parse each substring into an integer
            for (int i = 0; i < tokens.length; i++) {
                result[i] = Double.parseDouble(tokens[i]);
            }

            return result;
        }
    }

    // long parser
    protected class LongParser extends AbstractParser<Long> {
        @Override
        protected Long parse(String toParse) {
            return Long.parseLong(toParse);
        }

        @Override
        protected Long parseSmart(String toParse) {
            Matcher matcher = Pattern.compile("\\d+").matcher(toParse);
            matcher.find();

            return Long.parseLong(matcher.group());
        }

        @Override
        public Long[] parseString(String toParse) {
            // Split the input string by spaces
            String[] tokens = toParse.trim().split("\\s+");

            // Create an array to store the integers
            Long[] result = new Long[tokens.length];

            // Parse each substring into an integer
            for (int i = 0; i < tokens.length; i++) {
                result[i] = Long.parseLong(tokens[i]);
            }

            return result;
        }
    }

    // boolean parser
    protected class BooleanParser extends AbstractParser<Boolean> {
        @Override
        protected Boolean parse(String toParse) {
            return Boolean.parseBoolean(toParse);
        }

        @Override
        public Boolean parseSmart(String toParse) {
            Matcher matcher = Pattern.compile("//(d+)").matcher(toParse);
            matcher.find();

            return Boolean.parseBoolean(matcher.group());
        }

        @Override
        public Boolean[] parseString(String toParse) {
            // Split the input string by spaces
            String[] tokens = toParse.trim().split("\\s+");

            // Create an array to store the integers
            Boolean[] result = new Boolean[tokens.length];

            // Parse each substring into an integer
            for (int i = 0; i < tokens.length; i++) {
                result[i] = Boolean.parseBoolean(tokens[i]);
            }

            return result;
        }
    }
    // endregion
    // endregion
}