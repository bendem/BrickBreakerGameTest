package be.bendem.gametest.core.graphics;

import be.bendem.gametest.core.Killable;
import be.bendem.gametest.core.graphics.windows.GameFrame;
import be.bendem.gametest.utils.RepeatingTask;

import java.util.concurrent.TimeUnit;

/**
 * @author bendem
 */
public class Graphics implements Killable {

    private static final int FPS = 10;
    private static final long UPDATE_INTERVAL = TimeUnit.SECONDS.toMillis(1) / FPS;

    private final GameFrame frame;
    private final RepeatingTask graphicsUpdater;

    public Graphics() {
        frame = new GameFrame();
        graphicsUpdater = new RepeatingTask(frame::redraw, "graphics-updater", UPDATE_INTERVAL);
    }

    @Override
    public void kill() {
        try {
            graphicsUpdater.cancel(500);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        frame.kill();
    }

    public void show() {
        graphicsUpdater.start();
        frame.display();
    }

}
