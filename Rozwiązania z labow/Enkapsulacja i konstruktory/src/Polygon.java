// --- Plik: Polygon.java ---

/**
 * KLASA POLYGON (Wielokąt)
 * Składa się z tablicy punktów połączonych ze sobą.
 */
public class Polygon {
    
    // ZADANIE 3: Prywatna tablica punktów.
    private Point[] points;

    // ZADANIE 3 i 4: Konstruktor przyjmujący tablicę i wykonujący jej GŁĘBOKĄ KOPIĘ.
    public Polygon(Point[] points) {
        // Krok 1: Tworzymy nową tablicę o takiej samej długości jak ta przekazana.
        this.points = new Point[points.length];
        
        // Krok 2: Pętla przechodząca przez oryginalną tablicę.
        for (int i = 0; i < points.length; i++) {
            // Konstruktor kopiujący Point - wkładamy niezależne KLONY punktów.
            // Głęboka kopia chroni nasz wielokąt przed zmianą punktu gdzieś indziej w kodzie.
            this.points[i] = new Point(points[i]);
        }
    }

    // ZADANIE 4: Konstruktor kopiujący obiektu Polygon (Wykonuje głęboką kopię).
    public Polygon(Polygon other) {
        // Tworzymy nową tablicę o rozmiarze takim jak w kopiowanym wielokącie.
        this.points = new Point[other.points.length];
        for (int i = 0; i < other.points.length; i++) {
            // Głęboka kopia - również kopiujemy fizycznie każdy pojedynczy punkt.
            this.points[i] = new Point(other.points[i]);
        }
    }

    // ZADANIE 3: toString() 
    public String toString() {
        StringBuilder sb = new StringBuilder("Polygon[");
        for (int i = 0; i < points.length; i++) {
            sb.append(points[i].toString());
            if (i < points.length - 1) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }

    // ZADANIE 3: toSvg() - zamienia listę punktów na format odczytywany w przeglądarkach.
    public String toSvg() {
        StringBuilder sb = new StringBuilder();
        sb.append("<polygon points=\"");
        // SVG oczekuje formatu: x1,y1 x2,y2 x3,y3 ...
        for (Point p : points) {
            sb.append(p.getX()).append(",").append(p.getY()).append(" ");
        }
        sb.append("\" fill=\"none\" stroke=\"black\" />"); // Dodałem styl dla lepszej wizualizacji
        return sb.toString();
    }

    // ZADANIE 7: Zwraca obrys opisujący, ile miejsca na ekranie zajmuje dany wielokąt.
    public BoundingBox boundingBox() {
        if (points == null || points.length == 0) {
            return new BoundingBox(0, 0, 0, 0);
        }

        // Ustawiamy wartości skrajne na parametry pierwszego punktu w tablicy
        double minX = points[0].getX();
        double minY = points[0].getY();
        double maxX = points[0].getX();
        double maxY = points[0].getY();

        // Szukamy najmniejszych i największych "iksów" oraz "igreków"
        for (Point p : points) {
            if (p.getX() < minX) minX = p.getX();
            if (p.getX() > maxX) maxX = p.getX();
            if (p.getY() < minY) minY = p.getY();
            if (p.getY() > maxY) maxY = p.getY();
        }

        // X i Y to lewy-górny róg (czyli minX i minY).
        // Szerokość to różnica między prawą a lewą krawędzią. Analogicznie wysokość.
        return new BoundingBox(minX, minY, maxX - minX, maxY - minY);
    }
}