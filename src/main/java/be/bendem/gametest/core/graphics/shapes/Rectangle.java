package be.bendem.gametest.core.graphics.shapes;

import be.bendem.gametest.core.graphics.Point;
import be.bendem.gametest.core.graphics.Translatable;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.*;

import java.awt.Color;
import java.awt.Graphics;
import java.nio.FloatBuffer;

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
        // Prevents the corner from not being the upper left one
        if(width < 0) {
            width = -width;
            corner.setA(corner.getA() - width);
        }
        if(height < 0) {
            height = -height;
            corner.setB(corner.getB() - height);
        }

        this.corner = corner;
        this.width = width;
        this.height = height;
        this.color = color;
    }

    public Point getCorner() {
        return corner;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public void draw() {
        FloatBuffer vertices = BufferUtils.createFloatBuffer(8);
        vertices.put(new float[] {
            corner.getA(),         corner.getB(),
            corner.getA() + width, corner.getB(),
            corner.getA(),         corner.getB() + height,
            corner.getA() + width, corner.getB() + height
        }).rewind();

        GL11.glBegin(GL11.GL_TRIANGLES);
            GL11.glVertexPointer(8, 1, vertices);
        GL11.glEnd();

/*
        shader = new ShaderProgram();

        // Create a VAO
        GL20.glGenVertexArrays();
        glBindVertexArray(vaoID);

        // Create a VBO
        vboID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);

        // Set the pointers in the VAO
        glVertexAttribPointer(0, 2, GL_FLOAT, false, 0, 0);

        // Unbind the VAO
        glBindVertexArray(0);*/
    }

    @Override
    public void translate(int x, int y) {
        corner.translate(x, y);
    }

}
