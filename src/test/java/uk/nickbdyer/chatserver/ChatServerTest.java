package uk.nickbdyer.chatserver;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import uk.nickbdyer.chatserver.testdoubles.FaultyServerSocketStub;
import uk.nickbdyer.chatserver.testdoubles.ServerSocketStub;

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
    private Socket client1;
    private Socket client2;

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
    public void aServerCanReceiveMessagesFromMultipleClients() throws IOException, InterruptedException {
        Thread serverThread = new Thread(chatServer::acceptConnections, "Server Thread");
        serverThread.setUncaughtExceptionHandler((th, ex) -> System.out.println("Uncaught exception: " + ex));
        serverThread.start();

        client1 = new Socket("localhost", 4440);
        client2 = new Socket("localhost", 4440);
        new PrintWriter(client1.getOutputStream(), true).println("Client1: Message");
        new PrintWriter(client2.getOutputStream(), true).println("Client2: Another Message");
        new PrintWriter(client1.getOutputStream(), true).println("Client1: Final Message");

        assertEquals("Client1: Message\nClient2: Another Message\n", receivedMessage.toString());
        client1.close();
        client2.close();
    }

    //Tests for Raised Exceptions

    @Test(expected = RuntimeException.class)
    public void ifTheServerSocketCannotAcceptConnectionsARunTimeExceptionWillBeThrown() throws IOException {
        FaultyServerSocketStub faultyServerSocketStub = new FaultyServerSocketStub();
        ChatServer chatServer = new ChatServer(faultyServerSocketStub, receivedMessage);

        chatServer.listen();
    }

    @Test(expected = RuntimeException.class)
    public void ifTheServerSocketCannotGetInputStreamARunTimeExceptionWillBeThrown() throws IOException {
        ServerSocketStub serverSocketStub = new ServerSocketStub();
        ChatServer chatServer = new ChatServer(serverSocketStub, receivedMessage);

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
