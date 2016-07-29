package uk.nickbdyer.chatserver;

import org.junit.Test;
import uk.nickbdyer.chatserver.testdoubles.FakeUser;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MemberTest {

    @Test
    public void aMemberCanSendAMessageToTheChatRoom() {
        Member nick = new Member();
        ChatRoom chatRoom = new ChatRoom();

        nick.sendMessageToRoom("Hi everyone!", chatRoom);

        assertEquals(chatRoom.messages(), Arrays.asList("Hi everyone!"));
    }

    @Test
    public void aMessageSentToTheChatRoomIsHeardByAnotherUser() {
        Member nick = new Member();
        FakeUser testUser = new FakeUser();
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.addUser(testUser);

        nick.sendMessageToRoom("Hi everyone!", chatRoom);

        assertTrue(testUser.receivedMessage("Hi everyone!"));
    }

}
