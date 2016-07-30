package uk.nickbdyer.chatserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(Arguments.getPort(args));
        new ChatServer(Executors.newCachedThreadPool(),server, new ChatRoom()).start();
    }

}
