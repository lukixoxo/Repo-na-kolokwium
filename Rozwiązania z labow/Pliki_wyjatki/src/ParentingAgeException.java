// --- Plik: ParentingAgeException.java ---

/**
 * ZADANIE 6: Wyjątek rzucany, gdy rodzic ma mniej niż 15 lat lub nie żyje w momencie narodzin dziecka.
 */
public class ParentingAgeException extends Exception {
    public ParentingAgeException(String message) {
        super(message);
    }
}