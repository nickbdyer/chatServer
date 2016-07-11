package uk.nickbdyer.chatserver;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import uk.nickbdyer.chatserver.testdoubles.UnReadableInputStreamStub;

import java.io.*;

import static org.junit.Assert.assertEquals;

public class ChatRoomTest {

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
        ChatRoom chatRoom = new ChatRoom(in, out);

        chatRoom.sendInputToOutput();

        assertEquals("", out.toString());
    }

    @Test
    public void outputsANewLineWhenInputIsEmptyStringWithNewLine() {
        InputStream in = new ByteArrayInputStream("\n".getBytes());
        ChatRoom chatRoom = new ChatRoom(in, out);

        chatRoom.sendInputToOutput();

        assertEquals("\n", out.toString());
    }

    @Test
    public void outputsHelloWhenTheInputIsHello() {
        InputStream in = new ByteArrayInputStream("Hello".getBytes());
        ChatRoom chatRoom = new ChatRoom(in, out);

        chatRoom.sendInputToOutput();

        assertEquals("Hello\n", out.toString());
    }
    
    @Test
    public void outputsHiWhenTheInputIsHi() {
        InputStream in = new ByteArrayInputStream("Hi".getBytes());
        ChatRoom chatRoom = new ChatRoom(in, out);

        chatRoom.sendInputToOutput();

        assertEquals("Hi\n", out.toString());
    }

    @Test(expected=RuntimeException.class)
    public void throwsExceptionIfInputStreamIsUnreadable() {
        InputStream in = new UnReadableInputStreamStub();
        ChatRoom chatRoom = new ChatRoom(in, out);

        chatRoom.sendInputToOutput();
    }

}
