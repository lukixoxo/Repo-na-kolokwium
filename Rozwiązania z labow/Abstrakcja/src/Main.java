// --- Plik: Main.java ---

public class Main {
    public static void main(String[] args) {
        
        // --- ZADANIE 1: Dziedziczenie (SolidFilledPolygon) ---
        Vec2[] polyPoints = { new Vec2(10, 10), new Vec2(100, 10), new Vec2(50, 100) };
        Shape badPolygon = new SolidFilledPolygon(polyPoints, "red");
        System.out.println("Zad 1 (Dziedziczenie): \n" + badPolygon.toSvg("") + "\n");

        
        // --- ZADANIE 2: Dekoratory (SolidFillShapeDecorator) ---
        Shape simplePolygon = new Polygon(polyPoints);
        Shape simpleEllipse = new Ellipse(new Vec2(200, 200), 50, 30);

        // Ubieramy wielokąt w kolor zielony, a elipsę w niebieski
        Shape greenPolygon = new SolidFillShapeDecorator(simplePolygon, "green");
        Shape blueEllipse = new SolidFillShapeDecorator(simpleEllipse, "blue");
        
        System.out.println("Zad 2 (Kolorowanie z Dekoratora):");
        System.out.println(greenPolygon.toSvg(""));
        System.out.println(blueEllipse.toSvg("") + "\n");


        // --- ZADANIE 3: Kolejne dekoratory nakładane na siebie (StrokeShapeDecorator) ---
        // Bierzemy wczeniej ubraną na niebiesko elipsę i dodajemy jej czerwoną ramkę o grubości 3!
        Shape framedEllipse = new StrokeShapeDecorator(blueEllipse, "red", 3.0);
        
        System.out.println("Zad 3 (Niebieska elipsa z czerwoną ramką):");
        System.out.println(framedEllipse.toSvg("") + "\n");


        // --- ZADANIE 4: TransformationDecorator i Wzorzec Builder ---
        // Stworzymy nową elipsę
        Shape movingEllipse = new Ellipse(new Vec2(50, 50), 20, 10);
        
        // Nakładamy kolor
        movingEllipse = new SolidFillShapeDecorator(movingEllipse, "yellow");
        
        // Budujemy transformację krok po kroku za pomocą Buildera
        Shape transformedEllipse = new TransformationDecorator.Builder()
                .translate(new Vec2(100, 50))
                .rotate(45, new Vec2(50, 50))
                .scale(new Vec2(1.5, 1.5))
                .build(movingEllipse); // <-- na samym końcu przekazujemy Kształt do obudowania!

        System.out.println("Zad 4 (Przesunięta, obrócona i powiększona elipsa):");
        System.out.println(transformedEllipse.toSvg(""));
    }
}