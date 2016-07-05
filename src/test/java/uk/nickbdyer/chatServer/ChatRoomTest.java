package uk.nickbdyer.chatServer;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ChatRoomTest {
    
    @Test
    public void containsKnowSentencesWhenInitialized() {
        ChatRoom chatRoom = new ChatRoom();
        assertEquals(0, chatRoom.numberOfSentences());
    }
}
