package be.bendem.gametest.core.graphics.shapes;

import be.bendem.gametest.core.graphics.Point;
import be.bendem.gametest.core.graphics.Translatable;

import java.awt.Graphics;

/**
 * @author bendem
 */
public class Circle extends BaseShape implements Translatable {

    private Point center;
    private int radius;

    public Circle(Point center, int radius) {
        this(center, radius, false);
    }

    public Circle(Point center, int radius, boolean filled) {
        super(filled);
        this.center = center;
        this.radius = radius;
    }

    @Override
    public void draw(Graphics graphics) {
        if(filled) {
            graphics.fillOval(center.getA() - radius, center.getB() - radius, radius * 2, radius * 2);
        } else {
            graphics.drawOval(center.getA() - radius, center.getB() - radius, radius * 2, radius * 2);
        }
    }

    @Override
    public void translate(int x, int y) {
        center.translate(x, y);
    }
}
