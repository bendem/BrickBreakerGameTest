package be.bendem.gametest.core.graphics;

import be.bendem.gametest.utils.Tuple;

import java.awt.Point;

/**
 * @author bendem
 */
public final class Vector2D extends Tuple<Point, Point> implements Cloneable {

    public Vector2D(int x, int y) {
        this(new Point(0, 0), new Point(x, y));
    }

    public Vector2D(Point start, Point end) {
        super(start, end);
    }

    public void nullifyStart() {
        int x1 = getA().x;
        if(x1 != 0) {
            getB().x = 0 - x1 + getB().x;
        }
        int y1 = getA().y;
        if(y1 != 0) {
            getB().y = 0 - y1 + getB().y;
        }
    }

    public int getX() {
        nullifyStart();
        return getB().x;
    }

    public int getY() {
        nullifyStart();
        return getB().y;
    }

    public void setX(int x) {
        nullifyStart();
        getB().x = x;
    }

    public void setY(int y) {
        nullifyStart();
        getB().y = y;
    }

    @Override
    public Vector2D clone() {
        try {
            return (Vector2D) super.clone();
        } catch(CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

}
