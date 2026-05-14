// --- Plik: Point.java ---

/**
 * KLASA POINT (Punkt)
 * To jest nasz przepis na stworzenie punktu na ekranie/kartce.
 * Klasa nie ma metody main, bo nie jest głównym programem, a jedynie "foremką".
 */
public class Point {
    
    // ZADANIE 1: Dwa publiczne, zmiennoprzecinkowe pola (współrzędne x i y).
    // 'public' oznacza, że każdy z zewnątrz może je zobaczyć i zmienić.
    // 'double' to typ danych - liczby z ułamkiem (zmiennoprzecinkowe).
    public double x;
    public double y;

    // ZADANIE 1: Bezargumentowa metoda toString().
    // Nadpisujemy domyślną metodę Javy, która zwykle wypisuje jakieś losowe znaki (tzw. hashcode).
    // Chcemy, żeby po wypisaniu punktu na ekran pojawiło się coś czytelnego, np. "(5.0, 3.0)".
    public String toString() {
        // Zlepiamy (konkatenujemy) tekst ze zmiennymi za pomocą znaku '+'
        return "(" + x + ", " + y + ")";
    }

    // ZADANIE 2: Metoda toSvg().
    // Zwraca kawałek tekstu, który jest kodem w języku SVG (język do rysowania grafik w internecie).
    public String toSvg() {
        // Znak \" oznacza, że chcemy wypisać prawdziwy cudzysłów w środku tekstu.
        // cx i cy to środek koła w SVG, r="5" to promień koła.
        return "<circle cx=\"" + x + "\" cy=\"" + y + "\" r=\"5\" fill=\"black\" />";
    }

    // ZADANIE 3: Metoda translate() - przesuwa obecny punkt.
    // Metoda jest typu 'void', czyli nie zwraca żadnego wyniku. Ona po prostu robi robotę.
    // Przyjmuje dwa argumenty: dx (o ile przesunąć w poziomie) i dy (o ile w pionie).
    public void translate(double dx, double dy) {
        // 'this.x' to nasze pole na górze. Powiększamy je o wartość 'dx'.
        // Zapis 'this.x += dx' to krótsza wersja 'this.x = this.x + dx'.
        this.x += dx;
        this.y += dy;
    }

    // ZADANIE 3: Metoda translated() - tworzy NOWY punkt po przesunięciu.
    // Zwróć uwagę, że ta metoda zwraca typ 'Point', a nie void.
    public Point translated(double dx, double dy) {
        // 1. Wyciągamy nową foremkę i tworzymy całkiem nowy obiekt punktu.
        Point newPoint = new Point();
        
        // 2. Ustawiamy mu współrzędne: bierzemy obecne pozycje (this.x) i dodajemy przesunięcie.
        newPoint.x = this.x + dx;
        newPoint.y = this.y + dy;
        
        // 3. Odsyłamy ten nowy, gotowy punkt. Stary punkt pozostaje nienaruszony!
        return newPoint;
    }
}