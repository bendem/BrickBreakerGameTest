package be.bendem.gametest;

import be.bendem.gametest.core.Killable;
import be.bendem.gametest.core.config.Config;
import be.bendem.gametest.core.engine.GameEngine;
import be.bendem.gametest.core.events.EventManager;
import be.bendem.gametest.core.events.InternalEvent;
import be.bendem.gametest.core.graphics.Graphics;
import be.bendem.gametest.core.logging.Logger;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.ContextCapabilities;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GLContext;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;

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
        graphics.start();
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
        /*try {
            Logger.debug("Creating lwjgl context");
            Display.create();
        } catch(LWJGLException e) {
            Logger.error("Failed to create GL context", e);
        }
        Logger.debug("Aknowledging capabilitites");
        ContextCapabilities capabilities = GLContext.getCapabilities();
        Logger.debug("OpenGL11: " + capabilities.OpenGL11);
        Logger.debug("OpenGL12: " + capabilities.OpenGL12);
        Logger.debug("OpenGL13: " + capabilities.OpenGL13);
        Logger.debug("OpenGL14: " + capabilities.OpenGL14);
        Logger.debug("OpenGL15: " + capabilities.OpenGL15);
        Logger.debug("OpenGL20: " + capabilities.OpenGL20);
        Logger.debug("OpenGL21: " + capabilities.OpenGL21);
        Logger.debug("OpenGL30: " + capabilities.OpenGL30);
        Logger.debug("OpenGL31: " + capabilities.OpenGL31);
        Logger.debug("OpenGL32: " + capabilities.OpenGL32);
        Logger.debug("OpenGL33: " + capabilities.OpenGL33);
        Logger.debug("OpenGL40: " + capabilities.OpenGL40);
        Logger.debug("OpenGL41: " + capabilities.OpenGL41);
        Logger.debug("OpenGL42: " + capabilities.OpenGL42);
        Logger.debug("OpenGL43: " + capabilities.OpenGL43);
        Logger.debug("OpenGL44: " + capabilities.OpenGL44);

        if(!capabilities.OpenGL31) {
            Logger.error("OpenGL31 not available, abort mission :O");
            return;
        }*/

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
