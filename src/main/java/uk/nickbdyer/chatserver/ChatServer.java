package uk.nickbdyer.chatserver;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {

    private ServerSocket serverSocket;
    private Socket clientSocket;
    private OutputStream output;

    public ChatServer(ServerSocket serverSocket, OutputStream receivedMessage) {
        this.serverSocket = serverSocket;
        this.output = receivedMessage;
    }

    public void acceptConnections() {
        while (true) {
           listen();
        }
    }

    public void listen() {
        try {
            clientSocket = serverSocket.accept();
            new ChatRoom(clientSocket.getInputStream(), output).sendInputToOutput();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
