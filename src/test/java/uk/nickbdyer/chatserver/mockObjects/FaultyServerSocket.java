package uk.nickbdyer.chatserver.mockObjects;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class FaultyServerSocket extends ServerSocket {

    public FaultyServerSocket() throws IOException {
    }

    @Override
    public Socket accept() throws IOException {
        throw new IOException();
    }

}
