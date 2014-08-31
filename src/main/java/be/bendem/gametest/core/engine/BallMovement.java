package be.bendem.gametest.core.engine;

import be.bendem.gametest.GameTest;
import be.bendem.gametest.core.Killable;
import be.bendem.gametest.core.graphics.Graphics;
import be.bendem.gametest.core.graphics.Vector2D;
import be.bendem.gametest.core.graphics.shapes.Circle;
import be.bendem.gametest.core.graphics.shapes.Rectangle;
import be.bendem.gametest.utils.IntersectionUtils;
import be.bendem.gametest.utils.RepeatingTask;

/**
 * @author bendem
 */
public class BallMovement implements Killable {

    private final Circle ball;
    private final RepeatingTask task;
    private final Vector2D direction;
    private final Rectangle plateform;

    public BallMovement(GameTest game, Rectangle plateform, Circle ball) {
        this.plateform = plateform;
        this.ball = ball;
        this.task = new RepeatingTask(this::moveBall, null, "ball-mover", game.getConfig().getInt("engine.ball.update.delay", 7));
        direction = new Vector2D(1, 2);
    }

    public void moveBall() {
        if(ball.getCenter().getB() + ball.getRadius() >= Graphics.HEIGHT && direction.getY() > 0
                || ball.getCenter().getB() - ball.getRadius() <= 0 && direction.getY() < 0
                || IntersectionUtils.doIntersect(plateform, ball)) {
            direction.setY(-direction.getY());
        }
        if(ball.getCenter().getA() + ball.getRadius() >= Graphics.WIDTH && direction.getX() > 0
                || ball.getCenter().getA() - ball.getRadius() <= 0 && direction.getX() < 0) {
            direction.setX(-direction.getX());
        }
        ball.translate(direction.getX(), direction.getY());
    }

    public void start() {
        task.start();
    }

    @Override
    public void kill() {
        task.cancel();
    }

}
