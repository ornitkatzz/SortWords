package tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.sortwords.FileProcessor;
import com.sortwords.SortWords;
import com.sortwords.SortWords.SortResult;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FullFlowTest {
    
    private static final String TEST_FILE_DIR = "src/tests/testFiles/";
    private String getFilePath(String fileName) {
        return TEST_FILE_DIR + fileName;
    }
    private static final String OUTPUT_FILE_PATH = "F2.txt";
    FileProcessor fileProcessor = new FileProcessor();

    @BeforeEach
    public void setUp() throws IOException {
        Files.deleteIfExists(Paths.get(OUTPUT_FILE_PATH));
    }

    @AfterEach
    public void cleanUp() throws IOException {
        Files.deleteIfExists(Paths.get(OUTPUT_FILE_PATH));
    }

    @Test
    public void testFullFlowValidFileAscending() throws IOException {
    	String validFilePath = getFilePath("validFile.txt");
        SortResult result = SortWords.processFile(validFilePath, "sort -a", true);

        List<String> sortedWords = result.getSortedWords();
        
        // validating the flow result
        assertEquals("all", sortedWords.get(0), "The first word should be 'all'");  
        assertEquals("with", sortedWords.get(sortedWords.size() - 1), "The last word should be 'with'");
        // validating that the output file was created
        assertTrue(Files.exists(Paths.get(OUTPUT_FILE_PATH)), "Output file should be created for the valid input file.");
        // validating that the expected values are written correctly in the output file
        assertEquals(sortedWords.get(0), fileProcessor.getFirstValue(OUTPUT_FILE_PATH), "The first word in the output file should be 'all'");
        assertEquals(sortedWords.get(sortedWords.size() - 1), fileProcessor.getLastValue(OUTPUT_FILE_PATH), "The last word in the output file should be 'with'");
        
        // Assert: Check most frequent words
        List<String> mostFrequentWords = result.getMostFrequentWords();
        assertTrue(mostFrequentWords.contains("some"), "Most frequent words should include 'some'");
        // Assert: Check frequency count - "some" appears 6 times
        assertEquals(6, result.getMostFrequentCount(), "Most frequent word 'some' should appear 6 times.");
    }

    @Test
    public void testFullFlowValidFileDescending() throws IOException {
    	String validFilePath = getFilePath("validFile.txt");
        SortResult result = SortWords.processFile(validFilePath, "sort -d", true);

        List<String> sortedWords = result.getSortedWords();
        
        // validating the flow result
        assertEquals("with", sortedWords.get(0), "The first word should be 'with'"); 
        assertEquals("all", sortedWords.get(sortedWords.size() - 1), "The last word should be 'all'");
        // validating that the output file was created
        assertTrue(Files.exists(Paths.get(OUTPUT_FILE_PATH)), "Output file should be created for the valid input file.");
        // validating that the expected values are written correctly in the output file
        assertEquals(sortedWords.get(0), fileProcessor.getFirstValue(OUTPUT_FILE_PATH), "The first word in the output file should be 'with'");
        assertEquals(sortedWords.get(sortedWords.size() - 1), fileProcessor.getLastValue(OUTPUT_FILE_PATH), "The last word in the output file should be 'all'");

        // Assert: Check most frequent words
        List<String> mostFrequentWords = result.getMostFrequentWords();
        assertTrue(mostFrequentWords.contains("some"), "Most frequent words should include 'some'");
        // Assert: Check frequency count - "some" appears 6 times
        assertEquals(6, result.getMostFrequentCount(), "Most frequent word 'some' should appear 6 times.");
    }
    
    @Test
    public void testFullFlowValidFileOneWord() throws IOException {
    	String validFilePath = getFilePath("oneWordFile.txt");
        SortResult result = SortWords.processFile(validFilePath, "sort -d", true);

        List<String> sortedWords = result.getSortedWords();
        
        // validating the flow result
        assertEquals("hello", sortedWords.get(0), "The first(only) word should be 'hello'"); 
        assertEquals("hello", sortedWords.get(sortedWords.size() - 1), "The last (only) word should be 'hello'");
        // validating that the output file was created
        assertTrue(Files.exists(Paths.get(OUTPUT_FILE_PATH)), "Output file should be created for the valid input file.");
        // validating that the expected values are written correctly in the output file
        assertEquals(sortedWords.get(0), fileProcessor.getFirstValue(OUTPUT_FILE_PATH), "The first (only) word in the output file should be 'hello'");
        assertEquals(sortedWords.get(sortedWords.size() - 1), fileProcessor.getLastValue(OUTPUT_FILE_PATH), "The last (only) word in the output file should be 'hello'");

        // Assert: Check most frequent words
        List<String> mostFrequentWords = result.getMostFrequentWords();
        assertTrue(mostFrequentWords.contains("hello"), "Most frequent (and only) word is 'hello'");
        // Assert: Check frequency count - "hello" appears 1 time
        assertEquals(1, result.getMostFrequentCount(), "Most frequent (and only) word is 'hello' should appear 1 time.");
    }
    
    @Test
    public void testFullFlowValidLargeFileAscending() throws IOException {
    	String validFilePath = getFilePath("largeFile.txt");
        SortResult result = SortWords.processFile(validFilePath, "sort -a", true);

        List<String> sortedWords = result.getSortedWords();
        
        assertEquals("a", sortedWords.get(0), "The first word should be 'a");
        assertEquals("zygomatic", sortedWords.get(sortedWords.size() - 1), "The last word should be 'zygomatic'");
        // validating that the output file was created
        assertTrue(Files.exists(Paths.get(OUTPUT_FILE_PATH)), "Output file should be created for the valid input file.");
        // validating that the expected values are written correctly in the output file
        assertEquals(sortedWords.get(0), fileProcessor.getFirstValue(OUTPUT_FILE_PATH), "The first word in the output file should be 'a'");
        assertEquals(sortedWords.get(sortedWords.size() - 1), fileProcessor.getLastValue(OUTPUT_FILE_PATH), "The last word in the output file should be 'zygomatic'");

        // Assert: Check most frequent words
        List<String> mostFrequentWords = result.getMostFrequentWords();
        assertTrue(mostFrequentWords.contains("the"), "Most frequent words should include 'the'");
        // Assert: Check frequency count - "the" appears 80030 times
        assertEquals(80030, result.getMostFrequentCount(), "Most frequent word 'the' should appears 80030 times.");
    }

    @Test
    public void testFullFlowEmptyFile() throws IOException {
    	String emptyFilePath = getFilePath("emptyFile.txt");

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                SortWords.processFile(emptyFilePath, "sort -a", true));
        assertEquals("The file is empty or contains only invalid characters.", exception.getMessage());
        assertFalse(Files.exists(Paths.get("F2.txt")), "No output file should be created for an empty input file.");
    }

    @Test
    public void testFullFlowOnlySpecialCharsFile() throws IOException {
    	String specialCharsFile = getFilePath("specialChars.txt");

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                SortWords.processFile(specialCharsFile, "sort -a", true));
        assertEquals("The file is empty or contains only invalid characters.", exception.getMessage());
        assertFalse(Files.exists(Paths.get("F2.txt")), "No output file should be created for an input file that only has special characters.");
    }

    @Test
    public void testFullFlowInputFileDoesntExist() {
    	String nonExistentFile = getFilePath("nonExistentFile.txt");
        
        NoSuchFileException exception = assertThrows(NoSuchFileException.class, () ->
                SortWords.processFile(nonExistentFile, "sort -a", true));
        
        String expectedFilePath = Paths.get(nonExistentFile).toString();
        assertEquals(expectedFilePath, exception.getFile(), "Exception should report the missing file path.");
        assertFalse(Files.exists(Paths.get("F2.txt")), "No output file should be created for an non-existent input file.");
    }

    @Test
    public void testFullFlowMultipleMostFrequentWords() throws IOException {
    	String multipleMostFrequentWordsFile = getFilePath("multipleMostFrequentWords.txt");

        SortResult result = SortWords.processFile(multipleMostFrequentWordsFile, "sort -a", true);

        // Assert: Check sorted words (first word should be "all")
        List<String> sortedWords = result.getSortedWords();
        // validating the flow result
        assertEquals("all", sortedWords.get(0), "The first word should be 'all'");  
        assertEquals("with", sortedWords.get(sortedWords.size() - 1), "The last word should be 'with'");
        // validating that the output file was created
        assertTrue(Files.exists(Paths.get(OUTPUT_FILE_PATH)), "Output file should be created for the valid input file.");
        // validating that the expected values are written correctly in the output file
        assertEquals(sortedWords.get(0), fileProcessor.getFirstValue(OUTPUT_FILE_PATH), "The first word in the output file should be 'all'");
        assertEquals(sortedWords.get(sortedWords.size() - 1), fileProcessor.getLastValue(OUTPUT_FILE_PATH), "The last word in the output file should be 'with'");

        // Assert: Check most frequent words - expecting "one","two" and "some" to be the most frequent words
        Set<String> expectedMostFrequent = Set.of("one", "some", "two");
        Set<String> actualMostFrequent = new HashSet<>(result.getMostFrequentWords());
        assertEquals(expectedMostFrequent, actualMostFrequent, "Most frequent words should be exactly one, some, and two.");

        // Assert: Check frequency count - both "one" and "some" appear 6 times
        assertEquals(6, result.getMostFrequentCount(), "Most frequent words should appear 6 times.");
    }
}
