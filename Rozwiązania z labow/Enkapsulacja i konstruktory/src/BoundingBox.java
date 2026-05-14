// --- Plik: BoundingBox.java ---

/**
 * REKORD BOUNDING BOX (Pudełko ograniczające/obrys)
 * Rekordy (wprowadzone w nowszych wersjach Javy) to prosty sposób na klasy,
 * które służą wyłącznie do przechowywania danych. Automatycznie generują gettery
 * oraz metody toString(), equals() i hashCode().
 */
public record BoundingBox(double x, double y, double width, double height) {
    // ZADANIE 7: Definicja rekordu. 
    // To wszystko! Kompilator "pod spodem" dopisze resztę kodu za nas.
}