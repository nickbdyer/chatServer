package uk.nickbdyer.chatserver.mockObjects;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

class MockClientSocket extends Socket {

    MockClientSocket() {}

    @Override
    public InputStream getInputStream() throws IOException {
        throw new IOException();
    }
}
