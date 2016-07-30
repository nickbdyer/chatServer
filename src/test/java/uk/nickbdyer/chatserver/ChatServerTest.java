package uk.nickbdyer.chatserver;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import uk.nickbdyer.chatserver.testdoubles.FaultyServerSocketStub;
import uk.nickbdyer.chatserver.testdoubles.ServerSocketStub;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ChatServerTest {

    private ServerSocket serverSocket;
    private OutputStream receivedMessage;
    private ChatServer chatServer;
    private ChatRoom chatRoom;

    @Before
    public void setUp() throws IOException {
        receivedMessage = new ByteArrayOutputStream();
        serverSocket = new ServerSocket(4440);
        chatRoom = new ChatRoom();
        chatServer = new ChatServer(chatRoom, serverSocket, receivedMessage);
    }

    @After
    public void tearDown() throws IOException {
        chatServer.stop();
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

    @Test
    public void whenASocketConnectionIsMadeOneMemberIsAddedToTheChatRoom() throws IOException, InterruptedException {
        ChatRoom chatRoom = new ChatRoom();
        Executor executor = Executors.newFixedThreadPool(2);
        ChatServer chatServer = new ChatServer(executor, serverSocket, chatRoom);
        chatServer.start();

        new Socket("localhost", 4440);

        Thread.sleep(50);
        assertEquals(1, chatRoom.numberOfUsers());
    }

    @Test
    public void whenTwoSocketConnectionsAreMadeTwoMembersAreAddedToTheChatRoom() throws IOException, InterruptedException {
        ChatRoom chatRoom = new ChatRoom();
        Executor executor = Executors.newFixedThreadPool(2);
        ChatServer chatServer = new ChatServer(executor, serverSocket, chatRoom);
        chatServer.start();

        new Socket("localhost", 4440);
        new Socket("localhost", 4440);

        Thread.sleep(50);
        assertEquals(2, chatRoom.numberOfUsers());
    }

    @Test(expected = RuntimeException.class)
    public void ifTheServerSocketCannotAcceptConnectionsARunTimeExceptionWillBeThrown() throws IOException {
        FaultyServerSocketStub faultyServerSocketStub = new FaultyServerSocketStub();
        ChatServer chatServer = new ChatServer(chatRoom, faultyServerSocketStub, receivedMessage);

        chatServer.listen();
    }

    @Test(expected = RuntimeException.class)
    public void ifTheServerSocketCannotGetInputStreamARunTimeExceptionWillBeThrown() throws IOException {
        ServerSocketStub serverSocketStub = new ServerSocketStub();
        ChatServer chatServer = new ChatServer(chatRoom, serverSocketStub, receivedMessage);

        chatServer.listen();
    }

    private void makeSocketConnectionAndSendMessage(String message) throws IOException, InterruptedException {
        Thread serverThread = new Thread(chatServer::listen, "Server Listening Thread");
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
