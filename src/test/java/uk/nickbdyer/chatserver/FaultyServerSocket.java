package uk.nickbdyer.chatserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

class FaultyServerSocket extends ServerSocket {

    FaultyServerSocket() throws IOException {
    }

    @Override
    public Socket accept() throws IOException {
        throw new IOException();
    }

}
