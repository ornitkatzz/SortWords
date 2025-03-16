package tests;

import com.sortwords.UserInputHandler;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

public class UserInputHandlerTest {

    private String simulateUserInput(String input) {
        InputStream originalSystemIn = System.in;  
        try {
            ByteArrayInputStream testInput = new ByteArrayInputStream(input.getBytes());
            System.setIn(testInput);  
            UserInputHandler handler = new UserInputHandler();
            return handler.getSortingOrder();
        } finally {
            System.setIn(originalSystemIn);  
        }
    }

    @Test
    public void testValidInputAscending() {
        String input = "sort -a\n";  
        String result = simulateUserInput(input);
        assertEquals("sort -a", result, "Expected 'sort -a' but got a different result.");
    }

    @Test
    public void testValidInputDescending() {
        String input = "sort -d\n";
        String result = simulateUserInput(input);
        assertEquals("sort -d", result, "Expected 'sort -d' but got a different result.");
    }

    @Test
    public void testInvalidThenValidInput() {
        String input = "wrong input\nsort -a\n"; 
        String result = simulateUserInput(input);
        assertEquals("sort -a", result, "Expected 'sort -a' after invalid input.");
    }

    @Test
    public void testHandlesSpaces() {
        String input = "  sort -a  \n"; 
        String result = simulateUserInput(input);
        assertEquals("sort -a", result, "Expected 'sort -a' with spaces handled correctly.");
    }

    @Test
    public void testCaseInsensitivity() {
        String input = "Sort -D\n"; 
        String result = simulateUserInput(input);
        assertEquals("sort -d", result, "Expected 'sort -d' with case insensitivity.");
    }

    @Test
    public void testMultipleInvalidInputsBeforeValid() {
        String input = "wrong\ninvalid\nsort -d\n";  
        String result = simulateUserInput(input);
        assertEquals("sort -d", result, "Expected 'sort -d' after multiple invalid attempts.");
    }
}
