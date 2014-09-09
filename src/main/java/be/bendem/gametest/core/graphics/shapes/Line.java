package be.bendem.gametest.core.graphics.shapes;

import be.bendem.gametest.core.graphics.Drawable;
import be.bendem.gametest.core.graphics.Point;
import be.bendem.gametest.core.graphics.Translatable;

import java.awt.Color;
import java.awt.Graphics;

/**
 * @author bendem
 */
public class Line implements Drawable, Translatable {

    private final Point start;
    private final Point end;
    private final Color color;

    public Line(Point start, Point end) {
        this(start, end, Color.DARK_GRAY);
    }

    public Line(Point start, Point end, Color color) {
        this.start = start;
        this.end = end;
        this.color = color;
    }

    public Point getStart() {
        return start;
    }

    public Point getEnd() {
        return end;
    }

    @Override
    public void draw(Graphics graphics) {
        graphics.setColor(color);
        graphics.drawLine(start.getA(), start.getB(), end.getA(), end.getB());
    }

    @Override
    public void translate(int x, int y) {
        start.translate(x, y);
        end.translate(x, y);
    }

}
