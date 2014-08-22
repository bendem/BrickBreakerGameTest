package be.bendem.gametest.core.graphics.shapes;

import be.bendem.gametest.core.graphics.Drawable;
import be.bendem.gametest.core.graphics.Point;
import be.bendem.gametest.core.graphics.Translatable;

import java.awt.Graphics;

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
    public void draw(Graphics graphics) {
        graphics.drawLine(start.getA(), start.getB(), end.getA(), end.getB());
    }

    @Override
    public void translate(int x, int y) {
        start.translate(x, y);
        end.translate(x, y);
    }

}
