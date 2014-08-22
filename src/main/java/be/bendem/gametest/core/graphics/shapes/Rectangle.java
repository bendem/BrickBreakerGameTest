package be.bendem.gametest.core.graphics.shapes;

import be.bendem.gametest.core.graphics.Point;
import be.bendem.gametest.core.graphics.Translatable;
import be.bendem.gametest.core.logging.Logger;

import java.awt.Color;
import java.awt.Graphics;

/**
 * @author bendem
 */
public class Rectangle extends BaseShape implements Translatable {

    private final Point corner;
    private int width;
    private int height;
    private final Color color;

    public Rectangle(Point corner1, Point corner2) {
        this(corner1, corner2, false);
    }

    public Rectangle(Point corner1, Point corner2, boolean filled) {
        this(corner1, corner2, filled, Color.WHITE);
    }

    public Rectangle(Point corner1, Point corner2, boolean filled, Color color) {
        this(corner1, corner2.getA() - corner1.getA(), corner2.getB() - corner1.getB(), filled, color);
    }

    public Rectangle(Point corner, int width, int height) {
        this(corner, width, height, false);
    }

    public Rectangle(Point corner, int width, int height, boolean filled) {
        this(corner, width, height, filled, Color.WHITE);
    }

    public Rectangle(Point corner, int width, int height, boolean filled, Color color) {
        super(filled);
        // TODO Choose between w -= w and w = Math.abs(w)
        if(width < 0) {
            width -= width;
            corner.setA(corner.getA() - width);
        }
        if(height < 0) {
            height = Math.abs(height);
            corner.setB(corner.getB() - height);
        }
        this.corner = corner;
        this.width = width;
        this.height = height;
        this.color = color;
    }

    @Override
    public void draw(Graphics graphics) {
        graphics.setColor(color);
        Logger.debug("Drawing a rectangle from " + corner + ", width: " + width + ", height: " + height);
        if(filled) {
            graphics.fillRect(corner.getA(), corner.getB(), width, height);
        } else {
            graphics.drawRect(corner.getA(), corner.getB(), width, height);
        }
    }

    @Override
    public void translate(int x, int y) {
        corner.translate(x, y);
    }

}