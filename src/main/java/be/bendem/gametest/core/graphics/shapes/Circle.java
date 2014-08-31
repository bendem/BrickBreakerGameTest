package be.bendem.gametest.core.graphics.shapes;

import be.bendem.gametest.core.graphics.Point;
import be.bendem.gametest.core.graphics.Translatable;

import java.awt.Color;
import java.awt.Graphics;

/**
 * @author bendem
 */
public class Circle extends BaseShape implements Translatable {

    private final Point center;
    private int radius;
    private final Color color;

    public Circle(Point center, int radius) {
        this(center, radius, false);
    }

    public Circle(Point center, int radius, boolean filled) {
        this(center, radius, filled, Color.GRAY);
    }

    public Circle(Point center, int radius, boolean filled, Color color) {
        super(filled);
        this.center = center;
        this.radius = radius;
        this.color = color;
    }

    @Override
    public void draw() {
    }

    @Override
    public void translate(int x, int y) {
        center.translate(x, y);
    }

    public Point getCenter() {
        return center;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

}
