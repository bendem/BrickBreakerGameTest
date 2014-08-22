package be.bendem.gametest.core.engine;

import be.bendem.gametest.core.graphics.Point;
import be.bendem.gametest.core.graphics.Vector2D;
import be.bendem.gametest.core.graphics.shapes.Circle;
import be.bendem.gametest.core.graphics.windows.GameFrame;
import be.bendem.gametest.utils.RepeatingTask;

/**
 * @author bendem
 */
public class BallMovement {

    private static final long BALL_MOVEMENT_DELAY = 10;

    private final Circle ball;
    private final RepeatingTask task;
    private final Vector2D direction;

    public BallMovement(Circle ball) {
        this.ball = ball;
        this.task = new RepeatingTask(this::moveBall, "ball-mover", BALL_MOVEMENT_DELAY);
        direction = new Vector2D(Point.zero(), new Point(0, 2));
    }

    public void moveBall() {
        if(ball.getCenter().getB() + ball.getRadius() >= GameFrame.HEIGHT && direction.getY() > 0) {
            direction.setY(-direction.getY());
        } else if(ball.getCenter().getB() - ball.getRadius() <= 0 && direction.getY() < 0) {
            direction.setY(-direction.getY());
        }
        ball.translate(direction.getX(), direction.getY());
    }

    public void start() {
        task.start();
    }

    public void kill() {
        task.cancel();
    }

}
