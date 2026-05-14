// --- Plik: StrokeShapeDecorator.java ---

/**
 * ZADANIE 3: DEKORATOR OBRYSU
 * Dodaje kolorową ramkę/obrys wokół dowolnego kształtu.
 */
public class StrokeShapeDecorator extends ShapeDecorator {
    private String color;
    private double width;

    public StrokeShapeDecorator(Shape decoratedShape, String color, double width) {
        super(decoratedShape);
        this.color = color;
        this.width = width;
    }

    @Override
    public String toSvg(String parameters) {
        // Doklejamy stroke i stroke-width. Locale.US pilnuje kropek dziesiętnych w width.
        String newParameters = String.format(java.util.Locale.US, "stroke=\"%s\" stroke-width=\"%f\" %s", 
                color, width, 
                parameters != null ? parameters : "");
                
        return decoratedShape.toSvg(newParameters);
    }
}