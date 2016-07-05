package uk.nickbdyer.chatserver;

import java.io.*;
import java.util.Optional;

public class ChatRoom {

    private BufferedReader input;
    private PrintStream output;

    public ChatRoom(InputStream input, OutputStream output) {
        this.input = new BufferedReader(new InputStreamReader(input));
        this.output = new PrintStream(output);
    }

    public void sendInputToOutput() {
        try {
            Optional<String> sentence = Optional.ofNullable(input.readLine());
            output.println(sentence.orElse(""));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
