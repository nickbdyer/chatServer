package uk.nickbdyer.chatServer;

import java.io.*;
import java.util.Optional;

public class ChatRoom {

    private BufferedReader input;
    private PrintStream output;

    public ChatRoom(InputStream inputStream, OutputStream outputStream) {
        this.input = new BufferedReader(new InputStreamReader(inputStream));
        this.output = new PrintStream(outputStream);
    }

    public void sendInputToOutput() {
        try {
            Optional<String> sentence = Optional.ofNullable(input.readLine());
            output.print(sentence.orElse(""));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
