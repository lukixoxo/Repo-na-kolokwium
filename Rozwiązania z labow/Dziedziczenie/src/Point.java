public class Point {
    private double x;
    private double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Point() {
        this(0.0, 0.0);
    }

    public Point(Point other) {
        this.x = other.x;
        this.y = other.y;
    }

    public double getX() { return x; }
    public double getY() { return y; }

    public void setX(double x) { this.x = x; }
    public void setY(double y) { this.y = y; }

    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    public String toSvg() {
        return "<circle cx=\"" + x + "\" cy=\"" + y + "\" r=\"5\" fill=\"black\" />";
    }

    public void translate(double dx, double dy) {
        this.x += dx;
        this.y += dy;
    }

    public Point translated(double dx, double dy) {
        return new Point(this.x + dx, this.y + dy);
    }
}