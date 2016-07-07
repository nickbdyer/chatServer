package uk.nickbdyer.chatserver;

import java.io.*;

public class ChatRoom {

    private BufferedReader input;
    private PrintWriter output;

    public ChatRoom(InputStream input, OutputStream output) {
        this.input = new BufferedReader(new InputStreamReader(input));
        this.output = new PrintWriter(output, true);
    }

    public void sendInputToOutput() {
        try {
            String sentence;
            while ((sentence = input.readLine()) != null) {
                output.println(sentence);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
