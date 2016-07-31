package uk.nickbdyer.chatserver.testdoubles;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class FaultyServerSocketStub extends ServerSocket {

    public FaultyServerSocketStub() throws IOException {
    }

    @Override
    public Socket accept() throws IOException {
        throw new IOException();
    }

}
