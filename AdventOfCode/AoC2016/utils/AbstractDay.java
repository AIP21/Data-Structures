package utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

/**
 * This is a Day class used for Advent of Code
 */
public abstract class AbstractDay {
    private final int YEAR = 2016;
    private final boolean OVERWRITE_INPUT_FILE = false;

    private PrintStream printFileWriter = null;

    protected final String input;
    protected final ArrayList<String> lines;

    protected IntParser intParser = new IntParser();
    protected FloatParser floatParser = new FloatParser();
    protected DoubleParser doubleParser = new DoubleParser();
    protected BooleanParser booleanParser = new BooleanParser();

    /**
     * 
     * @param skipPart1 Whether or not to skip part 1 (in the common case that you
     *                  brute force part 1 and it takes like 5 min to run and now
     *                  you need to test part 2)
     */
    public AbstractDay(boolean skipPart1) {
        String thisName = this.getClass().toString().replace("class ", "");

        // Download and load in the input
        int day = Integer.parseInt(thisName.replace("Day", ""));
        input = Downloader.downloadInput(YEAR, day, OVERWRITE_INPUT_FILE);

        // Read every line in the file and put it into a list
        lines = getLines(input);

        // Calculate log timestamp
        setupLogging();

        long start = System.currentTimeMillis();
        if (!skipPart1) {
            part1();
        }

        part2();

        print("Total time: " + (System.currentTimeMillis() - start) + "ms");

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
    public abstract int part1();

    /**
     * Code for part two of this day
     * 
     * @return 0 if failed, 1 if succeeded
     */
    public abstract int part2();

    // region Printing and Logging
    protected void print(Object o) {
        System.out.println(o);
        log(o);
    }

    protected void inprint(Object o) {
        System.out.print(o);
        logInline(o);
    }

    protected void log(Object o) {
        printFileWriter.println(o);
    }

    protected void logInline(Object o) {
        printFileWriter.print(o);
    }

    protected void setupLogging() {
        String runDirectory = System.getProperty("user.dir");
        long timestampMillis = System.currentTimeMillis();

        String thisName = this.getClass().toString().replace("class ", "");

        // Download and load in the input
        int day = Integer.parseInt(thisName.replace("Day", ""));

        DateFormat dateTimeFormatter = new SimpleDateFormat("MM-dd-yyyy; HH-mm-ss-SSS");

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestampMillis);
        String logTimestamp = dateTimeFormatter.format(calendar.getTime());

        try {
            String logsDirectory = runDirectory + "/AdventOfCode/AoC" + YEAR + "/files/logs/" + thisName;
            File dir = new File(logsDirectory);

            if (!dir.exists()) {
                dir.mkdirs();
            }

            File file = new File(logsDirectory + "/" + YEAR + "-" + day + " (" + logTimestamp + ").log");
            file.createNewFile();

            printFileWriter = new PrintStream(new BufferedOutputStream(new FileOutputStream(file, true)), true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        print("~=~=~=~=~=~=~=~=~=~< Advent of Code " + YEAR + ": Day " + day + " >~=~=~=~=~=~=~=~=~=~");
        print("-------------------<  " + logTimestamp + "  >-------------------\n");
    }
    // endregion

    // region Input Management
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
    private abstract class AbstractParser<T> {
        public abstract T parse(int lineIndex);

        public abstract T parse(int lineIndex, int startIndex);

        public abstract T parse(int lineIndex, int startIndex, int endInex);

        public abstract T parseSmart(int lineIndex);

        public abstract T parseSmart(int lineIndex, int startIndex);

        public abstract T parseSmart(int lineIndex, int startIndex, int endInex);
    }

    // int parser
    private class IntParser extends AbstractParser<Integer> {
        @Override
        public Integer parse(int lineIndex) {
            return Integer.parseInt(getLine(lineIndex));
        }

        @Override
        public Integer parse(int lineIndex, int startIndex) {
            return Integer.parseInt(getLinePart(lineIndex, startIndex));
        }

        @Override
        public Integer parse(int lineIndex, int startIndex, int endIndex) {
            return Integer.parseInt(getLinePart(lineIndex, startIndex, endIndex));
        }

        @Override
        public Integer parseSmart(int lineIndex) {
            String line = lines.get(lineIndex);
            int firstNumIndex = line.indexOf("[0-9]");
            for (int i = firstNumIndex; i < line.length(); i++) {
                if (!Character.isDigit(line.charAt(i))) {
                    return Integer.parseInt(line.substring(firstNumIndex, i));
                }
            }

            return 0;
        }

        @Override
        public Integer parseSmart(int lineIndex, int startIndex) {
            String line = lines.get(lineIndex);
            for (int i = startIndex; i < line.length(); i++) {
                if (!Character.isDigit(line.charAt(i))) {
                    return Integer.parseInt(line.substring(startIndex, i));
                }
            }

            return 0;
        }

        @Override
        public Integer parseSmart(int lineIndex, int startIndex, int endIndex) {
            String line = lines.get(lineIndex);
            for (int i = startIndex; i < endIndex; i++) {
                if (!Character.isDigit(line.charAt(i))) {
                    return Integer.parseInt(line.substring(startIndex, i));
                }
            }

            return 0;
        }
    }

    // float parser
    private class FloatParser extends AbstractParser<Float> {
        @Override
        public Float parse(int lineIndex) {
            return Float.parseFloat(getLine(lineIndex));
        }

        @Override
        public Float parse(int lineIndex, int startIndex) {
            return Float.parseFloat(getLinePart(lineIndex, startIndex));
        }

        @Override
        public Float parse(int lineIndex, int startIndex, int endIndex) {
            return Float.parseFloat(getLinePart(lineIndex, startIndex, endIndex));
        }

        @Override
        public Float parseSmart(int lineIndex) {
            String line = lines.get(lineIndex);
            int firstNumIndex = line.indexOf("[0-9]");
            for (int i = firstNumIndex; i < line.length(); i++) {
                if (!Character.isDigit(line.charAt(i)) && line.charAt(i) != '.') {
                    return Float.parseFloat(line.substring(firstNumIndex, i));
                }
            }

            return 0.0f;
        }

        @Override
        public Float parseSmart(int lineIndex, int startIndex) {
            String line = lines.get(lineIndex);
            for (int i = startIndex; i < line.length(); i++) {
                if (!Character.isDigit(line.charAt(i)) && line.charAt(i) != '.') {
                    return Float.parseFloat(line.substring(startIndex, i));
                }
            }

            return 0.0f;
        }

        @Override
        public Float parseSmart(int lineIndex, int startIndex, int endIndex) {
            String line = lines.get(lineIndex);
            for (int i = startIndex; i < endIndex; i++) {
                if (!Character.isDigit(line.charAt(i)) && line.charAt(i) != '.') {
                    return Float.parseFloat(line.substring(startIndex, i));
                }
            }

            return 0.0f;
        }
    }

    // double parser
    private class DoubleParser extends AbstractParser<Double> {
        @Override
        public Double parse(int lineIndex) {
            return Double.parseDouble(getLine(lineIndex));
        }

        @Override
        public Double parse(int lineIndex, int startIndex) {
            return Double.parseDouble(getLinePart(lineIndex, startIndex));
        }

        @Override
        public Double parse(int lineIndex, int startIndex, int endIndex) {
            return Double.parseDouble(getLinePart(lineIndex, startIndex, endIndex));
        }

        @Override
        public Double parseSmart(int lineIndex) {
            String line = lines.get(lineIndex);
            int firstNumIndex = line.indexOf("[0-9]");
            for (int i = firstNumIndex; i < line.length(); i++) {
                if (!Character.isDigit(line.charAt(i)) && line.charAt(i) != '.') {
                    return Double.parseDouble(line.substring(firstNumIndex, i));
                }
            }

            return 0.0;
        }

        @Override
        public Double parseSmart(int lineIndex, int startIndex) {
            String line = lines.get(lineIndex);
            for (int i = startIndex; i < line.length(); i++) {
                if (!Character.isDigit(line.charAt(i)) && line.charAt(i) != '.') {
                    return Double.parseDouble(line.substring(startIndex, i));
                }
            }

            return 0.0;
        }

        @Override
        public Double parseSmart(int lineIndex, int startIndex, int endIndex) {
            String line = lines.get(lineIndex);
            for (int i = startIndex; i < endIndex; i++) {
                if (!Character.isDigit(line.charAt(i)) && line.charAt(i) != '.') {
                    return Double.parseDouble(line.substring(startIndex, i));
                }
            }

            return 0.0;
        }
    }

    // boolean parser
    private class BooleanParser extends AbstractParser<Boolean> {
        @Override
        public Boolean parse(int lineIndex) {
            return Boolean.parseBoolean(getLine(lineIndex));
        }

        @Override
        public Boolean parse(int lineIndex, int startIndex) {
            return Boolean.parseBoolean(getLinePart(lineIndex, startIndex));
        }

        @Override
        public Boolean parse(int lineIndex, int startIndex, int endIndex) {
            return Boolean.parseBoolean(getLinePart(lineIndex, startIndex, endIndex));
        }

        @Override
        public Boolean parseSmart(int lineIndex) {
            String line = lines.get(lineIndex);
            int firstNumIndex = line.indexOf("[0-9]");
            for (int i = firstNumIndex; i < line.length(); i++) {
                if (!Character.isDigit(line.charAt(i)) && line.charAt(i) != '.') {
                    return Boolean.parseBoolean(line.substring(firstNumIndex, i));
                }
            }

            return false;
        }

        @Override
        public Boolean parseSmart(int lineIndex, int startIndex) {
            String line = lines.get(lineIndex);
            for (int i = startIndex; i < line.length(); i++) {
                if (!Character.isDigit(line.charAt(i)) && line.charAt(i) != '.') {
                    return Boolean.parseBoolean(line.substring(startIndex, i));
                }
            }

            return false;
        }

        @Override
        public Boolean parseSmart(int lineIndex, int startIndex, int endIndex) {
            String line = lines.get(lineIndex);
            for (int i = startIndex; i < endIndex; i++) {
                if (!Character.isDigit(line.charAt(i)) && line.charAt(i) != '.') {
                    return Boolean.parseBoolean(line.substring(startIndex, i));
                }
            }

            return false;
        }
    }
    // endregion
    // endregion
}