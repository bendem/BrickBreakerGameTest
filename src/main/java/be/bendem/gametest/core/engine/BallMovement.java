package be.bendem.gametest.core.engine;

import be.bendem.gametest.GameTest;
import be.bendem.gametest.core.Killable;
import be.bendem.gametest.core.graphics.Direction;
import be.bendem.gametest.core.graphics.GraphicObject;
import be.bendem.gametest.core.graphics.Graphics;
import be.bendem.gametest.core.graphics.Vector2D;
import be.bendem.gametest.core.graphics.shapes.Circle;
import be.bendem.gametest.core.logging.Logger;
import be.bendem.gametest.utils.IntersectionUtils;
import be.bendem.gametest.utils.RepeatingTask;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Collection;
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
        this.direction = new Vector2D(-1, -1);
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
            Logger.debug("Collided with " + object.getBounds2D() + " in " + collisionDirection + " direction");
            if(collisionDirection == null) {
                // FIXME That should never happen, if it does (and it does), it's definitly a bug :|
                Logger.error("Collision is null", new AssertionError("That's some fucked up collision :("));
                ball.translate(direction.getX(), direction.getY());
                return;
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
        Collection<Point2D> intersectionPoints = IntersectionUtils.intersect(object, ball);

        if(intersectionPoints.size() < 1 || intersectionPoints.size() > 2) {
            // FIXME Handle possible multiple intersections by pushing the ball out of the object
            Logger.error("You broke it, the ball didn't intersect with one point but " + intersectionPoints.size() + " :(");
            return null;
        }

        Direction direction = null;
        for(Point2D point : intersectionPoints) {
            Direction tmp = getDirectionFromPoint(point);
            if(direction != null && direction != tmp) {
                Logger.error("Collided in more than one direction : " + direction + " / " + tmp);
                return null;
            }
            direction = getDirectionFromPoint(point);
        }
        return direction;
    }

    private Direction getDirectionFromPoint(Point2D point) {
        return getDirectionFromAngle(getAngleFromPoint(point));
    }

    private double getAngleFromPoint(Point2D point) {
        return (Math.toDegrees(Math.atan2(ball.getCenterY() - point.getY(), point.getX() - ball.getCenterX())) + 360) % 360;
    }

    private Direction getDirectionFromAngle(double angle) {
        Logger.debug("Collision angle: " + angle);
        if(angle <= 45 || angle > 315) {
            return Direction.Right;
        }
        if(angle < 135) {
            return Direction.Up;
        }
        if(angle < 225) {
            return Direction.Left;
        }
            return Direction.Down;
        }

    public void start() {
        task.start();
    }

    @Override
    public void kill() {
        task.cancel();
    }

}
