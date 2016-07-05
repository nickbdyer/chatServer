package uk.nickbdyer.chatserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

class MockServerSocket extends ServerSocket {

    boolean hasAcceptBeenCalled;

    MockServerSocket() throws IOException {
        this.hasAcceptBeenCalled = false;
    }

    @Override
    public Socket accept() {
        this.hasAcceptBeenCalled = true;
        return null;
    }

}
