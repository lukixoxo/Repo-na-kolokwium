package org.example.powtorzenie.client;

import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.example.powtorzenie.Dot;

import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.util.function.Consumer;

public class ServerThread extends Thread{
    private Socket socket;
    private javafx.scene.canvas.Canvas canvas;
    Consumer<Dot> dotConsumer;

    public void setDotConsumer(Consumer<Dot> dotConsumer) {
        this.dotConsumer = dotConsumer;
    }

    public void run(){
        try {
            InputStream inputStream =  socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            while (true){
                String message = br.readLine();
                Platform.runLater(() -> {
                    System.out.println("Serwer przysłał: " + message);
                    Dot dot = Dot.fromMessage(message);
                    this.dotConsumer.accept(dot);

                });
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void send(String message){
        try {
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
            printWriter.println(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ServerThread(String name, int port, Canvas canvas) throws IOException {
        this.socket = new Socket(name, port);
        this.canvas = canvas;
    }
}
