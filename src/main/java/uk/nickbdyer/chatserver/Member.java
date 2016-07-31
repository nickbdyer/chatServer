package uk.nickbdyer.chatserver;

import java.io.*;

public class Member implements User {

    private OutputStream output;
    private BufferedReader input;

    public Member(ByteArrayInputStream inputStream, ByteArrayOutputStream outputStream) {
        input = new BufferedReader(new InputStreamReader(inputStream));
        output = outputStream;
    }

    public Member() {

    }

    @Override
    public void notifyNewMessage(String message) {

    }

    public void sendMessageToRoom(String message, ChatRoom room) {
        room.postMessage(message);
    }

    public void sendAndReceiveMessages(ChatRoom chatRoom) {
        try {
            String sentence;
            while ((sentence = input.readLine()) != null) {
                sendMessageToRoom(sentence, chatRoom);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
