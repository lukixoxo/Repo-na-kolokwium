package org.example.powtorzenie;

import javafx.scene.paint.Color;

public record Dot(double x, double y, Color color, double radius) {


    static public String toMessage(double x, double y, Color color, double radius){
        String message;
        message = String.valueOf(x) + ";" + String.valueOf(y) + ';' + color.toString() + ';' + String.valueOf(radius);

        return message;
    }
    public String toMessage(){
        return String.valueOf(x) + ";" + String.valueOf(y) + ';' + color.toString() + ';' + String.valueOf(radius);
    }

    static public Dot fromMessage(String message){
        String[] elements = message.split(";");
        Dot dot = new Dot(Double.parseDouble(elements[0]), Double.parseDouble( elements[1]), Color.valueOf(elements[2]),Double.parseDouble(elements[3]));
        return dot;
    }

}
