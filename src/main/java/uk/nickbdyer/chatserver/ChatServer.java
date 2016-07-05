package uk.nickbdyer.chatserver;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {

    private ServerSocket serverSocket;
    private Socket clientSocket;
    private OutputStream output;

    public static void main(String[] args) throws IOException {
        ServerSocket socket = new ServerSocket(Integer.parseInt(args[0]));
        ChatServer chatServer = new ChatServer(socket, System.out);
        chatServer.listen();
        chatServer.receiveMessage();
    }

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
