package be.bendem.gametest.core.engine;

import be.bendem.gametest.GameTest;
import be.bendem.gametest.core.Killable;
import be.bendem.gametest.core.events.Callback;
import be.bendem.gametest.core.events.EventManager;
import be.bendem.gametest.core.events.InternalEvent;
import be.bendem.gametest.core.events.awt.events.KeyPressedEvent;
import be.bendem.gametest.core.events.awt.events.KeyReleasedEvent;
import be.bendem.gametest.core.events.awt.events.WindowClosingEvent;
import be.bendem.gametest.core.graphics.shapes.Rectangle;
import be.bendem.gametest.core.graphics.windows.GameFrame;

import java.awt.event.KeyEvent;

/**
 * TODO Check collisions
 *
 * @author bendem
 */
public class GameEngine implements Killable {

    private static final int PLATEFORM_DISTANCE = 15;

    private final GameTest game;
    private final Rectangle plateform;
    private final BallMovement ballMovement;

    public GameEngine(GameTest game) {
        this.game = game;
        this.plateform = game.getGraphics().createPlaterform();
        this.ballMovement = new BallMovement(this.plateform, game.getGraphics().createBall());
    }

    public void start() {
        // Where we kill that game
        register(e -> game.kill(), KeyReleasedEvent.class).filter(e -> e.isButton(KeyEvent.VK_ESCAPE));
        register(e -> game.kill(), WindowClosingEvent.class);

        // Game controls
        register(this::moveLeft, KeyPressedEvent.class).filter(e -> e.isButton(KeyEvent.VK_LEFT));
        register(this::moveRight, KeyPressedEvent.class).filter(e -> e.isButton(KeyEvent.VK_RIGHT));

        ballMovement.start();
    }

    private void moveLeft(KeyPressedEvent e) {
        if(plateform.getCorner().getA() > 5 + PLATEFORM_DISTANCE) {
            plateform.translate(-PLATEFORM_DISTANCE, 0);
        } else {
            plateform.getCorner().setA(5);
        }
    }

    private void moveRight(KeyPressedEvent e) {
        if(plateform.getCorner().getA() + plateform.getWidth() + PLATEFORM_DISTANCE < GameFrame.WIDTH - 5) {
            plateform.translate(PLATEFORM_DISTANCE, 0);
        } else {
            plateform.getCorner().setA(GameFrame.WIDTH - plateform.getWidth() - 5);
        }
    }

    private <T extends InternalEvent> EventManager<InternalEvent>.PredicateProvider<T> register(Callback<T> callback, Class<T> clazz) {
        return game.getEventManager().register(callback, clazz);
    }

    @Override
    public void kill() {
        ballMovement.kill();
    }

}
