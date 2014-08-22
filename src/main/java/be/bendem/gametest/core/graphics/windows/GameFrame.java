package be.bendem.gametest.core.graphics.windows;

import be.bendem.gametest.GameTest;
import be.bendem.gametest.core.Killable;
import be.bendem.gametest.core.events.InternalEvent;
import be.bendem.gametest.core.events.awt.events.KeyReleasedEvent;
import be.bendem.gametest.core.events.awt.events.WindowClosingEvent;
import be.bendem.gametest.core.graphics.Point;
import be.bendem.gametest.core.graphics.shapes.Circle;
import be.bendem.gametest.core.graphics.shapes.Line;
import be.bendem.gametest.core.graphics.shapes.Rectangle;
import be.bendem.gametest.core.logging.Logger;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyEvent;

/**
 * @author bendem
 */
public class GameFrame extends BaseFrame implements Killable {

    public static final int WIDTH = 800;
    public static final int HEIGHT = 500;

    public static final int RECTANGLE_WIDTH = 80;
    public static final int RECTANGLE_HEIGHT = 10;

    private final Rectangle plateform;

    public GameFrame(GameTest game) {
        super("Game Test", game);
        panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        panel.setBackground(Color.BLACK);

        plateform = new Rectangle(
            new Point(
                WIDTH / 2 - RECTANGLE_WIDTH / 2,
                HEIGHT - RECTANGLE_HEIGHT - 5
            ),
            RECTANGLE_WIDTH,
            RECTANGLE_HEIGHT,
            true
        );
        objects.add(plateform);

        // Debug center
        objects.add(new Circle(new Point(WIDTH / 2, HEIGHT / 2), 15));
        objects.add(new Line(new Point(0, HEIGHT / 2), new Point(WIDTH, HEIGHT / 2)));
        objects.add(new Line(new Point(WIDTH / 2, 0), new Point(WIDTH / 2, HEIGHT)));

        register(e -> GameTest.getInstance().kill(), WindowClosingEvent.class);
        register(e -> Logger.debug(e.getClass().getName()), InternalEvent.class);
        register(e -> {
            int distance = 0;
            if(e.isButton(KeyEvent.VK_LEFT))
                distance = -40;
            else if(e.isButton(KeyEvent.VK_RIGHT))
                distance = 40;
            translatePlateform(distance);
        }, KeyReleasedEvent.class);
    }

    public void translatePlateform(int distance) {
        if(distance == 0) {
            return;
        }
        plateform.translate(distance, 0);
    }

    @Override
    public void kill() {
        dispose();
    }

}
