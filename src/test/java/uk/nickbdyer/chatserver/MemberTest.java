package uk.nickbdyer.chatserver;

import org.junit.Test;
import uk.nickbdyer.chatserver.testdoubles.FakeUser;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.Collections;

import static java.util.Collections.singletonList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MemberTest {

    @Test
    public void aMemberCanSendAMessageToTheChatRoom() {
        Member nick = new Member();
        ChatRoom chatRoom = new ChatRoom();

        nick.sendMessageToRoom("Hi everyone!", chatRoom);

        assertEquals(singletonList("Hi everyone!"), chatRoom.messages());
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

    @Test
    public void aMemberCanSendAMessageToTheChatRoomFromTheUserInput() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        ByteArrayInputStream inContent = new ByteArrayInputStream("Hello".getBytes());
        ChatRoom chatRoom = new ChatRoom();
        Member nick = new Member(inContent, outContent);

        nick.sendAndReceiveMessages(chatRoom);

        assertEquals(singletonList("Hello"), chatRoom.messages());
    }

    @Test
    public void aMemberCanSendMultipleMessagesToTheChatRoomFromTheUserInput() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        ByteArrayInputStream inContent = new ByteArrayInputStream("Hello\nHow are you?\n".getBytes());
        ChatRoom chatRoom = new ChatRoom();
        Member nick = new Member(inContent, outContent);

        nick.sendAndReceiveMessages(chatRoom);

        assertEquals(Arrays.asList("Hello", "How are you?"), chatRoom.messages());
    }

}
