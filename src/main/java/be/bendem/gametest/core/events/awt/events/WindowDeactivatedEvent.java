package be.bendem.gametest.core.events.awt.events;

import java.awt.event.WindowEvent;

/**
 * @author bendem
 */
public class WindowDeactivatedEvent extends InternalWindowEvent {

    public WindowDeactivatedEvent(WindowEvent event) {
        super(event);
    }

}
