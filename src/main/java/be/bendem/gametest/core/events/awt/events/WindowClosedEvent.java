package be.bendem.gametest.core.events.awt.events;

import java.awt.event.WindowEvent;

/**
 * @author bendem
 */
public class WindowClosedEvent extends InternalWindowEvent {

    public WindowClosedEvent(WindowEvent event) {
        super(event);
    }

}
