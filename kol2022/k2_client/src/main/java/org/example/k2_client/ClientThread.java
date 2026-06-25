package org.example.k2_client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.time.Clock;
import java.time.DateTimeException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ClientThread extends Thread  {
    Socket socket;
    private Controller controller;

    public ClientThread(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void run(){
        try {
            socket = new Socket("localhost", 5000);
            InputStream inputStream = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            String word;
            while ((word = br.readLine()) != null){
                LocalTime time = LocalTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                String displayMessage = time.format(formatter) + " " + word;
                controller.receiveWord(displayMessage);

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



}
