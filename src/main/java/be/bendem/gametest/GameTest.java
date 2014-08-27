package be.bendem.gametest;

import be.bendem.gametest.core.Killable;
import be.bendem.gametest.core.config.Config;
import be.bendem.gametest.core.engine.GameEngine;
import be.bendem.gametest.core.events.EventManager;
import be.bendem.gametest.core.events.InternalEvent;
import be.bendem.gametest.core.graphics.Graphics;
import be.bendem.gametest.core.logging.Logger;

import java.util.Arrays;
import java.util.Iterator;

/**
 * @author bendem
 */
public class GameTest implements Killable {

    private final EventManager<InternalEvent> eventManager;
    private final Config config;
    private final Graphics graphics;
    private final GameEngine engine;

    public GameTest(String configFile) {
        // Loading screen could go here :)
        eventManager = new EventManager<>();
        config = new Config(configFile);
        if(!config.exists()) {
            config.create();
        }
        config.load();
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

    public EventManager<InternalEvent> getEventManager() {
        return eventManager;
    }

    public Graphics getGraphics() {
        return graphics;
    }

    public Config getConfig() {
        return config;
    }

    public GameEngine getEngine() {
        return engine;
    }

    public static void main(String[] args) {
        Iterator<String> iterator = Arrays.asList(args).iterator();
        String configFilename = "./config.cfg";
        while(iterator.hasNext()) {
            String arg = iterator.next();
            if(arg.equals("-c") && iterator.hasNext()) {
                configFilename = iterator.next();
            }
        }

        new GameTest(configFilename).start();
    }

}
