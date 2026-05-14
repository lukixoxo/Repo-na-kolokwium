// --- Plik: SolidFillShapeDecorator.java ---

/**
 * ZADANIE 2: DEKORATOR WYPEŁNIENIA
 * Potrafi pomalować "od środka" (fill) dowolny kształt: Polygon, Ellipse, a nawet inny Dekorator!
 */
public class SolidFillShapeDecorator extends ShapeDecorator {
    private String color;

    public SolidFillShapeDecorator(Shape decoratedShape, String color) {
        super(decoratedShape); // Przekazujemy kształt do rodzica
        this.color = color;
    }

    @Override
    public String toSvg(String parameters) {
        // Doklejamy komendę 'fill' do otrzymanych parametrów
        String newParameters = String.format("fill=\"%s\" %s", 
                color, 
                parameters != null ? parameters : "");
                
        // Przekazujemy wzbogacony tekst z parametrami do udekorowanego kształtu
        return decoratedShape.toSvg(newParameters);
    }
}