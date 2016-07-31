package uk.nickbdyer.chatserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.Executor;

public class ChatServer {

    private final ChatRoom chatRoom;
    private final Executor executor;
    private ServerSocket serverSocket;
    private boolean listening;

    public ChatServer(Executor executor, ServerSocket serverSocket, ChatRoom chatRoom) {
        this.serverSocket = serverSocket;
        this.chatRoom = chatRoom;
        this.executor = executor;
    }

    public void start() {
        listening = true;
            executor.execute(() -> {
                while (listening) {
                    Socket client = awaitConnections();
                    try {
                        if (client != null) {
                            Member newMember = new Member(client.getInputStream(), client.getOutputStream());
                            chatRoom.addUser(newMember);
                            executor.execute(() -> {
                                newMember.sendAndReceiveMessages(chatRoom);
                            });
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
    }

    public Socket awaitConnections() {
        try {
            return serverSocket.accept();
        } catch (IOException e) {
            if (e instanceof SocketException && "Socket closed".equals(e.getMessage())) {
                listening = false;
                return null;
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

}
