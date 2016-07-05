package uk.nickbdyer.chatserver;

import java.io.IOException;
import java.net.ServerSocket;

public class ChatServer {

    private ServerSocket serverSocket;

    public ChatServer(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public void listen() {
        try {
            serverSocket.accept();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
