package be.bendem.gametest.core.events.awt.events;

import java.awt.event.WindowEvent;

/**
 * @author bendem
 */
public class WindowOpenedEvent extends InternalWindowEvent {

    public WindowOpenedEvent(WindowEvent event) {
        super(event);
    }

}
