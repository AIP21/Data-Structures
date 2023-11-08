package utils;

// general utilities for the Advent of Code
// problems.

import java.io.File;
import java.util.Scanner;
import java.util.ArrayList;

public class AofCUtils
{
  // for reading in data from a file called fileName and
  // storing the data in an array list of strings for
  // later processing.
  public static ArrayList<String> getData(String fileName)
  {
    // result will be the list of strings we get from the file
    ArrayList<String> result = new ArrayList<>();

    try
    {
      //constructor of file class having file as argument
      File file = new File(fileName);

      //file to be scanned
      Scanner sc = new Scanner(file);

      //returns true IFF scanner has another token
      while (sc.hasNextLine())
        result.add( sc.nextLine() );

      // close the scanner
      sc.close();
    }
    catch(Exception e)
    { e.printStackTrace(); }

    // return the result
    return result;
  }

}
