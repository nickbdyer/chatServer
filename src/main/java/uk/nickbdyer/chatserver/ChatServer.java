package uk.nickbdyer.chatserver;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {

    private final ChatRoom chatRoom;
    private ServerSocket serverSocket;
    private OutputStream output;

    public ChatServer(ChatRoom chatRoom, ServerSocket serverSocket, OutputStream receivedMessage) {
        this.chatRoom = chatRoom;
        this.serverSocket = serverSocket;
        this.output = receivedMessage;
    }

    public ChatServer(ServerSocket serverSocket, ChatRoom chatRoom) {
        this.serverSocket = serverSocket;
        this.chatRoom = chatRoom;
    }

    public void listen() {
        try {
            Socket clientSocket = serverSocket.accept();
            chatRoom.addUser(new Member());
            new MessageStream(clientSocket.getInputStream(), output).send();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void start() {
        try {
            Socket clientSocket = serverSocket.accept();
            chatRoom.addUser(new Member());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // new server has a chatroom
    // server starts to listen
    // new socket connections are represented as new members
    // new members can send messages to the chatroom
    // chatroom will broadcast those messages to all members of the chatroom

}
