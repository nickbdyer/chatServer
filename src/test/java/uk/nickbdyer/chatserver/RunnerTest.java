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
        Runner.getPortNumber(new String[]{}, out);
        assertEquals("Usage: java -jar build/libs/chatServer.jar <port_number>\n", outContent.toString());
    }

    @Test
    public void advisesUserToPassPortAsNumberIfNotTheyPassAlphabeticalCharacters() {
        Runner.getPortNumber(new String[]{"abc"}, out);
        assertEquals("Incorrect port format\n", outContent.toString());
    }

    @Test
    public void returnsPortNumberIfPresent() {
        int portNumber = Runner.getPortNumber(new String[]{"4444"}, out).get();
        assertEquals(4444, portNumber);
    }
}
