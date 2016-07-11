package uk.nickbdyer.chatserver.testdoubles;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

class FaultyClientSocketStub extends Socket {

    @Override
    public InputStream getInputStream() throws IOException {
        throw new IOException();
    }

}
