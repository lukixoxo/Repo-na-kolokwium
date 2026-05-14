public class Segment {
    private Point p1;
    private Point p2;

    public Segment(Point p1, Point p2) {
        this.p1 = new Point(p1);
        this.p2 = new Point(p2);
    }

    public Point getP1() { return p1; }
    public Point getP2() { return p2; }

    public double length() {
        double xDiff = Math.pow(p2.getX() - p1.getX(), 2);
        double yDiff = Math.pow(p2.getY() - p1.getY(), 2);
        return Math.sqrt(xDiff + yDiff);
    }

    public Segment perpendicular() {
        double dx = p2.getX() - p1.getX();
        double dy = p2.getY() - p1.getY();
        
        double cx = (p1.getX() + p2.getX()) / 2.0;
        double cy = (p1.getY() + p2.getY()) / 2.0;
        
        Point np1 = new Point(cx - dy / 2.0, cy + dx / 2.0);
        Point np2 = new Point(cx + dy / 2.0, cy - dx / 2.0);
        
        return new Segment(np1, np2);
    }

    public static Segment longestSegment(Segment[] segments) {
        if (segments == null || segments.length == 0) return null;
        Segment longest = segments[0];
        for (Segment s : segments) {
            if (s != null && s.length() > longest.length()) {
                longest = s;
            }
        }
        return longest;
    }

    public String toString() {
        return "Segment[" + p1.toString() + " -> " + p2.toString() + "]";
    }
}