public class Polygon extends Shape {
    private Point[] points;

    public Polygon(Point[] points, Style style) {
        super(style);
        this.points = new Point[points.length];
        for (int i = 0; i < points.length; i++) {
            this.points[i] = new Point(points[i]);
        }
    }

    public Polygon(Point[] points) {
        this(points, new Style("transparent", "black", 1.0));
    }

    public static Polygon square(Segment s, Style style) {
        Segment perp = s.perpendicular();
        Point[] squarePoints = { s.getP1(), perp.getP1(), s.getP2(), perp.getP2() };
        return new Polygon(squarePoints, style);
    }

    @Override
    public String toSvg() {
        StringBuilder sb = new StringBuilder();
        sb.append("<polygon points=\"");
        for (Point p : points) {
            sb.append(p.getX()).append(",").append(p.getY()).append(" ");
        }
        sb.append("\" ").append(style.toSvg()).append(" />");
        return sb.toString();
    }

    @Override
    public BoundingBox boundingBox() {
        if (points == null || points.length == 0) return new BoundingBox(0, 0, 0, 0);

        double minX = points[0].getX();
        double minY = points[0].getY();
        double maxX = points[0].getX();
        double maxY = points[0].getY();

        for (Point p : points) {
            if (p.getX() < minX) minX = p.getX();
            if (p.getX() > maxX) maxX = p.getX();
            if (p.getY() < minY) minY = p.getY();
            if (p.getY() > maxY) maxY = p.getY();
        }

        return new BoundingBox(minX, minY, maxX - minX, maxY - minY);
    }
}