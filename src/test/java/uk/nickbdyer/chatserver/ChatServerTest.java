package uk.nickbdyer.chatserver;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static org.junit.Assert.assertTrue;

public class ChatServerTest {

    private ServerSocket serverSocket;

    @Before
    public void setUp() throws IOException {
        serverSocket = new ServerSocket(4440);
    }

    @After
    public void tearDown() throws IOException {
        serverSocket.close();
    }

    @Test
    public void whenListeningForConnectionsTheServerSocketWillHaveAcceptCalled() throws IOException {
        MockServerSocket mockServerSocket = new MockServerSocket();
        ChatServer chatServer = new ChatServer(mockServerSocket);

        chatServer.listen();

        assertTrue(mockServerSocket.hasAcceptBeenCalled);
    }

    @Test(expected=RuntimeException.class)
    public void ifTheServerSocketCannotAcceptConnectionsARunTimeExceptionWillBeThrown() throws IOException {
        FaultyServerSocket mockServerSocket = new FaultyServerSocket();
        ChatServer chatServer = new ChatServer(mockServerSocket);

        chatServer.listen();
    }
    
    @Test
    public void aClientSocketCanConnectToTheServer() throws IOException, InterruptedException {
        Thread serverThread = new Thread(() -> {
            ChatServer chatServer = new ChatServer(serverSocket);
            chatServer.listen();
        });
        serverThread.start();
        Socket clientSocket = new Socket("localhost", 4440);
        serverThread.join();

        assertTrue(clientSocket.isConnected());
    }


}
