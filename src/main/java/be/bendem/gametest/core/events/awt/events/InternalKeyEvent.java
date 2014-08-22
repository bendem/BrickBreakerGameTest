package be.bendem.gametest.core.events.awt.events;

import be.bendem.gametest.core.events.InternalEvent;

import java.awt.event.KeyEvent;

/**
 * @author bendem
 */
public class InternalKeyEvent extends InternalEvent {

    protected final KeyEvent event;

    public InternalKeyEvent(KeyEvent event) {
        super(event.getSource());
        this.event = event;
    }

    public boolean isButton(int x) {
        return event.getKeyCode() == x;
    }

}
