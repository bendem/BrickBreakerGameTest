package be.bendem.gametest.core.events.awt.events;

import java.awt.event.KeyEvent;

/**
 * @author bendem
 */
public class KeyReleasedEvent extends InternalKeyEvent {

    private final KeyEvent event;

    public KeyReleasedEvent(KeyEvent event) {
        super(event);
        this.event = event;
    }

    public boolean isButton(int x) {
        return event.getKeyCode() == x;
    }

}
