package uk.nickbdyer.chatserver;

import java.io.IOException;
import java.net.ServerSocket;

public class Main {

    public static void main(String[] args) throws IOException {
        ChatServer chatServer = new ChatServer(new ServerSocket(UserInputValidator.getPortNumber(args)), System.out);
        chatServer.listen();
        chatServer.receiveMessage();
    }

}
