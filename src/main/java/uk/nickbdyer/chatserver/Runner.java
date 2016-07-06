package uk.nickbdyer.chatserver;

import java.io.PrintStream;
import java.util.Optional;

public class Runner {

    private String[] args;
    private PrintStream output;

    public Runner(String[] args, PrintStream output) {
        this.args = args;
        this.output = output;
    }

    public Optional<Integer> checkPortNumberIsPresent() {
        if (args.length != 1) {
            output.println("Usage: java -jar build/libs/chatServer.jar <port_number>");
            return Optional.empty();
        }
        try {
            return Optional.of(Integer.parseInt(args[0]));
        } catch (NumberFormatException e) {
            output.println("Incorrect port format");
            return Optional.empty();
        }
    }
}
