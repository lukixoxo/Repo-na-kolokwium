import java.io.FileWriter;
import java.io.IOException;

public class SvgScene {
    private Shape[] shapes = new Shape[3];
    private int nextIndex = 0;

    public void addShape(Shape s) {
        shapes[nextIndex % shapes.length] = s;
        nextIndex++;
    }

    public String toSvg() {
        StringBuilder sb = new StringBuilder();
        for (Shape s : shapes) {
            if (s != null) {
                sb.append(s.toSvg()).append("\n");
            }
        }
        return sb.toString();
    }

    public void save(String path) {
        double totalMaxX = 0, totalMaxY = 0;
        
        for (Shape s : shapes) {
            if (s != null) {
                BoundingBox box = s.boundingBox();
                double rightEdge = box.x() + box.width();
                double bottomEdge = box.y() + box.height();
                
                if (rightEdge > totalMaxX) totalMaxX = rightEdge;
                if (bottomEdge > totalMaxY) totalMaxY = bottomEdge;
            }
        }

        try (FileWriter writer = new FileWriter(path)) {
            writer.write(String.format(java.util.Locale.US, 
                    "<svg width=\"%f\" height=\"%f\" xmlns=\"http://www.w3.org/2000/svg\">\n", 
                    totalMaxX, totalMaxY));
            writer.write(this.toSvg());
            writer.write("</svg>");
            System.out.println("Plik SVG zapisany: " + path);
        } catch (IOException e) {
            System.err.println("Błąd: " + e.getMessage());
        }
    }
}