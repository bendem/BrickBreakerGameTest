package be.bendem.gametest.core.graphics.shapes;

import be.bendem.gametest.core.graphics.GraphicObject;
import be.bendem.gametest.core.graphics.Point;

import java.awt.Color;
import java.awt.Graphics;

/**
 * @author bendem
 */
public class Line implements GraphicObject {

    private final Point start;
    private final Point end;
    private final Color color;
    private boolean solid;

    public Line(Point start, Point end) {
        this(start, end, false);
    }

    public Line(Point start, Point end, boolean solid) {
        this(start, end, Color.DARK_GRAY, solid);
    }

    public Line(Point start, Point end, Color color, boolean solid) {
        this.start = start;
        this.end = end;
        this.color = color;
        this.solid = solid;
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

    @Override
    public Rectangle getBoundingBox() {
        return new Rectangle(start, end.getA(), end.getB());
    }

    public boolean isSolid() {
        return solid;
    }

    public void setSolid(boolean solid) {
        this.solid = solid;
    }

}
