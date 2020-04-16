import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Point;
import java.util.*;
import javax.swing.JPanel;
import java.awt.Graphics2D;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.RenderingHints;

public class Display extends JPanel {

    JPanel thisPanel = this;
    MousePaintingListener listener = new MousePaintingListener();
    int sectors = 16;
    boolean sectorsDrawn = true;

    Line tempLine = new Line(true, Color.WHITE, 4);
    ArrayList<Line> lines = new ArrayList<Line>();

    public Display() {

        setBackground(Color.DARK_GRAY);
        addMouseListener(listener);
        addMouseMotionListener(listener);
        setPreferredSize(new Dimension(Frame.WIDTH, Frame.HEIGHT));
    }

    public void paintComponent(Graphics g) {

        // initialise variables used in this method

        double angle = 360.0 / sectors;
        double radian = angle * Math.PI / 180;
        double centreX = this.getWidth() / 2;
        double centreY = this.getHeight() / 2;

        Point2D centralPoint = new Point2D.Double(centreX, centreY);
        Point2D outerPoint = new Point2D.Double(centreX, centreY - 1000);

        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // checks to see if the sectors are to be drawn

        if (sectorsDrawn == true) {

            for (int i = 0; i < sectors; i++) {

                g2d.setColor(Color.WHITE);
                g2d.draw(new Line2D.Double(centralPoint, outerPoint));
                g2d.rotate(radian, centreX, centreY);
            }
        }

        // draws the lines from the arraylist of lines

        for (int i = 0; i < lines.size(); i++) {
            for (int k = 0; k < sectors; k++) {

                ArrayList<Point> points = lines.get(i).getPoint();

                for (int j = 0; j < points.size() - 1; j++) {

                    // each point is iterated over and a line drawn between the current line and the
                    // next

                    Point prevPoint = points.get(j);
                    Point currentPoint = points.get(j + 1);

                    g2d.setColor(lines.get(i).penColor);
                    g2d.setStroke(lines.get(i).penStroke);
                    g2d.draw(new Line2D.Double(prevPoint, currentPoint));

                    // if the line is reflected, the reflections are drawn

                    if (lines.get(i).isReflected = true)
                        g2d.draw(new Line2D.Double(this.getWidth() - prevPoint.getX(), prevPoint.getY(),
                                this.getWidth() - currentPoint.getX(), currentPoint.getY()));
                }

                // the entire graphics is rotated by the angle corresponding to the number of
                // sectors chosen

                g2d.rotate(radian, centreX, centreY);
            }
        }

        // this method draws the lines on the display as they are being drawn, rather
        // than drawing stored lines

        for (int i = 0; i < tempLine.point.size() - 1; i++) {
            for (int j = 0; j < sectors; j++) {

                Point prevPoint = tempLine.point.get(i);
                Point currentPoint = tempLine.point.get(i + 1);

                g2d.setColor(tempLine.getPenColor());
                g2d.setStroke(tempLine.getPenStroke());
                g2d.draw(new Line2D.Double(prevPoint, currentPoint));

                // if the line is reflected, the reflections are drawn

                if (tempLine.isReflected = true)
                    g2d.draw(new Line2D.Double(this.getWidth() - prevPoint.getX(), prevPoint.getY(),
                            this.getWidth() - currentPoint.getX(), currentPoint.getY()));

                // the entire graphics is rotated by the angle corresponding to the number of
                // sectors chosen

                g2d.rotate(radian, centreX, centreY);
            }
        }
    }

    // custom mouse listeners

    class MousePaintingListener implements MouseListener, MouseMotionListener {

        public void mouseDragged(MouseEvent e) {

            // prevents drawing if there is no line to draw
            // stores the x and y coordinates in the temporary line

            if (!(tempLine.equals(null))) {
                tempLine.addPoint(new Point(e.getX(), e.getY()));
                thisPanel.repaint();
            }
        }

        public void mousePressed(MouseEvent e) {

            // adds points to the line when the mouse is pressed

            tempLine.addPoint(new Point(e.getX(), e.getY()));
            thisPanel.repaint();
        }

        public void mouseReleased(MouseEvent e) {

            // adds a copy of the temp line to the array of lines thus storing all the lines
            // data
            // the array of points in the temporary line is cleared

            lines.add(tempLine.clone());
            tempLine.point.clear();
        }

        @Override
        public void mouseMoved(MouseEvent e) {
        }

        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
    }
}