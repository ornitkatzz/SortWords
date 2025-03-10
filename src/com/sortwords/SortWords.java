package com.sortwords;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class SortWords {
    public static void main(String[] args) throws IOException {

        if (args.length == 0) {
            System.out.println("Error: No input file path was entered");
            return;
        }
        
        // Print the received argument: file path
        System.out.println("Received arguments: " + Arrays.toString(args));

        String filePath = args[0];
        Scanner scanner = new Scanner(System.in);
        String sortOption;
        
        // Step 1: Ask user for sorting option
        while (true) {
            System.out.println("Please enter the desired sorting type (sort -a for ascending, sort -d for descending):");
            sortOption = scanner.nextLine().trim().toLowerCase();
            if (sortOption.equals("sort -a") || sortOption.equals("sort -d")) {
                break; // Valid input, exit loop
            } else {
                System.out.println("Invalid input");
            }
        }
        scanner.close(); // Close scanner after input 
        
        Map<String, Integer> wordCount = new HashMap<>();     // key = word, value = count
        Set<String> uniqueWords;
        
        // Step 2: requested order = descending: the tree should be reversed. order = ascending: tree should be in natural order (default)
        if (sortOption.equals("sort -d")) {
            uniqueWords = new TreeSet<>(Comparator.reverseOrder()); // Descending order
        } else {
            uniqueWords = new TreeSet<>();                          // Ascending order (default)
        }
        
        // Step 3: going over the input file, and in each line: converting to lowercase, removing unwanted characters, and splitting into words.
        //         then adding to treeset (sorted & unique) and incrementing the word's counter (in HashMap)
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean hasValidWords = false;  // Flag to check if the file has valid words
            while ((line = reader.readLine()) != null) {
                String[] words = line.toLowerCase().replaceAll("[^a-z\\s]+", " ").split("\\s+");
                
                // Check if there are valid words in the line
                if (words.length > 0 && !words[0].isEmpty()) {
                    hasValidWords = true;
                }
                
                for (String word : words) {
                    if (!word.isEmpty()) {
                        uniqueWords.add(word); // Auto-sort & unique
                        wordCount.put(word, wordCount.getOrDefault(word, 0) + 1);
                    }
                }
            }
            if (!hasValidWords) {
                System.out.println("The file is empty or contains only invalid characters.");
                return;
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file: " + e.getMessage());
            return; // Stop execution if file reading fails
        }
        
        // Step 4: Write sorted words to F2.txt directly from the TreeSet
        Path outputFilePath = Paths.get("F2.txt");
        try (BufferedWriter writer = Files.newBufferedWriter(outputFilePath)) {
        	int counter = 0;
            // Using a for-each loop to go over the TreeSet, which is already sorted
            for (String word : uniqueWords) {
            	if (counter == 0)
            		writer.write(word);
            	else
            		writer.write(", " + word);
            	counter++;
            }
            System.out.println("The file F2.txt has been created.");
        }

        // Step 5: Find the most frequent word
        String mostFrequentWord = Collections.max(wordCount.entrySet(), Map.Entry.comparingByValue()).getKey();
        System.out.println("The most frequent word is '" + mostFrequentWord + "', count: " + wordCount.get(mostFrequentWord));
    }
}
