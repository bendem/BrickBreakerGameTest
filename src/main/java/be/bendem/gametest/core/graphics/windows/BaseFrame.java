package be.bendem.gametest.core.graphics.windows;

import be.bendem.gametest.core.events.EventManager;
import be.bendem.gametest.core.events.InternalEvent;
import be.bendem.gametest.core.events.awt.AwtEventAdapter;
import be.bendem.gametest.core.graphics.GraphicObject;
import be.bendem.gametest.core.graphics.Graphics;
import be.bendem.gametest.core.logging.Logger;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Panel;
import java.util.Collection;

/**
 * @author bendem
 */
public abstract class BaseFrame extends Frame {

    protected final Collection<GraphicObject> objects;
    protected final Panel panel;

    public BaseFrame(String title, EventManager<InternalEvent> manager, Graphics graphics, Dimension dimensions, Color color) {
        super(title);
        objects = graphics.getObjects();

        setUndecorated(true);

        panel = new Panel();
        panel.setFocusable(false);
        panel.setPreferredSize(dimensions);
        panel.setBackground(color);
        add(panel);

        new AwtEventAdapter(manager).registerAllEvents(this);
    }

    public void display() {
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void redraw() {
        java.awt.Graphics graphics = panel.getGraphics();

        if(graphics == null) {
            // UI isn't loaded
            return;
        }

        drawBackgroung(graphics.create());

        objects.forEach(object -> object.draw(graphics.create()));
    }

    private void drawBackgroung(java.awt.Graphics graphics) {
        graphics.setColor(panel.getBackground());
        graphics.fillRect(0, 0, getWidth(), getHeight());
    }

}
