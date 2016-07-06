package uk.nickbdyer.chatpackage;

import java.io.IOException;
import java.net.ServerSocket;

public class Main {

    public static void main(String[] args) throws IOException {
        ChatServer chatServer = new ChatServer(new ServerSocket(Integer.parseInt(args[0])), System.out);
        chatServer.listen();
        chatServer.receiveMessage();
    }

}
