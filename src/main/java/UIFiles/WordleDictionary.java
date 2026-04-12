/*
 * File: WordleDictionary.java
 * ---------------------------
 * This file exports the dictionary of five-letter words used in the
 * Wordle project.
 */

package UIFiles;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.ArrayList;
import java.net.*;
import java.util.Objects;

/**
 * This class implements the dictionary for the Wordle project.
 */

public class WordleDictionary {

    static String filePath = "UIFiles/valid-wordle-words.txt";
    static String filePath2 = "test-words.txt";
    public static final String[] FIVE_LETTER_WORDS;
    public static final String[] TEST_WORDS;


    static {
        List<String> tempList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                Objects.requireNonNull(WordleDictionary.class.getResourceAsStream("/UIFiles/valid-wordle-words.txt"))
        ))) {
            String line;
            while ((line = br.readLine()) != null) {
                tempList.add(line.trim());
            }
        } catch (IOException | NullPointerException e) {
            System.out.println("File not found on classpath!");
        }
        FIVE_LETTER_WORDS = tempList.toArray(new String[0]);
    }
    
    static 
    {
        List<String> tempList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath2))) 
        {
            String line;
            while ((line = br.readLine()) != null) {
                tempList.add(line);
            }
        } catch (IOException e) {
            //e.printStackTrace();
            System.out.println("File not found! Make sure you have the test-words.txt file in the main folder!"); 
        }
        TEST_WORDS = tempList.toArray(new String[0]);
    }
}
