package be.bendem.gametest.core.graphics.shapes;

import be.bendem.gametest.core.graphics.GraphicObject;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 * @author bendem
 */
public class Rectangle extends Rectangle2D.Double implements GraphicObject {

    private final boolean filled;
    private final Color color;
    private final boolean breakable;

    public Rectangle(Point2D corner, int width, int height, boolean filled, Color color) {
        this(corner, width, height, filled, color, false);
    }

    public Rectangle(Point2D corner, int width, int height, boolean filled, Color color, boolean breakable) {
        // Prevents the corner from not being the upper left one
        double x = corner.getX();
        double y = corner.getY();
        if(width < 0) {
            width = -width;
            x = corner.getX() - width;
        }
        if(height < 0) {
            height = -height;
        }
        setFrame(x, y, width, height);

        this.filled = filled;
        this.color = color;
        this.breakable = breakable;
    }

    public Point2D getCorner() {
        return new Point2D.Double(getMinX(), getMinY());
    }

    @Override
    public void draw(Graphics2D graphics) {
        graphics.setColor(color);
        if(filled) {
            graphics.fill(this);
        } else {
            graphics.draw(this);
        }
    }

    @Override
    public void translate(int x, int y) {
        setFrame(new Rectangle2D.Double(getX() + x, getY() + y, getWidth(), getHeight()));
    }

    /**
     * Defines wether the object is solid or not (used in the collision checks).
     *
     * @return true if other object should collide with this one, false
     * otherwise
     */
    @Override
    public boolean isSolid() {
        return true;
    }

    /**
     * Defines wether the object is broken when intersecting with another one.
     *
     * @return true if the object breaks when colliding with another one, false
     * otherwise
     */
    @Override
    public boolean isBreakable() {
        return breakable;
    }

    @Override
    public String toString() {
        return "Rectangle{" +
            "corner=" + getCorner() +
            ", width=" + width +
            ", height=" + height +
            ", color=" + color +
            '}';
    }

}
