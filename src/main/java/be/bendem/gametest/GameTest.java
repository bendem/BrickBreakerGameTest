package be.bendem.gametest;

import be.bendem.gametest.core.Killable;
import be.bendem.gametest.core.engine.GameEngine;
import be.bendem.gametest.core.events.EventManager;
import be.bendem.gametest.core.events.InternalEvent;
import be.bendem.gametest.core.graphics.Graphics;
import be.bendem.gametest.core.logging.Logger;

/**
 * @author bendem
 */
public class GameTest implements Killable {

    private final GameEngine engine;
    private final Graphics graphics;
    private final EventManager<InternalEvent> eventManager;

    public GameTest() {
        // Loading screen could go here :)
        eventManager = new EventManager<>();
        graphics = new Graphics(this);
        engine = new GameEngine(this);
    }

    private void start() {
        Logger.debug("GameTest started");
        graphics.show();
        engine.start();
    }

    @Override
    public void kill() {
        Logger.debug("GameTest is dying");
        try {
            graphics.kill();
        } catch(Throwable t) {
            Logger.error("Game graphics couldn't die", t);
            System.exit(1);
        }
        try {
            engine.kill();
        } catch(Throwable t) {
            Logger.error("Game engine couldn't die", t);
            System.exit(2);
        }
        Logger.info("GameTest died");
        System.exit(0);
    }

    public GameEngine getEngine() {
        return engine;
    }

    public Graphics getGraphics() {
        return graphics;
    }

    public EventManager<InternalEvent> getEventManager() {
        return eventManager;
    }

    public static void main(String[] args) {
        new Thread(() -> new GameTest().start()).start();
    }

}
