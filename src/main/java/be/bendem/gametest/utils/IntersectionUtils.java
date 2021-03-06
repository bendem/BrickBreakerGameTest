package be.bendem.gametest.utils;

import be.bendem.gametest.core.graphics.GraphicObject;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author bendem
 */
public class IntersectionUtils {

    public static Collection<Point2D> intersectPoints(GraphicObject object, Ellipse2D circle) {
        if(object instanceof Line2D) {
            return intersectPoints((Line2D) object, circle);
        }
        return intersectPoints(object.getBounds2D(), circle);
    }

    public static Collection<Point2D> intersectPoints(Rectangle2D rectangle, Ellipse2D circle) {
        Set<Point2D> points = new HashSet<>();
        getRectangleLines(rectangle).forEach(line -> points.addAll(intersectPoints(line, circle)));
        return points;
    }

    private static Collection<Point2D> intersectPoints(Line2D line, Ellipse2D circle) {
        double baX = line.getP2().getX() - line.getP1().getX();
        double baY = line.getP2().getY() - line.getP1().getY();
        double caX = circle.getCenterX() - line.getP1().getX();
        double caY = circle.getCenterY() - line.getP1().getY();
        double radius = circle.getWidth() / 2;

        double a = baX * baX + baY * baY;
        double bBy2 = baX * caX + baY * caY;
        double c = caX * caX + caY * caY - radius * radius;

        double pBy2 = bBy2 / a;
        double q = c / a;

        double disc = pBy2 * pBy2 - q;
        if (disc < 0) {
            return Collections.emptyList();
        }
        // if disc == 0 ... dealt with later
        double tmpSqrt = Math.sqrt(disc);
        double abScalingFactor1 = -pBy2 + tmpSqrt;
        double abScalingFactor2 = -pBy2 - tmpSqrt;

        Point2D p1 = new Point2D.Double(line.getP1().getX() - baX * abScalingFactor1, line.getP1().getY() - baY * abScalingFactor1);
        if(!isPointOnLine(line, p1)) {
            p1 = null;
        }
        if (disc == 0) { // abScalingFactor1 == abScalingFactor2
            return CollectionUtils.setOfNotNull(p1);
        }
        Point2D p2 = new Point2D.Double(line.getP1().getX() - baX * abScalingFactor2, line.getP1().getY() - baY * abScalingFactor2);
        if(!isPointOnLine(line, p2)) {
            p2 = null;
        }
        return CollectionUtils.setOfNotNull(p1, p2);
    }

    public static boolean isPointOnLine(Line2D line, Point2D point) {
        if(line.getX1() == line.getX2() && line.getX1() == point.getX()) {
            return point.getY() > line.getY1() && point.getY() < line.getY2();
        }

        if(line.getY1() == line.getY2() && line.getY1() == point.getY()) {
            return point.getX() > line.getX1() && point.getX() < line.getX2();
        }

        // Line is not horizontal nor vertical, I'm not even trying
        return line.contains(point);
    }

    public static Collection<Line2D> getRectangleLines(Rectangle2D rectangle) {
        HashSet<Line2D> lines = new HashSet<>();
        Point2D topLeft = new Point2D.Double(rectangle.getMinX(), rectangle.getMinY());
        Point2D topRight = new Point2D.Double(rectangle.getMaxX(), rectangle.getMinY());
        Point2D bottomLeft = new Point2D.Double(rectangle.getMinX(), rectangle.getMaxY());
        Point2D bottomRight = new Point2D.Double(rectangle.getMaxX(), rectangle.getMaxY());

        lines.add(new Line2D.Double(topLeft, topRight));
        lines.add(new Line2D.Double(topLeft, bottomLeft));
        lines.add(new Line2D.Double(topRight, bottomRight));
        lines.add(new Line2D.Double(bottomLeft, bottomRight));

        return lines;
    }

}
