package com.example.Lesbarkeit;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TextAnalyse {

    private static final List<String> FILTER_TOKEN = Arrays.asList(".", "!", "?", ",", ":", ";", "–", "(", ")", "\"",
            "'");
    private static final String READING_TIME_FORMAT = "%02d:%02d:%02d";
    private static Sentence se = new Sentence();
    private static String content;
    private int sentences;
    private int complex;
    private int words;
    private int syllables;
    private String characters;
  
    public String getContent() {
      return content;
    }

    public int getWords() {
        return words;
    }

    public int getSentences() {
        return sentences;
    }

    public String getCharacters() {
        return characters;
    }


    public static void setContent(String content) {
      TextAnalyse.content = content;
    }

    public int getNumberOfComplexWords(){
        setContent(content);
        String[] word = content.split("\\s+");
        int complexWords = 0;
        for(String w: word){
            if(w.length()>=6){
                complexWords++;
            }
        }
        return complexWords;
    }

    public static int getNumberOfWords() {
        setContent(content);
        String[] word = content.split("\\s+");
        int words = 0;
        for(String w: word){
            if(w.length() > 0){
                words++;
            }
        }
        return words;
    }

    public static int numberOfDiphthongs() {
        setContent(content);
        int numberDiphthongs = 0;
        for (int i = 1; i <= content.length() - 1; i++) {
            if (content.charAt(i -1) == 'a') {
                if (content.charAt(i) == 'i' || content.charAt(i) == 'u') {
                    numberDiphthongs += 1;
                }
            }
            if (content.charAt(i -1) == 'ä') {
                if (content.charAt(i) == 'u') {
                    numberDiphthongs += 1;
                }

            }
            if (content.charAt(i-1) == 'e') {
                if (content.charAt(i) == 'i' || content.charAt(i) == 'u') {
                    numberDiphthongs += 1;
                }

            }
            if (content.charAt(i - 1) == 'u') {
                if (content.charAt(i) == 'i') {
                    numberDiphthongs += 1;
                }

            }

        }
        return numberDiphthongs;

    }

    public int getNumberOfCharacters(){
        setContent(content);
        String[] word = content.split("");

        int characters = 0;
        for(String w:word){
            characters+=w.length();
        }
        return characters;
    }

    public int getNumberOfSyllables(){
        setContent(content);
        String[] words = Silben.determineWord(content);
        int countSyllables = 0;
        for(String w: words){
            countSyllables++;
        }
        return countSyllables;
    }
    public double persentageOfWordsWithThreeOrMoreSyllables(){
        setContent(content);
        int countSyll = 0;
        String[] word = content.split(" ");
        for (String w : word) {
            String[] words = Silben.determineWord(w);
            if(words.length>=3){
                countSyll++;
            }
        }
        return (double) countSyll/getNumberOfWords();
    }
    public double averageNumberOfWordsPerSentence(){
        return (double) getNumberOfWords()/getNumberOfSentences();
    }
    public double persentageOfWordsWithOneSyllable(){
        setContent(content);
        int countSyll = 0;
        String[] word = content.split(" ");
        for (String w : word) {
            String[] words = Silben.determineWord(w);
            if(words.length==1){
                countSyll++;
            }
        }
        return (double) countSyll/getNumberOfWords();
    }

    public float percentageOfWordsWithSixOrMoreMoreCharacters(){
        return (float) getNumberOfComplexWords()/getNumberOfWords();
    }

    public static int numberOfPerSentence() {
        setContent(content);
        String[] tokens = content.split("[.?!]");
        int numsToken = tokens.length;
        String[] words = content.split(" ");

        int numWordspersentence = 0;
        for (int i = 0; i < numsToken; i++) {

            String[] wordPerSentence = tokens[i].split("[ ]+");
            /*            System.out.println(Arrays.toString(wordPerSentence));*/
            numWordspersentence = wordPerSentence.length;
        }
        return numWordspersentence;
    }
    public HashMap<String, Integer> getNumberOfWordsPerSentence () {
        setContent(content);
        content = content.trim();
        String[] tokens = content.split("[.?!]");
        int numsToken = tokens.length;
        String[] words = content.split(" ");
        int[] numWordspersentence = new int[numsToken];
        HashMap<String, Integer> sentenceBuild = new HashMap<>();
        for (int i = 0; i < numsToken; i++) {

            String[] wordPerSentence = tokens[i].split("[ ]+");
            //*            System.out.println(Arrays.toString(wordPerSentence));*//*
            numWordspersentence[i] = wordPerSentence.length;
            int num = i+1;
            sentenceBuild.put("Sätze #" + num, numWordspersentence[i]);
        }

       /* String[] strings = new String[sentenceBuild.size()];*/
        return sentenceBuild;
    }

    public int getNumberOfSentences(){
        setContent(content);
        int l = se.getSentences(content).length;
        if(l>0){
            return l;
        }else if(content.length()>0){
            return 1;
        }
        return 0;
    }


    public double getFleschReadingEase(){
        double score = 180 - (double) getNumberOfWords()/getNumberOfSentences() - 58.5 * getNumberOfSyllables()/getNumberOfWords();
        return round(score, 3);
    }

    public String getAge() {
        if (getFleschReadingEase() <= 30 && getFleschReadingEase() >= 0) {
            return "Sehr schwer: Scientific. Hochschulabsolvent.\n Sehr schwer zu lesen. Am besten von Hochschulabsolventen verstanden.";
        } else if (getFleschReadingEase() >=30 && getFleschReadingEase() <= 50) {
            return "Schwierig: Academic. Hochschule.\n Schwierig zu lesen.";
        } else if (getFleschReadingEase() >=50 && getFleschReadingEase() <= 60) {
            return "Relativ schwer: Quality. Geeignet für 10. bis 12. Klasse. Klasse.\n Ziemlich schwer zu lesen. ";
        } else if (getFleschReadingEase() >= 60 && getFleschReadingEase() <= 70) {
            return "Standard: Digests. Geeignet für 8. & 9. Klasse.\n Einfaches Deutsch. Leicht verständlich für 13- bis 15-jährige Schüler.";
        } else if (getFleschReadingEase() >= 70 && getFleschReadingEase() <= 80) {
            return "Relativ leicht: Slick-fiction. Geeignet für 7. Klasse.\nZiemlich einfach zu lesen.";
        } else if (getFleschReadingEase() >= 80 && getFleschReadingEase() <= 90) {
            return "Leicht: Pulp-fiction. Geeignet für 6. Klasse. \n Leicht zu lesen. Konversationales Englisch für Konsumenten.";
        }else if (getFleschReadingEase() >= 90 && getFleschReadingEase() <= 100) {
            return "Sehr leicht: Comics. Geeignet für 5. Klasse. \n Sehr leicht zu lesen. Leicht verständlich für einen durchschnittlichen 11-jährigen Schüler.";
        }
        return "Falsch Werten";
    }

    public double getFlesch_Kincaid_Grade_Level(){
        double score = (0.39 * (double) getNumberOfWords()/getNumberOfSentences()) + (11.8 * getNumberOfSyllables()/getNumberOfWords()) - 15.59;
        return round(score, 3);
    }

    public double getGunningFog() {
            double score = 0.4 * ((double) getNumberOfWords() / getNumberOfSentences() + (double) 100 * getNumberOfComplexWords() / getNumberOfWords());
            return round(score, 3);
    }

    public double calculateWienerSachtextformel() {
        float ms = (float) (persentageOfWordsWithThreeOrMoreSyllables() * 100);
        double sl = averageNumberOfWordsPerSentence();
        float iw = percentageOfWordsWithSixOrMoreMoreCharacters() * 100;
        float es = (float) (persentageOfWordsWithOneSyllable() * 100);

        return 0.1935 * ms + 0.1672 * sl + 0.1297 * iw - 0.0327 * es - 0.875;
    }

    public double precentageOfComplexWords(){
        double complexWordPrecentage = (double) (getNumberOfComplexWords()*100)/getNumberOfWords();
        return round(complexWordPrecentage, 3);
    }

    public double getLIX(){
        double score = (double) getNumberOfWords()/getNumberOfSentences() + precentageOfComplexWords();
        return round(score,3);
    }
    private static Double round(double d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Double.valueOf(d).longValue());
        bd = bd.setScale(3, BigDecimal.ROUND_HALF_UP);
        return bd.doubleValue();
    }

    public static String getReadingTime() {
        setContent(content);
        int wordCount = getNumberOfWords();
        // This equals 225 words per minute
        long secondsToRead = (long) (wordCount / 0.00375);
        return parseTime(secondsToRead, READING_TIME_FORMAT);
    }

    public static String getSpeakingTime() {
        setContent(content);
        int wordCount = getNumberOfWords();
        // This equals to 125 words per minute
        long secondsToSpeak = (long) (wordCount / 0.002083);
        return parseTime(secondsToSpeak, READING_TIME_FORMAT);
    }


    private static String parseTime(long milliseconds, String format) {
        return String.format(format, TimeUnit.MILLISECONDS.toHours(milliseconds),
                TimeUnit.MILLISECONDS.toMinutes(milliseconds)
                        - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(milliseconds)),
                TimeUnit.MILLISECONDS.toSeconds(milliseconds)
                        - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(milliseconds)));
    }


}