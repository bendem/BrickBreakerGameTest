package be.bendem.gametest.core.graphics;

import be.bendem.gametest.utils.Tuple;

/**
 * @author bendem
 */
public final class Point extends Tuple<Integer, Integer> implements Translatable, Cloneable {

    public Point(int x, int y) {
        super(x, y);
    }

    @Override
    public void translate(int x, int y) {
        setA(getA() + x);
        setB(getB() + y);
    }

    @Override
    public Vector2D clone() {
        try {
            return (Vector2D) super.clone();
        } catch(CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    public static Point zero() {
        return new Point(0, 0);
    }

}
