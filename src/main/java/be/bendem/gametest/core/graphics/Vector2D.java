package be.bendem.gametest.core.graphics;

import be.bendem.gametest.utils.Tuple;

/**
 * @author bendem
 */
public final class Vector2D extends Tuple<Point, Point> implements Cloneable {

    public Vector2D(int x, int y) {
        this(Point.zero(), new Point(x, y));
    }

    public Vector2D(Point end) {
        this(Point.zero(), end);
    }

    public Vector2D(Point start, Point end) {
        super(start, end);
    }

    public void nullifyStart() {
        int x1 = getA().getA();
        if(x1 != 0) {
            getB().setA(0 - x1 + getB().getA());
        }
        int y1 = getA().getB();
        if(y1 != 0) {
            getB().setB(0 - y1 + getB().getB());
        }
    }

    public int getX() {
        nullifyStart();
        return getB().getA();
    }

    public int getY() {
        nullifyStart();
        return getB().getB();
    }

    public void setX(int x) {
        nullifyStart();
        getB().setA(x);
    }

    public void setY(int y) {
        nullifyStart();
        getB().setB(y);
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
