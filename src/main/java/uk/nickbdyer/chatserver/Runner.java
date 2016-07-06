package uk.nickbdyer.chatserver;

import java.io.PrintStream;
import java.util.Optional;

public class Runner {

    public static Optional<Integer> getPortNumber(String[] args, PrintStream output) {
        try {
            return Optional.of(Integer.parseInt(args[0]));
        } catch (NumberFormatException e) {
            output.println("Incorrect port format");
        } catch (IndexOutOfBoundsException i) {
            output.println("Usage: java -jar build/libs/chatServer.jar <port_number>");
        }
        return Optional.empty();
    }
}
