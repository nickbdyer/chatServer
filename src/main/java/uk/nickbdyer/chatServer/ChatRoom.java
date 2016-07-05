package uk.nickbdyer.chatServer;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

public class ChatRoom {

    private InputStream input;
    private PrintStream output;

    public ChatRoom(InputStream inputStream, OutputStream outputStream) {
        this.input = inputStream;
        this.output = new PrintStream(outputStream);
    }

    public int numberOfSentences() {
        return 0;
    }

    public void sendInputToOutput() {
        output.print("Hello");
    }
}
