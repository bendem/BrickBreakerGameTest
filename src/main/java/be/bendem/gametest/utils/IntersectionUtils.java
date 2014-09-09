package be.bendem.gametest.utils;

import be.bendem.gametest.core.graphics.Point;
import be.bendem.gametest.core.graphics.shapes.Circle;
import be.bendem.gametest.core.graphics.shapes.Line;
import be.bendem.gametest.core.graphics.shapes.Rectangle;

/**
 * TODO Check stuff in http://docs.oracle.com/javase/8/docs/api/java/awt/geom/Area.html
 *
 * @author bendem
 */
public class IntersectionUtils {

    public static boolean doIntersect(Rectangle rect, Circle circle) {
        return doIntersect(rect, getBoundingBox(circle));/*

        Point circleDistance = new Point(
            Math.abs(circle.getCenter().getA() - rect.getCorner().getA()),
            Math.abs(circle.getCenter().getB() - rect.getCorner().getB())
        );

        if(circleDistance.getA() > rect.getWidth()/2 + circle.getRadius()
                || circleDistance.getB() > rect.getHeight()/2 + circle.getRadius()) {
            return false;
        }

        if (circleDistance.getA() <= rect.getWidth()/2
                || circleDistance.getB() <= rect.getHeight()/2) {
            return true;
        }

        int cornerDistance = circleDistance.getA() - rect.getWidth()/2^2 + circleDistance.getB() - rect.getHeight()/2^2;

        return cornerDistance <= (circle.getRadius()^2);*/
    }

    public static boolean doIntersect(Rectangle rekt, Line line) {
        return doIntersect(rekt, new Rectangle(line.getStart(), line.getEnd().getA(), line.getEnd().getB()));
    }

    public static boolean doIntersect(Rectangle rekt1, Rectangle rekt2) {
        int x1, x2, w1, w2, y1, y2, h1, h2;
        x1 = rekt1.getCorner().getA();
        y1 = rekt1.getCorner().getB();
        x2 = rekt2.getCorner().getA();
        y2 = rekt2.getCorner().getB();
        w1 = rekt1.getWidth();
        w2 = rekt2.getWidth();
        h1 = rekt1.getHeight();
        h2 = rekt2.getHeight();

        return !(x1 + w1 < x2 || x2 + w2 < x1 || y1 + h1 < y2 || y2 + h2 < y1);
    }

    public static Rectangle getBoundingBox(Circle circle) {
        int radius = circle.getRadius();
        Point corner = new Point(circle.getCenter().getA() - radius, circle.getCenter().getB() - radius);
        return new Rectangle(corner, radius *2, radius *2);
    }

}
