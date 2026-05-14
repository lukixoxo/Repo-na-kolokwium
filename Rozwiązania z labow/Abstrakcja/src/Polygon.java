// --- Plik: Polygon.java ---

public class Polygon implements Shape {
    private Vec2[] points;

    public Polygon(Vec2[] points) {
        this.points = points; // W ramach uproszczenia przypisujemy tablicę bezpośrednio
    }

    @Override
    public String toSvg(String parameters) {
        StringBuilder pointsString = new StringBuilder();
        for (Vec2 p : points) {
            pointsString.append(p.x()).append(",").append(p.y()).append(" ");
        }
        
        // ZADANIE 1: Zmieniony format wstawiania do SVG. Wklejamy punkty i otrzymane z zewnątrz parametry.
        // Jeśli parametrów nie ma (są null), wklejamy pusty znak.
        return String.format("<polygon points=\"%s\" %s />", 
                pointsString.toString().trim(), 
                parameters != null ? parameters : "");
    }
}