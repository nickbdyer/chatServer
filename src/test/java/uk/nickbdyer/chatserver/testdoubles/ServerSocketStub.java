package uk.nickbdyer.chatserver.testdoubles;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSocketStub extends ServerSocket {

    public ServerSocketStub() throws IOException {
    }

    @Override
    public Socket accept() {
        return new FaultyClientSocketStub();
    }

}
