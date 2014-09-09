package be.bendem.gametest.utils;

import be.bendem.gametest.core.graphics.Intersectable;
import be.bendem.gametest.core.graphics.shapes.Rectangle;

/**
 * TODO Check stuff in http://docs.oracle.com/javase/8/docs/api/java/awt/geom/Area.html
 *
 * @author bendem
 */
public class IntersectionUtils {

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

    public static boolean doIntersect(Intersectable o1, Intersectable o2) {
        return doIntersect(o1.getBoundingBox(), o2.getBoundingBox());
    }

}
