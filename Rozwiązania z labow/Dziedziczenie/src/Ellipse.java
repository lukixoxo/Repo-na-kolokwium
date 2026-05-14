public class Ellipse extends Shape {
    private Point center;
    private double rx;
    private double ry;

    public Ellipse(Point center, double rx, double ry, Style style) {
        super(style);
        this.center = new Point(center); 
        this.rx = rx;
        this.ry = ry;
    }

    @Override
    public String toSvg() {
        return "<ellipse cx=\"" + center.getX() + "\" cy=\"" + center.getY() + 
               "\" rx=\"" + rx + "\" ry=\"" + ry + "\" " + 
               style.toSvg() + " />";
    }

    @Override
    public BoundingBox boundingBox() {
        return new BoundingBox(
            center.getX() - rx, 
            center.getY() - ry, 
            rx * 2, 
            ry * 2
        );
    }
}