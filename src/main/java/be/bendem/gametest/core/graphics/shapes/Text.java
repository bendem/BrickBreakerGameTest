package be.bendem.gametest.core.graphics.shapes;

import be.bendem.gametest.core.graphics.GraphicObject;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 * @author bendem
 */
public class Text implements GraphicObject {

    private final String text;
    private final Point2D point;
    private final int size;
    private final boolean bold;
    private final Color color;

    public Text(String text, Point2D point, int size, boolean bold, Color color) {
        this.text = text;
        this.point = point;
        this.size = size;
        this.bold = bold;
        this.color = color;
    }

    @Override
    public void draw(Graphics2D graphics) {
        graphics.setColor(color);
        graphics.setFont(new Font(Font.MONOSPACED, bold ? Font.BOLD : Font.PLAIN, size));
        graphics.drawString(text, (float) point.getX(), (float) point.getY());
    }

    @Override
    public void translate(double x, double y) {
        point.setLocation(point.getX() + x, point.getY() + y);
    }

    /**
     * Defines wether the object is solid or not (used in the collision checks).
     *
     * @return true if other object should collide with this one, false
     * otherwise
     */
    @Override
    public boolean isSolid() {
        return false;
    }

    /**
     * Defines wether the object is broken when intersecting with another one.
     *
     * @return true if the object breaks when colliding with another one, false
     * otherwise
     */
    @Override
    public boolean isBreakable() {
        return false;
    }


    // I'm not writting that, I just need a GraphicObject
    // TODO Never write these
    @Override
    public Rectangle getBounds() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Rectangle2D getBounds2D() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean contains(double v, double v2) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean contains(Point2D point2D) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean intersects(double v, double v2, double v3, double v4) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean intersects(Rectangle2D rectangle2D) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean contains(double v, double v2, double v3, double v4) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean contains(Rectangle2D rectangle2D) {
        throw new UnsupportedOperationException();
    }

    @Override
    public PathIterator getPathIterator(AffineTransform affineTransform) {
        throw new UnsupportedOperationException();
    }

    @Override
    public PathIterator getPathIterator(AffineTransform affineTransform, double v) {
        throw new UnsupportedOperationException();
    }

}
