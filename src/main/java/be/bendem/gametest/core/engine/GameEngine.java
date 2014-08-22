package be.bendem.gametest.core.engine;

import be.bendem.gametest.GameTest;
import be.bendem.gametest.core.Killable;
import be.bendem.gametest.core.events.Callback;
import be.bendem.gametest.core.events.EventManager;
import be.bendem.gametest.core.events.InternalEvent;
import be.bendem.gametest.core.events.awt.events.KeyReleasedEvent;
import be.bendem.gametest.core.graphics.Translatable;
import be.bendem.gametest.core.graphics.shapes.Circle;
import be.bendem.gametest.core.graphics.shapes.Rectangle;

import java.awt.event.KeyEvent;


/**
 * @author bendem
 */
public class GameEngine implements Killable {

    private static final int PLATEFORM_DISTANCE = 40;

    private final GameTest game;
    private final Translatable platerform;
    private final Translatable ball;

    public GameEngine(GameTest game) {
        this.game = game;
        platerform = game.getGraphics().createPlaterform();
        ball = game.getGraphics().createBall();
    }

    public void start() {
        // TODO Check screen borders
        register(e -> platerform.translate(-PLATEFORM_DISTANCE, 0), KeyReleasedEvent.class)
                .filter(e -> e.isButton(KeyEvent.VK_LEFT));

        register(e -> platerform.translate(PLATEFORM_DISTANCE, 0), KeyReleasedEvent.class)
                .filter(e -> e.isButton(KeyEvent.VK_RIGHT));
    }

    private <T extends InternalEvent> EventManager<InternalEvent>.PredicateProvider<T> register(Callback<T> callback, Class<T> clazz) {
        return game.getEventManager().register(callback, clazz);
    }

    @Override
    public void kill() {}

}
