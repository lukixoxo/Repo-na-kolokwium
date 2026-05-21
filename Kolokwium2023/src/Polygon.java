import java.util.List;

public class Polygon {
    private List<Point> pointList;

    public List<Point> getPointList() {
        return pointList;
    }

    public Polygon(List<Point> pointList) {
        this.pointList = pointList;
    }

    public boolean inside(Point point){
        int counter = 0; // [cite: 20]
        int n = pointList.size();

        for (int i = 0; i < n; i++) {
            Point pa = pointList.get(i);
            Point pb = pointList.get((i + 1) % n);

            if (pa.y > pb.y) {
                Point tmp = pa;
                pa = pb;
                pb = tmp;
            }

            if (pa.y < point.y && point.y < pb.y) {
                double d = pb.x - pa.x; // [cite: 25]
                double x;

                if (d == 0) {
                    x = pa.x; // [cite: 26, 27]
                } else {
                    double a = (pb.y - pa.y) / d; // [cite: 29]
                    double b = pa.y - a * pa.x;   // [cite: 30]
                    x = (point.y - b) / a;        // [cite: 31]
                }

                if (x < point.x) { // [cite: 32]
                    counter++; // [cite: 33]
                }
            }
        }
        if(counter%2 == 1){
            return true;
        }
        return false;
    }

}
