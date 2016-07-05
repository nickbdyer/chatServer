package uk.nickbdyer.chatserver;

import org.junit.Test;

import java.io.*;

import static org.junit.Assert.assertEquals;

public class ChatRoomTest {
    
    @Test
    public void containsNoSentencesWhenInitialized() {
        InputStream in = new ByteArrayInputStream("".getBytes());
        OutputStream out = new ByteArrayOutputStream();
        ChatRoom chatRoom = new ChatRoom(in, out);

        chatRoom.sendInputToOutput();

        assertEquals("\n", out.toString());
    }

    @Test
    public void outputsHelloWhenTheSentenceThatWasPassedInWasHello() {
        InputStream in = new ByteArrayInputStream("Hello".getBytes());
        OutputStream out = new ByteArrayOutputStream();
        ChatRoom chatRoom = new ChatRoom(in, out);

        chatRoom.sendInputToOutput();

        assertEquals("Hello\n", out.toString());
    }
    
    @Test
    public void outputsHiWhenTheSentenceThatWasPassedInWasHi() {
        InputStream in = new ByteArrayInputStream("Hi".getBytes());
        OutputStream out = new ByteArrayOutputStream();
        ChatRoom chatRoom = new ChatRoom(in, out);

        chatRoom.sendInputToOutput();

        assertEquals("Hi\n", out.toString());
    }

    @Test(expected=RuntimeException.class)
    public void throwsExceptionIfInputStreamCannotRead() {
        InputStream in = new UnReadableInputStream();
        OutputStream out = new ByteArrayOutputStream();
        ChatRoom chatRoom = new ChatRoom(in, out);

        chatRoom.sendInputToOutput();
    }

}
