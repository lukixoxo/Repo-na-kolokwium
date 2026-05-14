// --- Plik: NegativeLifespanException.java ---

/**
 * ZADANIE 3: Wyjątek rzucany, gdy data śmierci jest wcześniejsza niż data narodzin.
 */
public class NegativeLifespanException extends Exception {
    public NegativeLifespanException(String message) {
        super(message);
    }
}