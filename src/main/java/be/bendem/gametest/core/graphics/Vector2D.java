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

    public int getX() {
        return getB().getA() - getA().getA();
    }

    public int getY() {
        return getB().getB() - getB().getA();
    }

    public void setX(int x) {
        getB().setA(getA().getA() + x);
    }

    public void setY(int y) {
        getB().setB(getA().getB() + y);
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
