package be.bendem.gametest.core.graphics;

import be.bendem.gametest.GameTest;
import be.bendem.gametest.core.Killable;
import be.bendem.gametest.core.graphics.shapes.Circle;
import be.bendem.gametest.core.graphics.shapes.Rectangle;
import be.bendem.gametest.core.graphics.windows.GameFrame;
import be.bendem.gametest.core.logging.Logger;
import be.bendem.gametest.utils.RepeatingTask;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author bendem
 */
public class Graphics implements Killable {

    public final int WIDTH;
    public final int HEIGHT;
    public final int PLATEFORM_WIDTH;
    public final int PLATEFORM_HEIGHT;

    private final Collection<GraphicObject> objects;
    private final GameFrame frame;
    private final RepeatingTask graphicsUpdater;

    public Graphics(GameTest game) {
        this.WIDTH = game.getConfig().getInt("graphics.width", 800);
        this.HEIGHT = game.getConfig().getInt("graphics.height", 500);
        this.PLATEFORM_WIDTH = game.getConfig().getInt("graphics.plateform.width", 80);
        this.PLATEFORM_HEIGHT = game.getConfig().getInt("graphics.plateform.height", 10);
        this.objects = Collections.synchronizedSet(new LinkedHashSet<>());
        this.frame = new GameFrame(game.getEventManager(), this, WIDTH, HEIGHT);

        long updateInterval = TimeUnit.SECONDS.toMillis(1) / game.getConfig().getInt("graphics.fps", 40);
        this.graphicsUpdater = new RepeatingTask(frame::redraw, "graphics-updater", updateInterval);
    }

    public void show() {
        graphicsUpdater.start();
        frame.display();
    }

    @Override
    public void kill() {
        try {
            graphicsUpdater.cancel(500);
        } catch(InterruptedException e) {
            Logger.error("Could not cancel graphic updater", e);
        }
        frame.kill();
    }

    public Rectangle createPlateform() {
        Rectangle plateform = new Rectangle(
            new Point2D.Double(
                WIDTH / 2 - PLATEFORM_WIDTH / 2,
                HEIGHT - PLATEFORM_HEIGHT - 5
            ),
            PLATEFORM_WIDTH,
            PLATEFORM_HEIGHT,
            true,
            Color.WHITE
        );
        objects.add(plateform);
        return plateform;
    }

    public Circle createBall() {
        Circle circle = new Circle(new Point2D.Double(WIDTH / 2, HEIGHT / 2), 7, true, Color.LIGHT_GRAY);
        objects.add(circle);
        return circle;
    }

    public Set<Rectangle> createBricks() {
        Set<Rectangle> bricks = new HashSet<>();
        int brickW = (WIDTH - 5) / 5;
        for(int i = 5; i < WIDTH - 5; i += brickW) {
            bricks.add(new Rectangle(new Point2D.Double(i, 5), brickW-5, 20, true, Color.GREEN, true));
        }
        return bricks;
    }

    public Collection<GraphicObject> getObjects() {
        return objects;
    }
}
