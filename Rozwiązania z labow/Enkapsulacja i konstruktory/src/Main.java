// --- Plik: Main.java ---

/**
 * KLASA MAIN (Główna)
 * Zaktualizowana tak, aby testowała wszystkie powyższe klasy i zadania.
 */
public class Main {
    
    public static void main(String[] args) {
        
        System.out.println("--- TEST: Zadanie 1 i 2 (Enkapsulacja i Wrażliwość) ---");
        // Tworzymy punkty używając NOWEGO konstruktora
        Point p1 = new Point(10.0, 10.0);
        Point p2 = new Point(50.0, 50.0);
        
        // Tworzymy odcinek. Dzięki głębokiej kopii w konstruktorze, odcinek jest "bezpieczny"
        Segment segment = new Segment(p1, p2);
        System.out.println("Odcinek przed modyfikacją p1: " + segment);
        
        // Próbujemy popsuć odcinek z zewnątrz, modyfikując punkt bazowy
        p1.setX(999.0);
        System.out.println("Odcinek po zmianie p1 na 999.0: " + segment);
        System.out.println("(Jak widać, odcinek się nie zmienił! Sukces!)");

        System.out.println("\n--- TEST: Zadanie 3 i 4 (Wielokąt) ---");
        Point[] pointsArray = {
            new Point(10, 10),
            new Point(100, 10),
            new Point(50, 100)
        };
        
        Polygon triangle = new Polygon(pointsArray);
        System.out.println("Stworzony wielokąt: " + triangle);
        System.out.println("Obrys (BoundingBox): " + triangle.boundingBox());

        System.out.println("\n--- TEST: Zadanie 5, 6 i 8 (Scena SVG i Zapis) ---");
        SvgScene scene = new SvgScene();
        
        // Dodajemy pierwszy trójkąt
        scene.addPolygon(triangle);
        
        // Tworzymy drugi trójkąt (przesunięty) i dodajemy do sceny
        Point[] otherPointsArray = {
            new Point(150, 10),
            new Point(200, 100),
            new Point(100, 100)
        };
        Polygon triangle2 = new Polygon(otherPointsArray);
        scene.addPolygon(triangle2);

        // Zapisujemy scenę do pliku "obrazek.svg".
        // Otwórz go w przeglądarce (np. Chrome), żeby zobaczyć rysunek!
        scene.save("obrazek.svg");
    }
}