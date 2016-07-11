package uk.nickbdyer.chatserver;

import java.io.IOException;
import java.net.ServerSocket;

public class Main {

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(Arguments.getPort(args));
        new ChatServer(server, System.out).acceptConnections();
    }

}
