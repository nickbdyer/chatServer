package uk.nickbdyer.chatserver.mockObjects;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MockServerSocket extends ServerSocket {

    public boolean hasAcceptBeenCalled;

    public MockServerSocket() throws IOException {
        this.hasAcceptBeenCalled = false;
    }

    @Override
    public Socket accept() {
        this.hasAcceptBeenCalled = true;
        return null;
    }

}
