package uk.nickbdyer.chatserver;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class MemberTest {

    @Test
    public void aMemberCanSendAMessageToTheChatRoom() {
        Member nick = new Member();
        ChatRoom chatRoom = new ChatRoom();

        nick.sendMessageToRoom("Hi everyone!", chatRoom);

        assertEquals(chatRoom.messages(), Arrays.asList("Hi everyone!"));
    }
}
