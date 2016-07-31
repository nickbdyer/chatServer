package uk.nickbdyer.chatserver;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import uk.nickbdyer.chatserver.testdoubles.FaultyServerSocketStub;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.Assert.assertEquals;

public class ChatServerTest {

    private ServerSocket serverSocket;
    private ChatServer chatServer;
    private ChatRoom chatRoom;
    private ExecutorService executor;

    @Before
    public void setUp() throws IOException {
        serverSocket = new ServerSocket(4440);
        chatRoom = new ChatRoom();
        executor = Executors.newFixedThreadPool(2);
        chatServer = new ChatServer(executor, serverSocket, chatRoom);
    }

    @After
    public void tearDown() throws IOException {
        chatServer.stop();
    }

    //These tests feel integration-y. Perhaps they can be improved on when more things are tied together.

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
        ChatServer chatServer = new ChatServer(executor, faultyServerSocketStub, chatRoom);

        chatServer.awaitConnections();
    }

}
