package be.bendem.gametest.core.engine;

import be.bendem.gametest.GameTest;
import be.bendem.gametest.core.Killable;
import be.bendem.gametest.core.graphics.Direction;
import be.bendem.gametest.core.graphics.GraphicObject;
import be.bendem.gametest.core.graphics.Graphics;
import be.bendem.gametest.core.graphics.Vector2D;
import be.bendem.gametest.core.graphics.shapes.Circle;
import be.bendem.gametest.core.logging.Logger;
import be.bendem.gametest.utils.RepeatingTask;

import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.util.Optional;

/**
 * @author bendem
 */
public class BallMovement implements Killable {

    private final Circle ball;
    private final RepeatingTask task;
    private final Vector2D direction;
    private final Graphics graphics;

    public BallMovement(GameTest game, Circle ball) {
        this.ball = ball;
        this.task = new RepeatingTask(this::moveBall, "ball-mover", game.getConfig().getInt("engine.ball.update.delay", 7));
        this.direction = new Vector2D(1, -2);
        this.graphics = game.getGraphics();
    }

    public void moveBall() {
        // TODO optimize that (i.e. /don't filter everything before finding one/ or /merge filters/ if streams doesn't already do that)
        Rectangle2D ballBoundingBox = ball.getBounds2D();
        Optional<GraphicObject> optionalObject = graphics.getObjects().stream()
                .filter(GraphicObject::isSolid)
                .filter(obj -> obj != ball)
                .filter(object -> object.intersects(ballBoundingBox))
                .findAny();

        if(optionalObject.isPresent()) {
            GraphicObject object = optionalObject.get();
            Direction collisionDirection = handleCollision(object);
            Logger.debug("Collided with " + object);
            if(collisionDirection == null) {
                throw new AssertionError("The object we collided with does not intersect with the ball :(");
            }

            // Reverse the corresponding direction
            switch(collisionDirection) {
                case Left:
                case Right:
                    direction.setX(-direction.getX());
                    break;
                case Up:
                case Down:
                    direction.setY(-direction.getY());
                    break;
                default:
                    throw new AssertionError("Unhandled Direction value");
            }
            if(object.isBreakable()) {
                graphics.getObjects().remove(object);
            }
        }
        ball.translate(direction.getX(), direction.getY());
    }

    private Direction handleCollision(GraphicObject object) {
        Rectangle objectBox = object.getBounds();
        if(direction.getX() > 0 // Going right
                && ball.getMaxX() >= objectBox.getMinX()) {
            return Direction.Right;
        }
        if(direction.getX() < 0 // Going left
                && ball.getMinX() <= objectBox.getMaxX()) {
            return Direction.Left;
        }
        if(direction.getY() > 0 // Going down
                && ball.getMaxY() >= objectBox.getMinY()) {
            return Direction.Down;
        }
        if(direction.getY() < 0 // Going up
                && ball.getMinY() <= objectBox.getMaxY()) {
            return Direction.Up;
        }
        return null;
    }

    public void start() {
        task.start();
    }

    @Override
    public void kill() {
        task.cancel();
    }

}
