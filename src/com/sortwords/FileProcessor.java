package com.sortwords;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class FileProcessor {
	
    public List<String> readAndCleanFile(String filePath) throws IOException {
        List<String> words = new ArrayList<>();
        
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fixedWords = line.toLowerCase().replaceAll("[^a-z\s]+", " ").split("\\s+");
                for (String word : fixedWords) {
                    if (!word.isEmpty()) {
                        words.add(word);
                    }
                }
            }
        }
        return words;
    }
    
    public void writeToFile(String fileName, List<String> sortedWords) throws IOException {
        Path outputFilePath = Paths.get(fileName);
        Files.write(outputFilePath, sortedWords);
        System.out.println("The file " + fileName + " has been created.");
    }
    
    public String getFirstValue(String filePath) throws IOException {
        List<String> words = readAndCleanFile(filePath);
        if (!words.isEmpty()) {
            return words.get(0);
       }
        return null;
    }

    public String getLastValue(String filePath) throws IOException {
        List<String> words = readAndCleanFile(filePath);
        if (!words.isEmpty()) {
            return words.get(words.size() - 1);
        }
        return null;
    }
}