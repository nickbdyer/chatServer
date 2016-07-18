package uk.nickbdyer.chatserver;

import java.util.ArrayList;
import java.util.List;

public class ChatRoom {

    private List<String> messages;
    private List<User> users;

    public ChatRoom() {
        messages = new ArrayList<>();
        users = new ArrayList<>();
    }

    public List<String> messages() {
        return messages;
    }

    public void postMessage(String message) {
        for (User user : users) {
            user.notifyNewMessage(message);
        }
        messages.add(message);
    }

    public void addUser(User user) {
        users.add(user);
    }
}
