package uk.nickbdyer.chatserver;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import uk.nickbdyer.chatserver.testdoubles.FaultyServerSocketStub;
import uk.nickbdyer.chatserver.testdoubles.ServerSocketStub;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
    public void aConnectedClientCanSendAMessageToTheServer() throws InterruptedException, IOException {
        makeSocketConnectionAndSendMessage("A Message");

        assertEquals("A Message\n", receivedMessage.toString());
    }

    @Test
    public void aConnectedClientCanSendMultipleMessagesToTheServer() throws InterruptedException, IOException {
        makeSocketConnectionAndSendMessage("A Message\nAnother Message");

        assertEquals("A Message\nAnother Message\n", receivedMessage.toString());
    }
    

    @Ignore
    @Test
    public void multipleClientsCanConnectToTheServer() throws IOException {
        ChatServer chatServer = new ChatServer(new ServerSocket(4444), receivedMessage);
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(chatServer::listen);

        Socket client1 = createClient();
        PrintWriter output = new PrintWriter(client1.getOutputStream(), true);
        output.println("A Message");

        Socket client2 = createClient();
        PrintWriter output2 = new PrintWriter(client2.getOutputStream(), true);
        output2.println("Another Message");

        output.println("Final Message");

        executorService.shutdown();

        assertEquals("A Message\nAnother Message\nFinal Message", receivedMessage.toString());
    }


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

    private Socket createClient() throws IOException {
        return new Socket("localhost", 4444);
    }

}
