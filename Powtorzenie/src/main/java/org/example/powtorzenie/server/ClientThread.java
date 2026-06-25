package org.example.powtorzenie.server;

import java.io.*;
import java.net.Socket;

public class ClientThread extends Thread{
    Server server;
    Socket socket;

    public ClientThread(Server server, Socket socket) {
        this.server = server;
        this.socket = socket;
    }

    public void run(){
        try {
            InputStream inputStream = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            while (true){
                String message = br.readLine();
                if(message == null)
                    break;
                server.broadcast(message);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void send(String message) throws IOException {
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        out.println(message);
    }

}
