// --- Plik: TransformationDecorator.java ---

/**
 * ZADANIE 4: DEKORATOR TRANSFORMACJI + WZORZEC BUILDER
 * Odpowiada za przesunięcia, obroty i skalowanie obiektu na płótnie SVG.
 */
public class TransformationDecorator extends ShapeDecorator {
    private String transform;

    // Konstruktor jest prywatny lub pakietowy, bo chcemy używać wyłącznie Buildera do jego tworzenia!
    public TransformationDecorator(Shape decoratedShape, String transform) {
        super(decoratedShape);
        this.transform = transform;
    }

    @Override
    public String toSvg(String parameters) {
        String newParameters = String.format("transform=\"%s\" %s", 
                transform, 
                parameters != null ? parameters : "");
        return decoratedShape.toSvg(newParameters);
    }

    /**
     * WZORZEC BUILDER (Budowniczy)
     * Pomaga ułożyć skomplikowany, długi łańcuch transformacji krok po kroku.
     */
    public static class Builder {
        private String transform = ""; // Pusty napis na start

        public Builder translate(Vec2 translation) {
            // Dodajemy przesunięcie np: "translate(10.5 20.0) "
            transform += String.format(java.util.Locale.US, "translate(%f %f) ", translation.x(), translation.y());
            return this; // Zwracamy 'this' co pozwala na tzw. "chaining" (łączenie metod kropką)
        }

        public Builder rotate(float angle, Vec2 center) {
            // Dodajemy obrót wokół podanego punktu
            transform += String.format(java.util.Locale.US, "rotate(%f %f %f) ", angle, center.x(), center.y());
            return this;
        }

        public Builder scale(Vec2 scaleFactor) {
            transform += String.format(java.util.Locale.US, "scale(%f %f) ", scaleFactor.x(), scaleFactor.y());
            return this;
        }

        // Metoda kończąca pracę - bierze zebrane transformacje, obudowuje kształt dekoratorem i go oddaje.
        public TransformationDecorator build(Shape shape) {
            // trim() ucina ostatnią, niepotrzebną spację
            return new TransformationDecorator(shape, transform.trim());
        }
    }
}