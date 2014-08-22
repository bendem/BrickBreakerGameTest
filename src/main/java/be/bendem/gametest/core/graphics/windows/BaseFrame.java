package be.bendem.gametest.core.graphics.windows;

import be.bendem.gametest.core.events.Callback;
import be.bendem.gametest.core.events.InternalEvent;
import be.bendem.gametest.core.events.InternalEventManager;
import be.bendem.gametest.core.graphics.Drawable;

import java.awt.Graphics;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * TODO Dirty thing to not redraw everything if not changed
 *
 * @author bendem
 */
public abstract class BaseFrame extends JFrame {

    protected final JPanel panel;
    protected final Collection<Drawable> objects;

    public BaseFrame(String title) {
        super(title);
        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics graphics) {
                // Call to super makes sure to handle the opaque background
                super.paintComponent(graphics);
                draw(graphics);
            }
        };
        setContentPane(panel);
        // TODO See if something else might not be better
        objects = Collections.synchronizedSet(new LinkedHashSet<>());
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
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
        objects.forEach((object) -> object.draw(graphics.create()));
    }

    protected <T extends InternalEvent> void register(Callback<T> callable, Class<T> clazz) {
        InternalEventManager.getInstance().register(callable, clazz);
    }

}
