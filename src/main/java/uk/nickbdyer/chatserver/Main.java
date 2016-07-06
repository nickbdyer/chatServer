package uk.nickbdyer.chatserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Optional;

public class Main {

    public static void main(String[] args) throws IOException {
        Optional<Integer> portNumber = Runner.getPortNumber(args, System.out);
        if(!portNumber.isPresent()) System.exit(1);

        ChatServer chatServer = new ChatServer(new ServerSocket(portNumber.get()), System.out);
        chatServer.listen();
        chatServer.receiveMessage();
    }

}
