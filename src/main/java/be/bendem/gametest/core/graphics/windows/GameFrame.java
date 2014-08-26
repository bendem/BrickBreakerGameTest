package be.bendem.gametest.core.graphics.windows;

import be.bendem.gametest.GameTest;
import be.bendem.gametest.core.Killable;
import be.bendem.gametest.core.graphics.Point;
import be.bendem.gametest.core.graphics.shapes.Circle;
import be.bendem.gametest.core.graphics.shapes.Line;
import be.bendem.gametest.core.graphics.shapes.Rectangle;

import java.awt.Color;
import java.awt.Dimension;

/**
 * @author bendem
 */
public class GameFrame extends BaseFrame implements Killable {

    public static final int WIDTH = 800;
    public static final int HEIGHT = 500;

    public static final int RECTANGLE_WIDTH = 80;
    public static final int RECTANGLE_HEIGHT = 10;

    public GameFrame(GameTest game) {
        super("Game Test", game);
        panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        panel.setBackground(Color.BLACK);

        objects.add(new Line(new Point(0, HEIGHT / 2), new Point(WIDTH, HEIGHT / 2)));
        objects.add(new Line(new Point(WIDTH / 2, 0), new Point(WIDTH / 2, HEIGHT)));
    }

    @Override
    public void kill() {
        dispose();
    }

    public Rectangle createPlateform() {
        Rectangle plateform = new Rectangle(
            new Point(
                WIDTH / 2 - RECTANGLE_WIDTH / 2,
                HEIGHT - RECTANGLE_HEIGHT - 5
            ),
            RECTANGLE_WIDTH,
            RECTANGLE_HEIGHT,
            true
        );
        objects.add(plateform);
        return plateform;
    }

    public Circle createBall() {
        Circle circle = new Circle(new Point(WIDTH / 2, HEIGHT / 2), 7, true, Color.LIGHT_GRAY);
        objects.add(circle);
        return circle;
    }

}
