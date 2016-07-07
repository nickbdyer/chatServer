package uk.nickbdyer.chatserver;

import java.io.IOException;
import java.net.ServerSocket;

public class Main {

    public static void main(String[] args) throws IOException {
        new ChatServer(new ServerSocket(UserInputValidator.getPortNumber(args)), System.out).listen();
    }

}
