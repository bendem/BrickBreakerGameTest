package be.bendem.gametest;

import be.bendem.gametest.core.Killable;
import be.bendem.gametest.core.engine.GameEngine;
import be.bendem.gametest.core.events.awt.events.InternalMouseEvent;
import be.bendem.gametest.core.events.awt.events.MouseClickedEvent;
import be.bendem.gametest.core.graphics.Graphics;
import be.bendem.gametest.core.logging.Logger;

/**
 * @author bendem
 */
public class GameTest implements Killable {

    private final GameEngine gameEngine;
    private final Graphics graphics;

    public GameTest() {
        // Loading screen could go here :)
        graphics = new Graphics();
        gameEngine = new GameEngine(graphics);
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
            Logger.error("Game couldn't die", t);
            System.exit(1);
        }
        Logger.info("GameTest died");
        System.exit(0);
    }

    private static final GameTest instance = new GameTest();

    public static GameTest getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        Logger.info("" + InternalMouseEvent.class.isAssignableFrom(MouseClickedEvent.class));
        Logger.info("" + MouseClickedEvent.class.isAssignableFrom(InternalMouseEvent.class));
        new Thread(instance::start).start();
        //while(!System.console().readLine().equals("stop"));
        //instance.kill();
    }

}
