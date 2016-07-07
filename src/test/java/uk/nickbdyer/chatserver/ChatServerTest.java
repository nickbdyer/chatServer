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
        Thread serverThread = new Thread(chatServer::listen);
        serverThread.start();

        Socket clientSocket = new Socket("localhost", 4440);
        sendMessageFromClientToServer(clientSocket, "");
        serverThread.join();

        assertTrue(clientSocket.isConnected());
    }

    @Test
    public void aConnectedClientCanSendAMessageToTheServer() throws InterruptedException, IOException {
        makeSocketConnectionAndSendMessage("A Message");

        assertEquals("A Message\n", receivedMessage.toString());
    }

    @Test
    public void aConnectedClientCanSendMultipleMessagesToTheServer() throws InterruptedException, IOException {
        makeSocketConnectionAndSendMessage("A Message\nAnother Message");

        assertEquals("A Message\nAnother Message\n", receivedMessage.toString());
    }

    @Test
    public void aServerCanReceiveMessagesFromMulitpleClients() throws IOException, InterruptedException {
        Thread serverThread = new Thread(chatServer::acceptConnections, "Server Thread");
        serverThread.setUncaughtExceptionHandler((th, ex) -> System.out.println("Uncaught exception: " + ex));
        serverThread.start();

        sendTwoMessagesFromTwoDifferentSockets();

        assertEquals("Client1: Message\nClient2: Another Message\n", receivedMessage.toString());
    }

    private void sendTwoMessagesFromTwoDifferentSockets() throws InterruptedException {
        Thread clientThreads = new Thread(() -> {
            try {
                sendMessageFromClientToServer(new Socket("localhost", 4440), "Client1: Message");
                sendMessageFromClientToServer(new Socket("localhost", 4440), "Client2: Another Message");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        );
        clientThreads.start();
        clientThreads.join();
    }

    //Tests for Raised Exceptions

    @Test(expected = RuntimeException.class)
    public void ifTheServerSocketCannotAcceptConnectionsARunTimeExceptionWillBeThrown() throws IOException {
        FaultyServerSocket faultyServerSocket = new FaultyServerSocket();
        ChatServer chatServer = new ChatServer(faultyServerSocket, receivedMessage);

        chatServer.listen();
    }

    @Test(expected = RuntimeException.class)
    public void ifTheServerSocketCannotGetInputStreamARunTimeExceptionWillBeThrown() throws IOException {
        MockServerSocket mockServerSocket = new MockServerSocket();
        ChatServer chatServer = new ChatServer(mockServerSocket, receivedMessage);

        chatServer.listen();
    }

    private void makeSocketConnectionAndSendMessage(String message) throws IOException, InterruptedException {
        Thread serverThread = new Thread(chatServer::listen);
        serverThread.start();
        sendMessageFromClientToServer(new Socket("localhost", 4440), message);
        serverThread.join();
    }

    private void sendMessageFromClientToServer(Socket clientSocket, String message) throws IOException {
        PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);
        output.println(message);
        output.close();
    }

}
