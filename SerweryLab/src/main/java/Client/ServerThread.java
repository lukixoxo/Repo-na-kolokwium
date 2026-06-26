package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;

public class ServerThread extends Thread{
    Client client;

    public ServerThread(Client client) {
        this.client = client;
    }

    public void run(){
        try {
            InputStream inputStream = client.socket.getInputStream();
            BufferedReader br = new BufferedReader( new InputStreamReader(inputStream));
            String message;
            while((message = br.readLine()) != null){
                System.out.println(message);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
