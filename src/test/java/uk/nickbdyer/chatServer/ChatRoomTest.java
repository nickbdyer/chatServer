package uk.nickbdyer.chatserver;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import static org.junit.Assert.assertEquals;

public class ChatRoomTest {
    
    @Test
    public void containsNoSentencesWhenInitialized() {
        InputStream in = new ByteArrayInputStream("".getBytes());
        OutputStream out = new ByteArrayOutputStream();
        ChatRoom chatRoom = new ChatRoom(in, out);

        chatRoom.sendInputToOutput();

        assertEquals("", out.toString());
    }

    @Test
    public void outputsHelloWhenTheSentenceThatWasPassedInWasHello() {
        InputStream in = new ByteArrayInputStream("Hello".getBytes());
        OutputStream out = new ByteArrayOutputStream();
        ChatRoom chatRoom = new ChatRoom(in, out);

        chatRoom.sendInputToOutput();

        assertEquals("Hello", out.toString());
    }
    
    @Test
    public void outputsHiWhenTheSentenceThatWasPassedInWasHi() {
        InputStream in = new ByteArrayInputStream("Hi".getBytes());
        OutputStream out = new ByteArrayOutputStream();
        ChatRoom chatRoom = new ChatRoom(in, out);

        chatRoom.sendInputToOutput();

        assertEquals("Hi", out.toString());
    }

}
