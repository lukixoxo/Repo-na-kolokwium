// --- Plik: SolidFilledPolygon.java ---

/**
 * ZADANIE 1: Klasa reprezentująca z góry pomalowany wielokąt.
 * Ilustruje ślepy zaułek dziedziczenia (omówiony na początku).
 */
public class SolidFilledPolygon extends Polygon {
    private String color;

    public SolidFilledPolygon(Vec2[] points, String color) {
        super(points);
        this.color = color;
    }

    @Override
    public String toSvg(String parameters) {
        // Dodajemy nasz parametr fill do innych parametrów (np. tych przekazanych wyżej).
        String newParameters = String.format("fill=\"%s\" %s", 
                color, 
                parameters != null ? parameters : "");
                
        // Wywołujemy toSvg z klasy nadrzędnej (Polygon), przekazując połączone parametry.
        return super.toSvg(newParameters);
    }
}