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
    private ChatServer chatServer;

    @Before
    public void setUp() throws IOException {
        receivedMessage = new ByteArrayOutputStream();
        serverSocket = new ServerSocket(4440);
        chatServer = new ChatServer(serverSocket, receivedMessage);
    }

    @After
    public void tearDown() throws IOException {
        serverSocket.close();
        receivedMessage.close();
    }

    @Test
    public void aClientSocketCanConnectToTheServer() throws IOException, InterruptedException {
        Socket clientSocket = openServerSocketAndMakeClientConnection();

        assertTrue(clientSocket.isConnected());
    }

    @Test
    public void aConnectedClientCanSendAMessageToTheServer() throws InterruptedException, IOException {
        Socket clientSocket = openServerSocketAndMakeClientConnection();

        sendMessageToServer(clientSocket, "A Message");

        assertEquals("A Message\n", receivedMessage.toString());
    }

    //Tests for Raised Exceptions

    @Test(expected=RuntimeException.class)
    public void ifTheServerSocketCannotAcceptConnectionsARunTimeExceptionWillBeThrown() throws IOException {
        FaultyServerSocket faultyServerSocket = new FaultyServerSocket();
        ChatServer chatServer = new ChatServer(faultyServerSocket, receivedMessage);

        chatServer.listen();
    }

    @Test(expected=RuntimeException.class)
    public void ifTheServerSocketCannotGetInputStreamARunTimeExceptionWillBeThrown() throws IOException {
        MockServerSocket mockServerSocket = new MockServerSocket();
        ChatServer chatServer = new ChatServer(mockServerSocket, receivedMessage);

        chatServer.listen();
        chatServer.receiveMessage();
    }

    private Socket openServerSocketAndMakeClientConnection() throws IOException, InterruptedException {
        Thread serverThread = new Thread(chatServer::listen);
        serverThread.start();
        Socket clientSocket = new Socket("localhost", 4440);
        serverThread.join();
        return clientSocket;
    }

    private void sendMessageToServer(Socket clientSocket, String message) throws IOException, InterruptedException {
        PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);
        Thread receiveMessageThread = new Thread(chatServer::receiveMessage);
        receiveMessageThread.start();
        output.println(message);
        receiveMessageThread.join();
    }

    // Refactor this test suite
    // Test to prompt while loop for message reading
    // Server and client stay live until client ctrl-c



}
