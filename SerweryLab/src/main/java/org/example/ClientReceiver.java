package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientReceiver extends Thread{
    Socket socket;
    ChatBox chatBox;

    public ClientReceiver(Socket socket, ChatBox chatBox) {
        this.socket = socket;
        this.chatBox = chatBox;
    }

    public void run(){
        try {
            InputStream inputStream = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            String message;
            while((message = br.readLine()) != null){
                if(message.startsWith("[BROADCAST]")){
                    chatBox.chatArea.append(message.substring(12) + '\n');
                }
                else if (message.startsWith("[ONLINE] ")) {
                    message = message.substring(9);
                    chatBox.model.addElement(message);
                }
                else if (message.startsWith("[OFFLINE] ")) {
                    message = message.substring(10);
                    chatBox.model.removeElement(message); // removeElement automatycznie usuwa dany tekst z listy!
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
