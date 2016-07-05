package uk.nickbdyer.chatServer;

import java.io.*;

public class ChatRoom {

    private BufferedReader input;
    private PrintStream output;

    public ChatRoom(InputStream inputStream, OutputStream outputStream) {
        this.input = new BufferedReader(new InputStreamReader(inputStream));
        this.output = new PrintStream(outputStream);
    }

    public int numberOfSentences() {
        return 0;
    }

    public void sendInputToOutput() {
        try {
            output.print(input.readLine());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
