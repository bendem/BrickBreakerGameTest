package be.bendem.gametest.core.engine;

import be.bendem.gametest.GameTest;
import be.bendem.gametest.core.Killable;
import be.bendem.gametest.core.events.Callback;
import be.bendem.gametest.core.events.EventManager;
import be.bendem.gametest.core.events.InternalEvent;
import be.bendem.gametest.core.events.awt.events.KeyReleasedEvent;
import be.bendem.gametest.core.graphics.Translatable;

import java.awt.event.KeyEvent;


/**
 * TODO Check collisions
 *
 * @author bendem
 */
public class GameEngine implements Killable {

    private static final int PLATEFORM_DISTANCE = 40;

    private final GameTest game;
    private final Translatable plateform;
    private final BallMovement ballMovement;

    public GameEngine(GameTest game) {
        this.game = game;
        this.plateform = game.getGraphics().createPlaterform();
        this.ballMovement = new BallMovement(game.getGraphics().createBall());
    }

    public void start() {
        register(e -> plateform.translate(-PLATEFORM_DISTANCE, 0), KeyReleasedEvent.class)
                .filter(e -> e.isButton(KeyEvent.VK_LEFT));
        register(e -> plateform.translate(PLATEFORM_DISTANCE, 0), KeyReleasedEvent.class)
                .filter(e -> e.isButton(KeyEvent.VK_RIGHT));

        ballMovement.start();
    }

    private <T extends InternalEvent> EventManager<InternalEvent>.PredicateProvider<T> register(Callback<T> callback, Class<T> clazz) {
        return game.getEventManager().register(callback, clazz);
    }

    @Override
    public void kill() {
        ballMovement.kill();
    }

}
