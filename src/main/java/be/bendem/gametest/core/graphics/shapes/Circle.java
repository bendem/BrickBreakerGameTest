package be.bendem.gametest.core.graphics.shapes;

import be.bendem.gametest.core.graphics.GraphicObject;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

/**
 * @author bendem
 */
public class Circle extends Ellipse2D.Double implements GraphicObject {

    private final boolean filled;
    private final Color color;

    public Circle(Point2D center, int radius, boolean filled, Color color) {
        super(center.getX() - radius, center.getY() - radius, radius * 2, radius * 2);
        this.filled = filled;
        this.color = color;
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
    public void translate(double x, double y) {
        setFrame(getX() + x, getY() + y, getWidth(), getHeight());
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

}
