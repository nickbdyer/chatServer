package uk.nickbdyer.chatserver;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {

    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintStream output;
    private BufferedReader clientInput;

    public ChatServer(ServerSocket serverSocket, OutputStream receivedMessage) {
        this.serverSocket = serverSocket;
        this.output = new PrintStream(receivedMessage);
    }

    public void listen() {
        try {
            clientSocket = serverSocket.accept();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void receiveMessage() {
        try {
            clientInput = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            output.print(clientInput.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}
