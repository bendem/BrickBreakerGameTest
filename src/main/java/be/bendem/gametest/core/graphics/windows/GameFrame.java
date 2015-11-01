package be.bendem.gametest.core.graphics.windows;

import be.bendem.gametest.core.Killable;
import be.bendem.gametest.core.events.EventManager;
import be.bendem.gametest.core.events.InternalEvent;
import be.bendem.gametest.core.graphics.Graphics;
import be.bendem.gametest.core.graphics.shapes.Line;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;

/**
 * @author bendem
 */
public class GameFrame extends BaseFrame implements Killable {

    public GameFrame(EventManager<InternalEvent> manager, Graphics graphics, int width, int height) {
        super("Game Test", manager, graphics, new Dimension(width, height), new Color(20, 20, 20));

        // Border of the screen
        objects.add(new Line(new Point(0, 0), new Point(0, height), true));
        objects.add(new Line(new Point(0, 0), new Point(width, 0), true));
        objects.add(new Line(new Point(width-1, 0), new Point(width-1, height), true)); // width-1 because why not

        // TODO Remove that if you want to play
        //objects.add(new Line(new Point(0, height-1), new Point(width, height-1), true)); // height-1 because why not
    }

    @Override
    public void kill() {
        dispose();
    }

}
