package be.bendem.gametest.core.events.awt.events;

import be.bendem.gametest.core.events.InternalEvent;
import be.bendem.gametest.core.graphics.Point;

import java.awt.event.MouseEvent;

/**
 * @author bendem
 */
public class InternalMouseEvent extends InternalEvent {

    private final MouseEvent event;
    private final Point position;
    private final Point screenPosition;

    public InternalMouseEvent(MouseEvent event) {
        super(event.getSource());
        this.event = event;
        position = new Point(event.getX(), event.getY());
        screenPosition = new Point(event.getXOnScreen(), event.getYOnScreen());
    }

    public boolean isButtonDown(int x) {
        int buttonMask = MouseEvent.getMaskForButton(x);
        return (event.getModifiersEx() & buttonMask) == buttonMask;
    }

    public Point getPosition() {
        return position;
    }

    public Point getScreenPosition() {
        return screenPosition;
    }

}
