package com.sortwords;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class SortWords {

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Error: No input file path was entered");
            return;
        }
        
        String filePath = args[0];
        
        try {
            // Get the sorting option from the user
            UserInputHandler inputHandler = new UserInputHandler();
            String sortOption = inputHandler.getSortingOrder();
            
            // Process the file and write the output
            SortResult result = processFile(filePath, sortOption, true);
            
            // Print the most frequent word(s) and its count
            System.out.println("The most frequent word(s): " + result.getMostFrequentWords() + 
                               " - Count: " + result.getMostFrequentCount());
        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public static SortResult processFile(String filePath, String sortOrder, boolean writeToFile) throws IOException {
        FileHandler fileProcessor = new FileHandler();
        List<String> words = fileProcessor.readAndCleanFile(filePath);
        
        if (words.isEmpty()) {
            throw new IllegalArgumentException("The file is empty or contains only invalid characters.");
        }
        
        WordAnalyzer analyzer = new WordAnalyzer(words);
        analyzer.sortWords(sortOrder);
        
        List<String> sortedWords = analyzer.getSortedWords();
        Map<String, Integer> wordCount = analyzer.getWordCount();
        List<String> mostFrequentWords = analyzer.findMostFrequentWords();
        int mostFrequentCount = wordCount.get(mostFrequentWords.get(0));
        
        if (writeToFile) {
            fileProcessor.writeToFile("F2.txt", sortedWords);
        }
        
        return new SortResult(sortedWords, mostFrequentWords, mostFrequentCount);
    }
    
 
    // Nested class to encapsulate the sorting and word frequency analysis results.
    
    public static class SortResult {
        private final List<String> sortedWords;
        private final List<String> mostFrequentWords;
        private final int mostFrequentCount;
        
        public SortResult(List<String> sortedWords, List<String> mostFrequentWords, int mostFrequentCount) {
            this.sortedWords = sortedWords;
            this.mostFrequentWords = mostFrequentWords;
            this.mostFrequentCount = mostFrequentCount;
        }
        
        public List<String> getSortedWords() {
            return sortedWords;
        }
        
        public List<String> getMostFrequentWords() {
            return mostFrequentWords;
        }
        
        public int getMostFrequentCount() {
            return mostFrequentCount;
        }
    }
}
