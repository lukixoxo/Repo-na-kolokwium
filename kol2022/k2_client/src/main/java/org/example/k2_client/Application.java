package org.example.k2_client;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);

        Controller controller = fxmlLoader.getController();
        ClientThread clientThread = new ClientThread(controller);
        clientThread.start();

        stage.setTitle("Kolokwium");
        stage.setScene(scene);
        stage.show();




    }
}
