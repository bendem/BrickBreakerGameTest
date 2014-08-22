package be.bendem.gametest.core.graphics;

import be.bendem.gametest.utils.Tuple;

/**
 * @author bendem
 */
public final class Vector2D extends Tuple<Point, Point> implements Cloneable {

    Vector2D(Point start, Point end) {
        super(start, end);
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
