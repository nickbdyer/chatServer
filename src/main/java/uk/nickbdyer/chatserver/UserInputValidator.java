package uk.nickbdyer.chatserver;

import java.io.PrintStream;
import java.util.Optional;

public class UserInputValidator {

    public static Integer getPortNumber(String[] args) {
        if(args.length != 1) return 4444;
        try {
            return Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Incorrect Port Format");
        }
    }
}
