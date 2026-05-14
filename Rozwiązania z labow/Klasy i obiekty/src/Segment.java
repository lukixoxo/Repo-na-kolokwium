// --- Plik: Segment.java ---

/**
 * KLASA SEGMENT (Odcinek)
 * Odcinek to nic innego jak linia łącząca dwa punkty.
 * Skoro mamy już klasę Point, możemy jej tu użyć jak klocków Lego do budowy Odcinka.
 */
public class Segment {
    
    // ZADANIE 4: Dwa publiczne obiekty Point.
    // To są nasze dwa końce odcinka. Zauważ, że typem zmiennej jest klasa 'Point', 
    // którą napisaliśmy w poprzednim pliku!
    public Point p1;
    public Point p2;

    // ZADANIE 4: Metoda length() zwracająca długość odcinka.
    // Używamy wzoru z matematyki na odległość dwóch punktów (Twierdzenie Pitagorasa).
    public double length() {
        // Math.pow podnosi do potęgi. Liczymy: (x2 - x1)^2 + (y2 - y1)^2
        double xDiff = Math.pow(p2.x - p1.x, 2);
        double yDiff = Math.pow(p2.y - p1.y, 2);
        
        // Math.sqrt wyciąga pierwiastek kwadratowy z całości.
        return Math.sqrt(xDiff + yDiff);
        
        // TIP na przyszłość: Java ma do tego gotową metodę, można by to zapisać w jednej linijce:
        // return Math.hypot(p2.x - p1.x, p2.y - p1.y);
    }

    // ZADANIE 5: Metoda statyczna szukająca najdłuższego segmentu z tablicy.
    // 'static' oznacza, że możemy użyć tej metody bez budowania konkretnego obiektu Segment.
    // Wystarczy napisać Segment.longestSegment(...). Działa to trochę jak uniwersalne narzędzie.
    public static Segment longestSegment(Segment[] segments) {
        // Jeśli tablica jest pusta, to nie ma najdłuższego odcinka. Oddajemy pustkę (null).
        if (segments.length == 0) {
            return null;
        }

        // Zakładamy na start, że pierwszy odcinek (indeks 0) jest najdłuższy.
        Segment longest = segments[0];

        // Pętla "for-each". Czytamy: "dla każdego pojedynczego Segmentu (nazwijmy go 's') w tablicy 'segments'..."
        for (Segment s : segments) {
            // Jeśli obecnie sprawdzany odcinek 's' jest dłuższy od naszego "króla" ('longest')...
            if (s.length() > longest.length()) {
                // ...to on zajmuje tron i staje się nowym najdłuższym odcinkiem.
                longest = s;
            }
        }

        // Na koniec zwracamy zwycięzcę.
        return longest;
    }
}