package be.bendem.gametest.core.graphics;

import be.bendem.gametest.GameTest;
import be.bendem.gametest.core.Killable;
import be.bendem.gametest.core.graphics.shapes.Circle;
import be.bendem.gametest.core.graphics.shapes.Line;
import be.bendem.gametest.core.graphics.shapes.Rectangle;
import be.bendem.gametest.core.logging.Logger;
import be.bendem.gametest.utils.RepeatingTask;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import java.awt.Color;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.concurrent.TimeUnit;

/**
 * @author bendem
 */
public class Graphics implements Killable {

    public static final int WIDTH = 800;
    public static final int HEIGHT = 500;

    public static final int RECTANGLE_WIDTH = 80;
    public static final int RECTANGLE_HEIGHT = 10;

    private final GameTest game;
    private final Collection<Drawable> objects;
    private final RepeatingTask graphicsUpdater;

    public Graphics(GameTest game) {
        this.game = game;

        // TODO See if another collection might not be better
        objects = Collections.synchronizedSet(new LinkedHashSet<>());
        setupScene();

        long updateInterval = TimeUnit.SECONDS.toMillis(1) / game.getConfig().getInt("graphics.fps", 40);
        this.graphicsUpdater = new RepeatingTask(this::redraw, () -> {
            Logger.debug("Starting rendering...");
            try {
                Display.create();
                Display.setTitle("Game test");
                Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
                redraw();
            } catch(LWJGLException e) {
                throw new RuntimeException(e);
            }
        }, "graphics-updater", updateInterval);
    }

    private void setupScene() {
        objects.add(new Line(new Point(0, HEIGHT / 2), new Point(WIDTH, HEIGHT / 2)));
        objects.add(new Line(new Point(WIDTH / 2, 0), new Point(WIDTH / 2, HEIGHT)));
    }

    @Override
    public void kill() {
        try {
            graphicsUpdater.cancel(500);
        } catch(InterruptedException e) {
            Logger.error("Could not cancel graphic updater", e);
        }
        Display.destroy();
    }

    public void start() {
        graphicsUpdater.start();
        //redraw();
    }

    public void redraw() {
        // Clears the scene (sets the backgound)
        GL11.glClearColor(0.1f, 0.1f, 0.1f, 1);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

        drawObjects();

        // Update
        Display.update(false);
    }

    private void drawObjects() {
        objects.forEach(Drawable::draw);
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
