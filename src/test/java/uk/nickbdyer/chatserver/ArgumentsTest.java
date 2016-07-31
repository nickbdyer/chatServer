package uk.nickbdyer.chatserver;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ArgumentsTest extends Arguments {

    @Test
    public void returnsPortNumberIfPresent() {
        int portNumber = Arguments.getPort(new String[]{"5555"});
        assertEquals(5555, portNumber);
    }

    @Test
    public void returnsDefaultPortNumberIfNotPresent() {
        int portNumber = Arguments.getPort(new String[]{});
        assertEquals(4444, portNumber);
    }

    @Test(expected = RuntimeException.class)
    public void advisesUserToPassPortAsNumberIfTheyPassAlphabeticalCharacters() {
        Arguments.getPort(new String[]{"abc"});
    }
}
