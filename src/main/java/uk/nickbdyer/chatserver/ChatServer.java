package uk.nickbdyer.chatserver;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.Executor;

public class ChatServer {

    private final ChatRoom chatRoom;
    private final Executor executor;
    private ServerSocket serverSocket;
    private OutputStream output;
    private boolean listening;

    public ChatServer(ChatRoom chatRoom, ServerSocket serverSocket, OutputStream receivedMessage) {
        this.chatRoom = chatRoom;
        this.serverSocket = serverSocket;
        this.output = receivedMessage;
        executor = null;
    }

    public ChatServer(Executor executor, ServerSocket serverSocket, ChatRoom chatRoom) {
        this.serverSocket = serverSocket;
        this.chatRoom = chatRoom;
        this.executor = executor;
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
        listening = true;
            executor.execute(() -> {
                while (listening) {
                    awaitConnections();
                    chatRoom.addUser(new Member());
                }
            });
    }

    public void awaitConnections() {
        try {
            serverSocket.accept();
        } catch (IOException e) {
            if (e instanceof SocketException && "Socket closed".equals(e.getMessage())) {
                listening = false;
                return;
            } else {
                throw new RuntimeException(e);
            }
        }
    }

    public void stop() {
        listening = false;

        try {
            serverSocket.close();
        } catch (IOException ignored) {
        }
    }

    // new members can send messages to the chatroom
    // chatroom will broadcast those messages to all members of the chatroom

}
