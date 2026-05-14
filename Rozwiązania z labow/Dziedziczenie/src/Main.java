public class Main {
    public static void main(String[] args) {
        SvgScene scene = new SvgScene();
        
        // 1. Zwykły wielokąt domyślnie przezroczysty
        Point[] points = { new Point(50, 10), new Point(150, 10), new Point(100, 100) };
        Polygon triangle = new Polygon(points);
        scene.addShape(triangle);

        // 2. Kwadrat wygenerowany z metody wytwórczej
        Segment diagonal = new Segment(new Point(10, 150), new Point(110, 250));
        Style blueStyle = new Style("blue", "red", 3.0);
        Polygon square = Polygon.square(diagonal, blueStyle);
        scene.addShape(square);

        // 3. Elipsa
        Point center = new Point(250, 150);
        Style greenStyle = new Style("green", "black", 1.5);
        Ellipse ellipse = new Ellipse(center, 50, 80, greenStyle);
        scene.addShape(ellipse);

        scene.save("dziedziczenie_obrazek.svg");
    }
}