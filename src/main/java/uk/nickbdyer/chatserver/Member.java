package uk.nickbdyer.chatserver;

public class Member implements User {

    @Override
    public void notifyNewMessage(String message) {

    }

    public void sendMessageToRoom(String message, ChatRoom room) {
        room.postMessage(message);
    }
}
