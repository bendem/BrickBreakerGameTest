package be.bendem.gametest.core.events.awt.events;

import java.awt.event.WindowEvent;

/**
 * @author bendem
 */
public class WindowActivatedEvent extends InternalWindowEvent {

    public WindowActivatedEvent(WindowEvent event) {
        super(event);
    }

}
