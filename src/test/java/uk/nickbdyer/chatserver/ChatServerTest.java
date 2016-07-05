package uk.nickbdyer.chatserver;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertTrue;

public class ChatServerTest {

    @Test
    public void whenListeningForConnectionsTheServerSocketWillHaveAcceptCalled() throws IOException {
        MockServerSocket mockServerSocket = new MockServerSocket();
        ChatServer chatServer = new ChatServer(mockServerSocket);

        chatServer.listen();

        assertTrue(mockServerSocket.hasAcceptBeenCalled);
    }

    @Test(expected=RuntimeException.class)
    public void ifTheServerSocketCannotAcceptConnectionsARunTimeExceptionWillBeThrown() {
        FaultyServerSocket mockServerSocket = null;
        try {
            mockServerSocket = new FaultyServerSocket();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ChatServer chatServer = new ChatServer(mockServerSocket);

        chatServer.listen();
    }



}
