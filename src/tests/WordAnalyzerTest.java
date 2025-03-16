package tests;

import com.sortwords.WordAnalyzer;
import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

public class WordAnalyzerTest {

    @Test
    public void testWordCount() {
        List<String> words = Arrays.asList("home", "world", "home", "code", "world", "world");
        WordAnalyzer analyzer = new WordAnalyzer(words);

        assertEquals(2, analyzer.getWordCount().get("home"));
        assertEquals(3, analyzer.getWordCount().get("world"));
        assertEquals(1, analyzer.getWordCount().get("code"));
    }

    @Test
    public void testEmptyInput() {
        WordAnalyzer analyzer = new WordAnalyzer(Collections.emptyList());
        assertTrue(analyzer.getWordCount().isEmpty());
        assertTrue(analyzer.getSortedWords() == null || analyzer.getSortedWords().isEmpty());
        assertTrue(analyzer.findMostFrequentWords().isEmpty());
    }

    @Test
    public void testSortingAscending() {
        List<String> words = Arrays.asList("world", "code", "home");
        WordAnalyzer analyzer = new WordAnalyzer(words);
        analyzer.sortWords("sort -a");
        assertEquals(Arrays.asList("code", "home", "world"), analyzer.getSortedWords());
    }

    @Test
    public void testSortingDescending() {
        List<String> words = Arrays.asList("world", "code", "home");
        WordAnalyzer analyzer = new WordAnalyzer(words);
        analyzer.sortWords("sort -d");
        assertEquals(Arrays.asList("world", "home", "code"), analyzer.getSortedWords());
    }

    @Test
    public void testSortingWithDuplicates() {
        List<String> words = Arrays.asList("world", "world", "code", "home", "home");
        WordAnalyzer analyzer = new WordAnalyzer(words);
        analyzer.sortWords("sort -a");
        assertEquals(Arrays.asList("code", "home", "world"), analyzer.getSortedWords());
    }

    @Test
    public void testFindMostFrequentSingle() {
        List<String> words = Arrays.asList("home", "world", "world", "code", "world");
        WordAnalyzer analyzer = new WordAnalyzer(words);
        assertEquals(Collections.singletonList("world"), analyzer.findMostFrequentWords());
    }

    @Test
    public void testFindMostFrequentMultiple() {
        List<String> words = Arrays.asList("home", "world", "home", "code", "world");
        WordAnalyzer analyzer = new WordAnalyzer(words);
        assertEquals(new HashSet<>(Arrays.asList("home", "world")), new HashSet<>(analyzer.findMostFrequentWords()));
    }
}
