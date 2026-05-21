public class Point {
    public final double x;
    public final double y;

    public Point(double point1, double point2) {
        this.x = point1;
        this.y = point2;
    }

    public double distance(Point point){
        return Math.sqrt(Math.pow(point.x - this.x , 2) + Math.pow(point.y - this.y, 2));
    }

}
