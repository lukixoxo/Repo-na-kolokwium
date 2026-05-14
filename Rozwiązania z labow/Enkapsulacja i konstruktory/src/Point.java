// --- Plik: Point.java ---

/**
 * KLASA POINT (Punkt)
 * Rozbudowana o hermetyzację (enkapsulację) i konstruktory.
 */
public class Point {
    
    // ZADANIE 1: Zmiana widoczności na 'private' (Enkapsulacja).
    // Pola są teraz ukryte przed światem zewnętrznym. Nie można ich zmienić "bezpośrednio" np. punkt.x = 5.
    private double x;
    private double y;

    // ZADANIE 1: Konstruktor ustawiający punkt w zadanym położeniu.
    // Konstruktor to specjalna metoda uruchamiana tylko raz podczas tworzenia obiektu (słowem 'new').
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    // ZADANIE 1: Konstruktor bezargumentowy, ustawiający punkt (0, 0).
    public Point() {
        // Używamy słówka 'this()', aby wywołać nasz główny konstruktor powyżej i uniknąć powielania kodu.
        this(0.0, 0.0);
    }

    // ZADANIE 2: Konstruktor kopiujący.
    // Przyjmuje inny obiekt typu Point i kopiuje jego wartości.
    // Pozwala to na stworzenie zupełnie nowego, niezależnego punktu o takich samych współrzędnych.
    public Point(Point other) {
        this.x = other.x;
        this.y = other.y;
    }

    // ZADANIE 1: Akcesory (gettery) i Mutatory (settery).
    // Gettery (zwracają wartość) - pozwalają "odczytać" ukryte pole.
    public double getX() { return x; }
    public double getY() { return y; }

    // Settery (ustawiają wartość) - pozwalają "zmienić" ukryte pole w kontrolowany sposób.
    public void setX(double x) { this.x = x; }
    public void setY(double y) { this.y = y; }

    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    public String toSvg() {
        return "<circle cx=\"" + x + "\" cy=\"" + y + "\" r=\"5\" fill=\"black\" />";
    }

    public void translate(double dx, double dy) {
        this.x += dx;
        this.y += dy;
    }

    public Point translated(double dx, double dy) {
        // Zmiana pod ZADANIE 1 - zamiast bezargumentowego, wygodniej użyć konstruktora z argumentami!
        return new Point(this.x + dx, this.y + dy);
    }
}