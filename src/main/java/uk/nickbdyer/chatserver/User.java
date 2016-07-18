package uk.nickbdyer.chatserver;

import java.io.*;

public class User {

    private BufferedReader input;
    private PrintWriter output;

    public User(InputStream input, OutputStream output) {
        this.input = new BufferedReader(new InputStreamReader(input));
        this.output = new PrintWriter(output, true);
    }

    public void sendMessages() {
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
