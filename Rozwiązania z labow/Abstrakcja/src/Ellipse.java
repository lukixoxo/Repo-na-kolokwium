// --- Plik: Ellipse.java ---

public class Ellipse implements Shape {
    private Vec2 center;
    private double rx, ry;

    public Ellipse(Vec2 center, double rx, double ry) {
        this.center = center;
        this.rx = rx;
        this.ry = ry;
    }

    @Override
    public String toSvg(String parameters) {
        // Używamy Locale.US, aby liczby zmiennoprzecinkowe miały kropkę zamiast przecinka
        return String.format(java.util.Locale.US, "<ellipse cx=\"%f\" cy=\"%f\" rx=\"%f\" ry=\"%f\" %s />",
                center.x(), center.y(), rx, ry, 
                parameters != null ? parameters : "");
    }
}