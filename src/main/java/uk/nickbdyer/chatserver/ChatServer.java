package uk.nickbdyer.chatserver;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {

    private ServerSocket serverSocket;
    private OutputStream output;

    public ChatServer(ServerSocket serverSocket, OutputStream receivedMessage) {
        this.serverSocket = serverSocket;
        this.output = receivedMessage;
    }

    public void listen() {
        try {
            Socket clientSocket = serverSocket.accept();
            new MessageStream(clientSocket.getInputStream(), output).send();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
