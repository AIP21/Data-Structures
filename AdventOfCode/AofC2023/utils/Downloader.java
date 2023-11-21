package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class Downloader {

    private static String getSessionCookie() {
        String runDirectory = System.getProperty("user.dir");
        String sessionFileDir = runDirectory + "/AdventOfCode/session.txt";
        File file = new File(sessionFileDir);
        String cookieString = "";

        try {
            Scanner reader = new Scanner(file);
            while (reader.hasNextLine()) {
                cookieString = reader.nextLine();
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return cookieString;
    }

    public static String downloadInput(int year, int day, boolean overwrite) {
        String url = "https://adventofcode.com/" + year + "/day/" + day + "/input";
        String input = "";

        try {
            String inputFileDirectory = System.getProperty("user.dir") + "/AdventOfCode/AofC" + year + "/data/";
            File inputFile = new File(inputFileDirectory + "/" + day + ".txt");

            if (inputFile.exists() && !overwrite) {
                Scanner reader = new Scanner(inputFile);

                while (reader.hasNextLine()) {
                    input += reader.nextLine() + "\n";
                }

                reader.close();

                return input;
            }

            HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
            con.setRequestMethod("GET");

            con.addRequestProperty("Cookie", "session=" + getSessionCookie());

            // System.out.println(con.getInputStream());
            // System.out.println(con.getResponseMessage());

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                // System.out.println(inputLine);
                input += inputLine + "\n";
            }

            in.close();

            // Write the input to a file
            new File(inputFileDirectory).mkdirs();
            inputFile.createNewFile();

            PrintWriter writer = new PrintWriter(inputFile);
            writer.write(input);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return input;
    }
}
