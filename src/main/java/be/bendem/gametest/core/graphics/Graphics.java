package be.bendem.gametest.core.graphics;

import be.bendem.gametest.GameTest;
import be.bendem.gametest.core.Killable;
import be.bendem.gametest.core.graphics.shapes.Circle;
import be.bendem.gametest.core.graphics.shapes.Rectangle;
import be.bendem.gametest.core.graphics.windows.GameFrame;
import be.bendem.gametest.core.logging.Logger;
import be.bendem.gametest.utils.RepeatingTask;

import java.util.concurrent.TimeUnit;

/**
 * @author bendem
 */
public class Graphics implements Killable {

    private final GameFrame frame;
    private final RepeatingTask graphicsUpdater;

    public Graphics(GameTest game) {
        this.frame = new GameFrame(game);

        long updateInterval = TimeUnit.SECONDS.toMillis(1) / game.getConfig().getInt("graphics.fps", 40);
        this.graphicsUpdater = new RepeatingTask(frame::redraw, "graphics-updater", updateInterval);
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

    public void show() {
        graphicsUpdater.start();
        frame.display();
    }

    public Rectangle createPlaterform() {
        return frame.createPlateform();
    }

    public Circle createBall() {
        return frame.createBall();
    }

}
