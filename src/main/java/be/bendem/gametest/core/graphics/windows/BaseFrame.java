package be.bendem.gametest.core.graphics.windows;

import be.bendem.gametest.core.events.EventManager;
import be.bendem.gametest.core.events.InternalEvent;
import be.bendem.gametest.core.events.awt.AwtEventAdapter;
import be.bendem.gametest.core.graphics.GraphicObject;
import be.bendem.gametest.core.graphics.Graphics;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.Collection;

/**
 * @author bendem
 */
public abstract class BaseFrame extends Frame {

    protected final Collection<GraphicObject> objects;
    protected final BufferStrategy bufferStrategy;

    public BaseFrame(String title, EventManager<InternalEvent> manager, Graphics graphics, Dimension dimensions, Color color) {
        super(title);
        objects = graphics.getObjects();

        setUndecorated(true);

        setPreferredSize(dimensions);
        setBackground(color);
        setIgnoreRepaint(true);

        Canvas canvas = new Canvas();
        canvas.setIgnoreRepaint(true);
        canvas.setPreferredSize(dimensions);
        add(canvas);

        display();

        canvas.createBufferStrategy(2);
        bufferStrategy = canvas.getBufferStrategy();

        new AwtEventAdapter(manager).registerAllEvents(this);
    }

    public void display() {
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void redraw() {
        Graphics2D graphics = (Graphics2D) bufferStrategy.getDrawGraphics();
        if(graphics == null) {
            // UI isn't loaded
            return;
        }

        try {
            drawBackground(graphics);
            objects.forEach(object -> object.draw(graphics));
        } finally {
            graphics.dispose();
        }
        bufferStrategy.show();
    }

    private void drawBackground(java.awt.Graphics graphics) {
        graphics.setColor(getBackground());
        graphics.fillRect(0, 0, getWidth(), getHeight());
    }

}
