package org.example;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    ServerSocket serverSocket;
    List<ClientThread> clients = new ArrayList<>();

    public Server(int port) {
        try {
            this.serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void run(){
        Socket clientSocket;
        while (true){
            try {
                clientSocket = serverSocket.accept();
                ClientThread clientThread = new ClientThread(clientSocket, this);
                clients.add(clientThread);
                clientThread.start();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void broadcast(String message){
        for(ClientThread cl : clients){
            cl.send(message);
        }

    }

}
