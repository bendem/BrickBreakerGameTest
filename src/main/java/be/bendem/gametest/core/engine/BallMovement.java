package be.bendem.gametest.core.engine;

import be.bendem.gametest.GameTest;
import be.bendem.gametest.core.Killable;
import be.bendem.gametest.core.graphics.Direction;
import be.bendem.gametest.core.graphics.GraphicObject;
import be.bendem.gametest.core.graphics.Graphics;
import be.bendem.gametest.core.graphics.Vector2D;
import be.bendem.gametest.core.graphics.shapes.Circle;
import be.bendem.gametest.core.graphics.shapes.Rectangle;
import be.bendem.gametest.core.logging.Logger;
import be.bendem.gametest.utils.IntersectionUtils;
import be.bendem.gametest.utils.RepeatingTask;

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
        Optional<GraphicObject> optionalObject = graphics.getObjects().stream()
                .filter(GraphicObject::isSolid)
                .filter(obj -> IntersectionUtils.doIntersect(ball, obj))
                .filter(obj -> obj != ball)
                .findAny();

        if(optionalObject.isPresent()) {
            Direction collisionDirection = handleCollision(optionalObject.get());
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
        }
        ball.translate(direction.getX(), direction.getY());
    }

    private Direction handleCollision(GraphicObject object) {
        Rectangle objectBox = object.getBoundingBox();
        if(direction.getX() > 0 // Going right
                && ball.getCenter().getA() + ball.getRadius() >= objectBox.getCorner().getA() + objectBox.getWidth()) {
            return Direction.Right;
        }
        if(direction.getX() < 0 // Going left
                && ball.getCenter().getA() - ball.getRadius() <= objectBox.getCorner().getA()) {
            return Direction.Left;
        }
        if(direction.getY() > 0 // Going down
                && ball.getCenter().getB() + ball.getRadius() >= objectBox.getCorner().getB()) {
            return Direction.Down;
        }
        if(direction.getY() < 0 // Going up
                && ball.getCenter().getB() - ball.getRadius() <= objectBox.getCorner().getB() + objectBox.getHeight()) {
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
