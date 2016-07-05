package uk.nickbdyer.chatserver;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import uk.nickbdyer.chatserver.mockObjects.FaultyServerSocket;
import uk.nickbdyer.chatserver.mockObjects.MockServerSocket;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ChatServerTest {

    private ServerSocket serverSocket;
    private OutputStream receivedMessage;

    @Before
    public void setUp() throws IOException {
        receivedMessage = new ByteArrayOutputStream();
        serverSocket = new ServerSocket(4440);
    }

    @After
    public void tearDown() throws IOException {
        serverSocket.close();
        receivedMessage.close();
    }

    @Test
    public void whenListeningForConnectionsTheServerSocketWillHaveAcceptCalled() throws IOException {
        MockServerSocket mockServerSocket = new MockServerSocket();
        ChatServer chatServer = new ChatServer(mockServerSocket, receivedMessage);

        chatServer.listen();

        assertTrue(mockServerSocket.hasAcceptBeenCalled);
    }

    @Test(expected=RuntimeException.class)
    public void ifTheServerSocketCannotAcceptConnectionsARunTimeExceptionWillBeThrown() throws IOException {
        FaultyServerSocket mockServerSocket = new FaultyServerSocket();
        ChatServer chatServer = new ChatServer(mockServerSocket, receivedMessage);

        chatServer.listen();
    }

    @Test
    public void aClientSocketCanConnectToTheServer() throws IOException, InterruptedException {
        Thread serverThread = new Thread(() -> {
            ChatServer chatServer = new ChatServer(serverSocket, receivedMessage);
            chatServer.listen();
        });
        serverThread.start();
        Socket clientSocket = new Socket("localhost", 4440);
        serverThread.join();

        assertTrue(clientSocket.isConnected());
    }
    
    @Test
    public void aConnectedClientCanSendAMessageToTheServer() throws InterruptedException, IOException {
        ChatServer chatServer = new ChatServer(serverSocket, receivedMessage);
        Thread listenThread = new Thread(chatServer::listen);
        listenThread.start();
        Socket clientSocket = new Socket("localhost", 4440);
        listenThread.join();

        PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);
        output.println("A Message");

        Thread receiveMessageThread = new Thread(chatServer::receiveMessage);
        receiveMessageThread.start();
        receiveMessageThread.join();

        assertEquals("A Message\n", receivedMessage.toString());
    }




}
