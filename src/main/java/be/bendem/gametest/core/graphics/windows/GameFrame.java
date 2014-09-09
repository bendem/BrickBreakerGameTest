package be.bendem.gametest.core.graphics.windows;

import be.bendem.gametest.core.Killable;
import be.bendem.gametest.core.events.EventManager;
import be.bendem.gametest.core.events.InternalEvent;
import be.bendem.gametest.core.graphics.Graphics;
import be.bendem.gametest.core.graphics.Point;
import be.bendem.gametest.core.graphics.shapes.Line;

import java.awt.Color;
import java.awt.Dimension;

/**
 * @author bendem
 */
public class GameFrame extends BaseFrame implements Killable {

    public GameFrame(EventManager<InternalEvent> manager, Graphics graphics, int width, int height) {
        super("Game Test", manager, graphics, new Dimension(width, height), Color.BLACK);

        // Border of the screen
        objects.add(new Line(new Point(0, 0), new Point(0, height)));
        objects.add(new Line(new Point(0, 0), new Point(width, 0)));
        // WIDTH-1 because why not
        objects.add(new Line(new Point(width-1, 0), new Point(width-1, height)));
    }

    @Override
    public void kill() {
        dispose();
    }

}
