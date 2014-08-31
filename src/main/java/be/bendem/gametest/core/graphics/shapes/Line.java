package be.bendem.gametest.core.graphics.shapes;

import be.bendem.gametest.core.graphics.Drawable;
import be.bendem.gametest.core.graphics.Point;
import be.bendem.gametest.core.graphics.Translatable;

/**
 * @author bendem
 */
public class Line implements Drawable, Translatable {

    private final Point start;
    private final Point end;

    public Line(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public void draw() {
    }

    @Override
    public void translate(int x, int y) {
        start.translate(x, y);
        end.translate(x, y);
    }

}
