package uk.nickbdyer.chatpackage.mockObjects;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MockServerSocket extends ServerSocket {

    public MockServerSocket() throws IOException {
    }

    @Override
    public Socket accept() {
        return new FaultyClientSocket();
    }

}
