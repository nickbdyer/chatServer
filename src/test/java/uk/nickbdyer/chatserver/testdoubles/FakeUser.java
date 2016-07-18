package uk.nickbdyer.chatserver.testdoubles;

import uk.nickbdyer.chatserver.User;

import java.util.ArrayList;

public class FakeUser implements User {

    private final ArrayList<String> receivedMessages;

    public FakeUser() {
        receivedMessages = new ArrayList<>();
    }

    public boolean receivedMessage(String message) {
        return receivedMessages.contains(message);
    }

    @Override
    public void notifyNewMessage(String message) {
        receivedMessages.add(message);
    }
}
