package uk.nickbdyer.chatserver;

import org.junit.Before;
import org.junit.Test;
import uk.nickbdyer.chatserver.testdoubles.FakeUser;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

public class ChatRoomTest {

    private ChatRoom chatRoom;

    @Before
    public void setUp() {
        chatRoom = new ChatRoom();
    }

    @Test
    public void newChatRoomHasNoMessages() {
        assertThat(chatRoom.messages(), is(equalTo(emptyMessageList())));
    }

    @Test
    public void keepsTrackOfPostedMessage() throws Exception {
        chatRoom.postMessage("Some Message");

        assertThat(chatRoom.messages(), hasItem("Some Message"));
    }

    @Test
    public void keepsTrackOfPostedMessages() throws Exception {
        chatRoom.postMessage("Some Message");
        chatRoom.postMessage("Another Message");

        assertThat(chatRoom.messages(), hasSize(2));
        assertThat(chatRoom.messages(), hasItem("Another Message"));
    }

    @Test
    public void postingAMessageNotifiesTheOnlyUser() {
        FakeUser user = new FakeUser();
        chatRoom.addUser(user);

        chatRoom.postMessage("Hello");

        assertThat(user.receivedMessage("Hello"), is(true));
    }

    @Test
    public void postingAMessageNotifiesBothUsers() {
        FakeUser userOne = new FakeUser();
        FakeUser userTwo = new FakeUser();
        chatRoom.addUser(userOne);
        chatRoom.addUser(userTwo);

        chatRoom.postMessage("Some Message");

        assertThat(userOne.receivedMessage("Some Message"), is(true));
        assertThat(userTwo.receivedMessage("Some Message"), is(true));
    }

    private ArrayList<String> emptyMessageList() {
        return new ArrayList<>();
    }

}
