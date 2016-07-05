package uk.nickbdyer.chatserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class FaultyServerSocket extends ServerSocket {

    public boolean hasAcceptBeenCalled;

    public FaultyServerSocket() throws IOException {
        this.hasAcceptBeenCalled = false;
    }

    @Override
    public Socket accept() throws IOException {
        throw new IOException();
    }

}
