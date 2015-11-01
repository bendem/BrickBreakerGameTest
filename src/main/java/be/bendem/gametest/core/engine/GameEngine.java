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
import be.bendem.gametest.core.graphics.shapes.Text;

import java.awt.event.KeyEvent;
import java.util.Collection;

/**
 * @author bendem
 */
public class GameEngine implements Killable {

    private final int PLATFORM_DISTANCE;

    private final GameTest game;
    private final Rectangle platform;
    private final BallMovement ballMovement;

    public GameEngine(GameTest game) {
        PLATFORM_DISTANCE = game.getConfig().getInt("engine.platform.distance", 15);

        this.game = game;

        Graphics graphics = game.getGraphics();
        this.platform = graphics.createPlatform();
        Circle ball = graphics.createBall();
        Collection<Circle> lifePoints = graphics.createLifePoints();
        Collection<Rectangle> bricks = graphics.createBricks();
        Text levelText = graphics.createLevelText();

        this.ballMovement = new BallMovement(game, ball, bricks, levelText, lifePoints);

        graphics.getObjects().add(ball);
        graphics.getObjects().addAll(lifePoints);
        graphics.getObjects().addAll(bricks);
        graphics.getObjects().add(levelText);
    }

    public void start() {
        // Where we kill that game
        register(e -> game.kill(), KeyReleasedEvent.class).filter(e -> e.isButton(KeyEvent.VK_ESCAPE));
        register(e -> game.kill(), WindowClosingEvent.class);

        // Game controls
        register(e -> moveLeft(PLATFORM_DISTANCE * 2), MousePressedEvent.class).filter(event -> event.isButtonDown(1));
        register(e -> moveLeft(PLATFORM_DISTANCE), KeyPressedEvent.class).filter(e -> e.isButton(KeyEvent.VK_LEFT));
        register(e -> moveRight(PLATFORM_DISTANCE * 2), MousePressedEvent.class).filter(event -> event.isButtonDown(3));
        register(e -> moveRight(PLATFORM_DISTANCE), KeyPressedEvent.class).filter(e -> e.isButton(KeyEvent.VK_RIGHT));

        ballMovement.start();
    }

    private void moveLeft(int distance) {
        // FIXME If the platform is moved into the ball, the ball can't free itself
        if(platform.getMinX() - distance < 5) {
            platform.translate((int) (5 - platform.getMinX()), 0);
        } else {
            platform.translate(-distance, 0);
        }
    }

    private void moveRight(int distance) {
        // FIXME If the platform is moved into the ball, the ball can't free itself
        if(platform.getMaxX() + distance > game.getGraphics().WIDTH - 5) {
            platform.translate((int) (game.getGraphics().WIDTH - 5 - platform.getMaxX()), 0);
        } else {
            platform.translate(distance, 0);
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
