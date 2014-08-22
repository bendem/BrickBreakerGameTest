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

    private final GameEngine gameEngine;
    private final Graphics graphics;
    private final EventManager<InternalEvent> eventManager;

    public GameTest() {
        // Loading screen could go here :)
        eventManager = new EventManager<>();
        graphics = new Graphics(this);
        gameEngine = new GameEngine(this);
    }

    private void start() {
        Logger.debug("GameTest started");
        graphics.show();
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
            gameEngine.kill();
        } catch(Throwable t) {
            Logger.error("Game engine couldn't die", t);
            System.exit(2);
        }
        Logger.info("GameTest died");
        System.exit(0);
    }

    public GameEngine getGameEngine() {
        return gameEngine;
    }

    public Graphics getGraphics() {
        return graphics;
    }

    public EventManager<InternalEvent> getEventManager() {
        return eventManager;
    }

    private static final GameTest instance = new GameTest();

    public static GameTest getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        new Thread(instance::start).start();
    }

}
