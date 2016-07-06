package uk.nickbdyer.chatserver;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UserInputValidatorTest {

    @Test
    public void returnsPortNumberIfPresent() {
        int portNumber = UserInputValidator.getPortNumber(new String[]{"5555"});
        assertEquals(5555, portNumber);
    }

    @Test
    public void returnsDefaultPortNumberIfNotPresent() {
        int portNumber = UserInputValidator.getPortNumber(new String[]{});
        assertEquals(4444, portNumber);
    }

    @Test(expected = RuntimeException.class)
    public void advisesUserToPassPortAsNumberIfTheyPassAlphabeticalCharacters() {
        UserInputValidator.getPortNumber(new String[]{"abc"});
    }
}
