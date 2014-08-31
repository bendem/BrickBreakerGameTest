package be.bendem.gametest.core.graphics.windows;

import be.bendem.gametest.GameTest;
import be.bendem.gametest.core.events.EventManager;
import be.bendem.gametest.core.events.InternalEvent;
import be.bendem.gametest.core.events.awt.AwtEventAdapter;
import be.bendem.gametest.core.graphics.Drawable;

import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Panel;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;

/**
 * @author bendem
 */
public abstract class BaseFrame extends Frame {

    protected final Panel panel;
    protected final Collection<Drawable> objects;

    public BaseFrame(String title, GameTest game) {
        super(title);

        panel = new Panel() {
            @Override
            public void paint(Graphics graphics) {
                // Call to super makes sure to handle the opaque background
                super.paint(graphics);
                draw(graphics);
            }
        };
        panel.setFocusable(false);
        add(panel);

        // TODO See if another collection might not be better
        objects = Collections.synchronizedSet(new LinkedHashSet<>());

        new AwtEventAdapter(game.getEventManager()).registerAllEvents(this);
    }

    public void display() {
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void redraw() {
        panel.update(panel.getGraphics());
    }

    private void draw(Graphics graphics) {
        objects.forEach(object -> object.draw(graphics.create()));
    }

}
