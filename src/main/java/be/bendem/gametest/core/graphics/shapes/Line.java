package be.bendem.gametest.core.graphics.shapes;

import be.bendem.gametest.core.graphics.GraphicObject;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Line2D;

/**
 * @author bendem
 */
public class Line extends Line2D.Double implements GraphicObject {

    private final Color color;
    private final boolean solid;

    public Line(Point start, Point end, boolean solid) {
        this(start, end, Color.DARK_GRAY, solid);
    }

    public Line(Point start, Point end, Color color, boolean solid) {
        super(start, end);
        this.color = color;
        this.solid = solid;
    }

    @Override
    public void draw(Graphics2D graphics) {
        graphics.setColor(color);
        graphics.drawLine((int) getX1(), (int) getY1(), (int) getX2(), (int) getY2());
    }

    @Override
    public void translate(double x, double y) {
        setLine(x1 + x, y1 + y, x2 + x, y2 + y);
    }

    public boolean isSolid() {
        return solid;
    }

    @Override
    public boolean isBreakable() {
        return false;
    }

    @Override
    public String toString() {
        return "Line{" +
            "start=" + getP1() +
            ", end=" + getP2() +
            ", color=" + color +
            ", solid=" + solid +
            '}';
    }

}
