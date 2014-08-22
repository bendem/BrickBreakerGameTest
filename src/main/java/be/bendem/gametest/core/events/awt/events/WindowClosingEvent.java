package be.bendem.gametest.core.events.awt.events;

import java.awt.event.WindowEvent;

/**
 * @author bendem
 */
public class WindowClosingEvent extends InternalWindowEvent {

    public WindowClosingEvent(WindowEvent e) {
        super(e);
    }

}
