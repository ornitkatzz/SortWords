package tests;

import org.junit.jupiter.api.Test;
import com.sortwords.FileHandler;
import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FileHandlerTest {
    FileHandler fileProcessor = new FileHandler();
    private final String TEST_FILE_DIR = "src/tests/testFiles/";

    private String getFilePath(String fileName) {
        return TEST_FILE_DIR + fileName;
    }

    @Test
    public void testReadAndCleanFile_validFile() throws IOException {
    	String filePath = getFilePath("FP_validFile.txt");
        List<String> words = fileProcessor.readAndCleanFile(filePath);
        
        Set<String> expectedWords = Set.of("hello", "world", "this", "is", "a", "test", "file");
        Set<String> actualWords = new HashSet<>(words);

        assertFalse(words.isEmpty(), "Words list should not be empty for valid input file.");
        assertEquals(expectedWords, actualWords, "The words read from the file should match the expected set of words.");
    }
    
    @Test
    public void testGetFirstValue() throws IOException {
    	String filePath = getFilePath("FP_validFile.txt");
        String firstValue = fileProcessor.getFirstValue(filePath);

        assertEquals("hello", firstValue, "The first word in the file should be 'hello'.");
    }

    @Test
    public void testGetLastValue() throws IOException {
    	String filePath = getFilePath("FP_validFile.txt");
        String lastValue = fileProcessor.getLastValue(filePath);

        assertEquals("file", lastValue, "The last word in the file should be 'file'.");
    }

    @Test
    public void testReadAndCleanFile_emptyFile() throws IOException {
        String emptyFilePath = getFilePath("emptyFile.txt");
        List<String> words = fileProcessor.readAndCleanFile(emptyFilePath);
        assertTrue(words.isEmpty(), "Words list should be empty for an empty file.");
    }
    
    @Test
    public void testGetFirstValueEmpty() throws IOException {
    	String emptyFilePath = getFilePath("emptyFile.txt");
        String firstValue = fileProcessor.getFirstValue(emptyFilePath);

        assertNull(firstValue, "The first value in an empty file should be null.");
    }

    @Test
    public void testGetLastValueEmpty() throws IOException {
    	String emptyFilePath = getFilePath("emptyFile.txt");
        String lastValue = fileProcessor.getLastValue(emptyFilePath);

        assertNull(lastValue, "The last value in an empty file should be null.");
    }

    @Test
    public void testReadAndCleanFile_specialCharactersFile() throws IOException {
        String specialCharsFilePath = getFilePath("specialChars.txt");
        List<String> words = fileProcessor.readAndCleanFile(specialCharsFilePath);
        assertTrue(words.isEmpty(), "Words list should be empty if the file only contains special characters.");
    }
    
    @Test
    public void testGetFirstValueSpecialChars() throws IOException {
    	String specialCharsFilePath = getFilePath("specialChars.txt");
        String firstValue = fileProcessor.getFirstValue(specialCharsFilePath);

        assertNull(firstValue, "The first value in an empty file should be null.");
    }

    @Test
    public void testGetLastValueSpecialChars() throws IOException {
    	String specialCharsFilePath = getFilePath("specialChars.txt");
        String lastValue = fileProcessor.getLastValue(specialCharsFilePath);

        assertNull(lastValue, "The last value in an empty file should be null.");
    }
    
    @Test
    public void testReadAndCleanFileOneWord() throws IOException {
    	String oneWordFilePath = getFilePath("oneWordFile.txt");
        List<String> words = fileProcessor.readAndCleanFile(oneWordFilePath);

        assertEquals(1, words.size(), "There should be exactly one word in the list.");
        assertEquals("hello", words.get(0), "The only word in the file should be 'hello'.");
    }

    @Test
    public void testGetFirstValueOneWord() throws IOException {
    	String oneWordFilePath = getFilePath("oneWordFile.txt");
        String firstValue = fileProcessor.getFirstValue(oneWordFilePath);

        assertEquals("hello", firstValue, "The first value should be 'hello'.");
    }

    @Test
    public void testGetLastValueOneWord() throws IOException {
    	String oneWordFilePath = getFilePath("oneWordFile.txt");
        String lastValue = fileProcessor.getLastValue(oneWordFilePath);

        assertEquals("hello", lastValue, "The last value should be 'hello'.");
    }

    @Test
    public void testReadAndCleanFile_nonExistentFile() {
        String filePath = getFilePath("nonExistentFile.txt");
        assertThrows(IOException.class, () -> {
            fileProcessor.readAndCleanFile(filePath);
        });
    }

    @Test
    public void testReadAndCleanFile_largeFile() throws IOException {
        String largeFilePath = getFilePath("largeFile.txt");
        List<String> words = fileProcessor.readAndCleanFile(largeFilePath);
        assertFalse(words.isEmpty(), "Words list should not be empty for a large file.");
    }
    
    @Test
    public void testGetFirstValue_largeFile() throws IOException {
    	String largeFilePath = getFilePath("largeFile.txt");
        String firstValue = fileProcessor.getFirstValue(largeFilePath);

        assertEquals("the", firstValue, "The first word in the file should be 'the'.");
    }

    @Test
    public void testGetLastValue_largeFile() throws IOException {
    	String largeFilePath = getFilePath("largeFile.txt");
        String lastValue = fileProcessor.getLastValue(largeFilePath);

        assertEquals("s", lastValue, "The last word in the file should be 's'.");
    }
    
    @Test
    public void testReadAndCleanFileMixedContent() throws IOException {
    	String mixedContentFilePath = getFilePath("FP_mixedContentFile.txt");
        List<String> words = fileProcessor.readAndCleanFile(mixedContentFilePath);

        Set<String> expectedWords = Set.of("red", "yellow", "black");
        Set<String> actualWords = new HashSet<>(words);

        assertEquals(expectedWords, actualWords, "The words list should match the expected words.");
    }
    
    @Test
    public void testWriteToFile() throws IOException {
        List<String> words = List.of("blue", "red", "white");
        fileProcessor.writeToFile("output.txt", words);
        File file = new File("output.txt");
        assertTrue(file.exists(), "The file should be created after writing.");
        List<String> fileContent = Files.readAllLines(file.toPath());
        assertEquals(words, fileContent, "The file content should match the list of words.");
    }
}
