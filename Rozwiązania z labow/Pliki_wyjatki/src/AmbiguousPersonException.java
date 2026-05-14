// --- Plik: AmbiguousPersonException.java ---

/**
 * ZADANIE 4: Wyjątek rzucany, gdy w pliku pojawi się więcej niż jedna osoba o takich samych personaliach.
 */
public class AmbiguousPersonException extends Exception {
    public AmbiguousPersonException(String message) {
        super(message);
    }
}