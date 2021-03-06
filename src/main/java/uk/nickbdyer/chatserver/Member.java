package uk.nickbdyer.chatserver;

import java.io.*;

public class Member implements User {

    private PrintWriter output;
    private BufferedReader input;

    public Member(InputStream inputStream, OutputStream outputStream) {
        input = new BufferedReader(new InputStreamReader(inputStream));
        output = new PrintWriter(outputStream, true);
    }

    public Member() {

    }

    @Override
    public void notifyNewMessage(String message) {
        output.println(message);
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
