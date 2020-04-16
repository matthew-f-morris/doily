
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;

public class Line {

    public ArrayList<Point> point = new ArrayList<Point>();
    public Color penColor;
    public int penSize;
    public boolean isReflected;
    public BasicStroke penStroke;

    public Line(boolean reflected, Color color, int size) {

        penColor = color;
        setPenSize(size);
        setReflected(reflected);
    }

    public Color getPenColor() {
        return penColor;
    }

    public void setPenColor(Color penColor) {
        this.penColor = penColor;
    }

    public int getPenSize() {
        return penSize;
    }

    public void setPenSize(int penSize) {

        this.penSize = penSize;
        penStroke = new BasicStroke(penSize, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL);
    }

    public boolean isReflected() {
        return isReflected;
    }

    public void setReflected(boolean isReflected) {
        this.isReflected = isReflected;
    }

    public BasicStroke getPenStroke() {
        return penStroke;
    }

    public void setPenStroke(BasicStroke penStroke) {
        this.penStroke = penStroke;
    }

    public void addPoint(Point addPoint) {
        point.add(addPoint);
    }

    public ArrayList<Point> getPoint() {
        return point;
    }

    public Line clone() {

        try {

            Line newLine = new Line(this.isReflected, this.penColor, this.penSize);
            for (Point aPoint : this.point)
                newLine.point.add((Point) aPoint.clone());

            return newLine;

        } catch (Exception e) {
            System.err.println("Error: Line clone failed");
            return null;
        }
    }
}
