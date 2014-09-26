package be.bendem.gametest.core.engine;

import be.bendem.gametest.GameTest;
import be.bendem.gametest.core.Killable;
import be.bendem.gametest.core.events.Callback;
import be.bendem.gametest.core.events.EventManager;
import be.bendem.gametest.core.events.InternalEvent;
import be.bendem.gametest.core.events.awt.events.KeyPressedEvent;
import be.bendem.gametest.core.events.awt.events.KeyReleasedEvent;
import be.bendem.gametest.core.events.awt.events.MousePressedEvent;
import be.bendem.gametest.core.events.awt.events.WindowClosingEvent;
import be.bendem.gametest.core.graphics.Graphics;
import be.bendem.gametest.core.graphics.shapes.Circle;
import be.bendem.gametest.core.graphics.shapes.Rectangle;

import java.awt.event.KeyEvent;
import java.util.Collection;
import java.util.Set;

/**
 * TODO Check collisions
 *
 * @author bendem
 */
public class GameEngine implements Killable {

    private final int PLATEFORM_DISTANCE;

    private final GameTest game;
    private final Rectangle plateform;
    private final BallMovement ballMovement;

    public GameEngine(GameTest game) {
        PLATEFORM_DISTANCE = game.getConfig().getInt("engine.plateform.distance", 15);

        this.game = game;

        Graphics graphics = game.getGraphics();
        this.plateform = graphics.createPlateform();
        Circle ball = graphics.createBall();
        Collection<Circle> lifePoints = graphics.createLifePoints();

        this.ballMovement = new BallMovement(game, ball, lifePoints);

        graphics.getObjects().add(ball);
        graphics.getObjects().addAll(lifePoints);
        graphics.getObjects().addAll(graphics.createBricks());
        graphics.getObjects().add(graphics.createLevelText());
    }

    public void start() {
        // Where we kill that game
        register(e -> game.kill(), KeyReleasedEvent.class).filter(e -> e.isButton(KeyEvent.VK_ESCAPE));
        register(e -> game.kill(), WindowClosingEvent.class);

        // Game controls
        register(e -> moveLeft(PLATEFORM_DISTANCE * 2), MousePressedEvent.class).filter(event -> event.isButtonDown(1));
        register(e -> moveLeft(PLATEFORM_DISTANCE), KeyPressedEvent.class).filter(e -> e.isButton(KeyEvent.VK_LEFT));
        register(e -> moveRight(PLATEFORM_DISTANCE * 2), MousePressedEvent.class).filter(event -> event.isButtonDown(3));
        register(e -> moveRight(PLATEFORM_DISTANCE), KeyPressedEvent.class).filter(e -> e.isButton(KeyEvent.VK_RIGHT));

        ballMovement.start();
    }

    private void moveLeft(int distance) {
        // FIXME If the plateform is moved into the ball, the ball can't free itself
        if(plateform.getMinX() - distance < 5) {
            plateform.translate((int) (5 - plateform.getMinX()), 0);
        } else {
            plateform.translate(-distance, 0);
        }
    }

    private void moveRight(int distance) {
        // FIXME If the plateform is moved into the ball, the ball can't free itself
        if(plateform.getMaxX() + distance > game.getGraphics().WIDTH - 5) {
            plateform.translate((int) (game.getGraphics().WIDTH - 5 - plateform.getMaxX()), 0);
        } else {
            plateform.translate(distance, 0);
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
