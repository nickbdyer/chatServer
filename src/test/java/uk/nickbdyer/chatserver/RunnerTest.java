package uk.nickbdyer.chatserver;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

public class RunnerTest {

    private PrintStream out;
    private OutputStream outContent;

    @Before
    public void setUp() {
        outContent = new ByteArrayOutputStream();
        out = new PrintStream(outContent);
    }

    @After
    public void tearDown() throws IOException {
        out.close();
    }

    @Test
    public void advisesUserToPassPortAsArgumentIfNotPresent() {
        Runner runner = new Runner(new String[]{}, out);
        runner.checkPortNumberIsPresent();
        assertEquals("Usage: java -jar build/libs/chatServer.jar <port_number>\n", outContent.toString());
    }

    @Test
    public void advisesUserToPassPortAsNumberIfNotTheyPassAlphabeticalCharacters() {
        Runner runner = new Runner(new String[]{"abc"}, out);
        runner.checkPortNumberIsPresent();
        assertEquals("Incorrect port format\n", outContent.toString());
    }

    @Test
    public void returnsPortNumberIfPresent() {
        Runner runner = new Runner(new String[]{"4444"}, out);
        assertEquals(4444, runner.checkPortNumberIsPresent().get().intValue());
    }
}
