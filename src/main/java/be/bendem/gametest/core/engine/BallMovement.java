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
import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author bendem
 */
public class BallMovement implements Killable {

    private final Circle ball;
    private final RepeatingTask task;
    private final Vector2D direction;
    private final Graphics graphics;
    private final Collection<Circle> lifePoints;

    public BallMovement(GameTest game, Circle ball, Collection<Circle> lifePoints) {
        this.ball = ball;
        this.task = new RepeatingTask(this::moveBall, "ball-mover", game.getConfig().getInt("engine.ball.update.delay", 7));
        this.direction = new Vector2D(-1, -1);
        this.graphics = game.getGraphics();
        this.lifePoints = lifePoints;
    }

    public void moveBall() {
        // TODO optimize that (i.e. /don't filter everything before finding one/ or /merge filters/ if streams doesn't already do that)
        // TODO IntersectionUtils.intersectPoints's actually called twice if an object is found
        Optional<GraphicObject> optionalObject = graphics.getObjects().stream()
                .filter(GraphicObject::isSolid)
                .filter(obj -> obj != ball)
                .filter(obj -> IntersectionUtils.intersectPoints(obj, ball).size() > 0)
                .findAny();

        if(optionalObject.isPresent()) {
            GraphicObject object = optionalObject.get();
            Collection<Point2D> intersectionPoints = IntersectionUtils.intersectPoints(object, ball);
            Direction collisionDirection = handleCollision(intersectionPoints);

            if(collisionDirection == null) {
                Logger.debug("Collided with " + object.getBounds2D());
                throw new AssertionError("That's some fucked up collision :(");
            }

            // Reverse the corresponding direction
            switch(collisionDirection) {
                case Left:
                    direction.setX(Math.abs(direction.getX()));
                    break;
                case Right:
                    direction.setX(-Math.abs(direction.getX()));
                    break;
                case Up:
                    direction.setY(Math.abs(direction.getY()));
                    break;
                case Down:
                    direction.setY(-Math.abs(direction.getY()));
                    break;
                default:
                    throw new AssertionError("Unhandled Direction value");
            }
            if(object.isBreakable()) {
                graphics.getObjects().remove(object);
            }
        }
        ball.translate(direction.getX(), direction.getY());

        // Handle loosing the game
        if(ball.getCenterY() + ball.getHeight()/2 > graphics.HEIGHT) {
            if(lifePoints.size() == 0) {
                Logger.info("You loose");
                kill();
                return;
            }
            Iterator<Circle> iterator = lifePoints.iterator();
            graphics.getObjects().remove(iterator.next());
            iterator.remove();
            ball.setCenter(new Point2D.Double(graphics.WIDTH/2, graphics.HEIGHT/2));
        }
    }

    private Direction handleCollision(Collection<Point2D> intersectionPoints) {
        if(intersectionPoints.size() > 1) {
            // FIXME Handle possible multiple intersections by pushing the ball out of the object
            Logger.error("You broke it, the ball didn't intersect with one point but " + intersectionPoints.size() + " :(");
            Logger.debug("Collided in [" + intersectionPoints.stream().map(Point2D::toString).collect(Collectors.joining(", ")) + "]");
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
