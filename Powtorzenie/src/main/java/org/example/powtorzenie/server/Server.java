package org.example.powtorzenie.server;

import javafx.scene.paint.Color;
import org.example.powtorzenie.Dot;

import javax.swing.plaf.nimbus.State;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private ServerSocket serverSocket;
    private List<ClientThread> userList = new ArrayList<>();
    Connection databaseConnection;

    public void connectToDatabase(){
        try {
            this.databaseConnection = DriverManager.getConnection("jdbc:sqlite:server_data.db");
            Statement statement = databaseConnection.createStatement();
            statement.execute("CREATE TABLE IF NOT EXISTS dot(\n" +
                    "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n" +
                    "x INTEGER NOT NULL,\n" +
                    "y INTEGER NOT NULL,\n" +
                    "color TEXT NOT NULL,\n" +
                    "radius INTEGER NOT NULL\n" +
                    ");");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveDot(Dot dot){
        double x = dot.x();
        double y = dot.y();
        Color color = dot.color();
        double radius = dot.radius();

        try {
            PreparedStatement statement = databaseConnection.prepareStatement("INSERT INTO dot (x, y, color , radius) values ( ?, ?, ?, ?)");
            statement.setDouble(1, x);
            statement.setDouble(2, y);
            statement.setString(3, color.toString());
            statement.setDouble(4, radius);
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Dot> getSavedDots(){
        List<Dot> dotList= new ArrayList<>();
        try {
            Statement statement = databaseConnection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM dot;");
            while (resultSet.next() != false){
                double x = resultSet.getDouble("x");
                double y = resultSet.getDouble("y");
                Color color = Color.valueOf(resultSet.getString("color"));
                double radius = resultSet.getDouble("radius");
                Dot dot = new Dot(x,y,color,radius);
                dotList.add(dot);
            }
            return dotList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Server(int port ) throws IOException {
        this.serverSocket = new ServerSocket(port);
        connectToDatabase();
    }

    public void listen() throws IOException {
        while (true){
            Socket clientSocket = serverSocket.accept();
            ClientThread clientThread = new ClientThread(this,clientSocket);
            clientThread.start();
            this.userList.add(clientThread);
            List<Dot> dotList = getSavedDots();
            for(Dot d : dotList){
                String message = d.toMessage();
                clientThread.send(message);
            }
        }
    }

    public void broadcast(String message) throws IOException {
        for(ClientThread clientThread : userList){
            clientThread.send(message);
        }
        Dot dot = Dot.fromMessage(message);
        saveDot(dot);
    }


}
