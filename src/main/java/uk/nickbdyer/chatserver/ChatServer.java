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

    public void listen() {
        try {
            clientSocket = serverSocket.accept();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void receiveMessage() {
        try {
            ChatRoom chatRoom = new ChatRoom(clientSocket.getInputStream(), output);
            chatRoom.sendInputToOutput();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
