package be.bendem.gametest.core.graphics.windows;

import be.bendem.gametest.core.events.EventManager;
import be.bendem.gametest.core.events.InternalEvent;
import be.bendem.gametest.core.events.awt.AwtEventAdapter;
import be.bendem.gametest.core.graphics.GraphicObject;
import be.bendem.gametest.core.graphics.Graphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.Panel;
import java.awt.RenderingHints;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author bendem
 */
public abstract class BaseFrame extends Frame {

    private static final Map<RenderingHints.Key, Object> RENDERER_OPTIONS;
    static {
        Map<RenderingHints.Key, Object> map = new HashMap<>();
        map.put(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_SPEED);
        map.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
        map.put(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_SPEED);
        map.put(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_DISABLE);
        map.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
        RENDERER_OPTIONS = Collections.unmodifiableMap(map);
    }

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
        Graphics2D graphics = ((Graphics2D) panel.getGraphics());
        if(graphics == null) {
            // UI isn't loaded
            return;
        }
        graphics.setRenderingHints(RENDERER_OPTIONS);

        drawBackgroung(graphics.create());

        objects.forEach(object -> object.draw((Graphics2D) graphics.create()));
    }

    private void drawBackgroung(java.awt.Graphics graphics) {
        graphics.setColor(panel.getBackground());
        graphics.fillRect(0, 0, getWidth(), getHeight());
    }

}
