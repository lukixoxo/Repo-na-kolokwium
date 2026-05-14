// --- Plik: ShapeDecorator.java ---

/**
 * ZADANIE 2: BAZOWY DEKORATOR
 * Implementuje Shape, ale jednocześnie w środku "trzyma" inny Shape.
 * Dzięki temu przepuszcza komendy dalej, pozwalając na modyfikacje po drodze.
 */
public class ShapeDecorator implements Shape {
    
    // Chronione pole, żeby dziedziczące po nim specyficzne dekoratory miały do niego dostęp
    protected Shape decoratedShape;

    public ShapeDecorator(Shape decoratedShape) {
        this.decoratedShape = decoratedShape;
    }

    @Override
    public String toSvg(String parameters) {
        // Domyślnie tylko podaje żądanie dalej, bez modyfikacji
        return decoratedShape.toSvg(parameters);
    }
}