// --- Plik: Segment.java ---

/**
 * KLASA SEGMENT (Odcinek)
 * Zabezpieczona przed przypadkową zmianą jego punktów "z zewnątrz".
 */
public class Segment {
    
    // ZADANIE 2: Zmiana widoczności na 'private'.
    private Point p1;
    private Point p2;

    // ZADANIE 2: Konstruktor odcinka, od razu uodporniony na modyfikacje z zewnątrz!
    public Segment(Point p1, Point p2) {
        // Wykorzystujemy konstruktor kopiujący klasy Point.
        // Dzięki temu odcinek tworzy WŁASNE kopie punktów. Jeśli ktoś na zewnątrz
        // programu nagle zmieni współrzędne oryginalnego p1, nasz odcinek się nie zmieni.
        this.p1 = new Point(p1);
        this.p2 = new Point(p2);
    }

    // ZADANIE 2: Akcesory dla pół klasy
    public Point getP1() { return p1; }
    public Point getP2() { return p2; }

    // ZADANIE 2: Metoda toString() zwracająca informacje o punktach (końcach odcinka).
    public String toString() {
        return "Segment[" + p1.toString() + " -> " + p2.toString() + "]";
    }

    public double length() {
        // Musieliśmy użyć getterów, ponieważ pola 'x' i 'y' w klasie Point stały się prywatne!
        double xDiff = Math.pow(p2.getX() - p1.getX(), 2);
        double yDiff = Math.pow(p2.getY() - p1.getY(), 2);
        return Math.sqrt(xDiff + yDiff);
    }

    public static Segment longestSegment(Segment[] segments) {
        if (segments == null || segments.length == 0) {
            return null;
        }
        Segment longest = segments[0];
        for (Segment s : segments) {
            if (s != null && s.length() > longest.length()) {
                longest = s;
            }
        }
        return longest;
    }
}