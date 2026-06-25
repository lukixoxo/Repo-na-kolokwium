package org.example.powtorzenie;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import org.example.powtorzenie.client.ServerThread;
import org.example.powtorzenie.server.Server;

import java.awt.*;
import java.io.IOException;
import java.lang.reflect.Field;

public class Controller {

    @FXML
    private ColorPicker colorPicker;

    @FXML
    private Slider radiusSlider;

    @FXML
    private Canvas canvas;

    @FXML
    private TextField portField;

    @FXML
    private TextField addressField;

    private ServerThread serverThread;

    private Server server;

    public Controller() {
    }

    public Controller(ServerThread serverThread, Server server) {
        this.serverThread = serverThread;
        this.server = server;
        serverThread.setDotConsumer(dot -> {
            double x = dot.x();
            double y = dot.y();
            Color color = dot.color();
            double radius = dot.radius();
            GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
            graphicsContext.setFill(color);
            graphicsContext.fillOval(x - radius, y - radius, radius * 2, radius * 2);
        });
    }

    @FXML
    public void onStartServerClicked(ActionEvent event) {
        int port = java.lang.Integer.parseInt(portField.getText());
        new Thread(() -> {
            try {
                server = new Server(port);
                server.listen();

            } catch (IOException e) {
                e.printStackTrace(); // Wypisze błąd w konsoli, jeśli coś pójdzie nie tak
            }
        }).start();
    }

    @FXML
    public void onConnectClicked(ActionEvent event) {
        String name = addressField.getText();
        int port = Integer.parseInt(portField.getText());
        try {
            serverThread = new ServerThread(name,port,canvas);
            serverThread.setDotConsumer(dot -> {
                double x = dot.x();
                double y = dot.y();
                Color color = dot.color();
                double radius = dot.radius();
                GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
                graphicsContext.setFill(color);
                graphicsContext.fillOval(x - radius, y - radius, radius * 2, radius * 2);
            });
            serverThread.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void onMouseClicked(MouseEvent event) {
        double x = event.getX();
        double y = event.getY();
        Color color = colorPicker.getValue();
        double radius = radiusSlider.getValue();
        Dot dot = new Dot(x,y,color,radius);
        String message = Dot.toMessage(x,y,color,radius);

        if (serverThread != null){
            serverThread.send(message);
        }
    }
}