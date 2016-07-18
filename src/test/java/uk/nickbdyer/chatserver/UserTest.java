package uk.nickbdyer.chatserver;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import uk.nickbdyer.chatserver.testdoubles.UnReadableInputStreamStub;

import java.io.*;

import static org.junit.Assert.assertEquals;

public class UserTest {

    private OutputStream out;

    @Before
    public void setUp() {
        out = new ByteArrayOutputStream();
    }

    @After
    public void tearDown() throws IOException {
        out.close();
    }

    @Test
    public void outputsANothingWhenNothingIsSent() {
        InputStream in = new ByteArrayInputStream("".getBytes());
        User user = new User(in, out);

        user.sendMessages();

        assertEquals("", out.toString());
    }

    @Test
    public void outputsANewLineWhenInputIsEmptyStringWithNewLine() {
        InputStream in = new ByteArrayInputStream("\n".getBytes());
        User user = new User(in, out);

        user.sendMessages();

        assertEquals("\n", out.toString());
    }

    @Test
    public void outputsHelloWhenTheInputIsHello() {
        InputStream in = new ByteArrayInputStream("Hello".getBytes());
        User user = new User(in, out);

        user.sendMessages();

        assertEquals("Hello\n", out.toString());
    }
    
    @Test
    public void outputsHiWhenTheInputIsHi() {
        InputStream in = new ByteArrayInputStream("Hi".getBytes());
        User user = new User(in, out);

        user.sendMessages();

        assertEquals("Hi\n", out.toString());
    }

    @Test(expected=RuntimeException.class)
    public void throwsExceptionIfInputStreamIsUnreadable() {
        InputStream in = new UnReadableInputStreamStub();
        User user = new User(in, out);

        user.sendMessages();
    }

}
