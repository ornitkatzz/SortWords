package com.sortwords;

import java.util.*;
import java.util.stream.Collectors;

public class WordAnalyzer {
    private final Map<String, Integer> wordCount;
    private List<String> sortedWords;

    public WordAnalyzer(List<String> words) {
        this.wordCount = new HashMap<>();
        for (String word : words) {
            wordCount.put(word, wordCount.getOrDefault(word, 0) + 1);
        }
    }

    public void sortWords(String sortOption) {
        Comparator<Map.Entry<String, Integer>> comparator = Map.Entry.comparingByKey();
        if (sortOption.equals("sort -d")) {
            comparator = comparator.reversed();
        }
        sortedWords = wordCount.entrySet().stream()
            .sorted(comparator)
            .map(Map.Entry::getKey)
            .collect(Collectors.toList());
    }
    
    public List<String> getSortedWords() {
        return sortedWords;
    }
    
    public Map<String, Integer> getWordCount() {
        return wordCount;
    }
    
    public List<String> findMostFrequentWords() {
        if (wordCount.isEmpty()) {
            return Collections.emptyList();
        }

        int maxCount = Collections.max(wordCount.values());

        List<String> mostFrequentWords = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : wordCount.entrySet()) {
            if (entry.getValue() == maxCount) {
                mostFrequentWords.add(entry.getKey());
            }
        }

        return mostFrequentWords;
    }


}
