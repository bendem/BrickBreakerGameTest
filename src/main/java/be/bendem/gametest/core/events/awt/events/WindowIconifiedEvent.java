package be.bendem.gametest.core.events.awt.events;

import java.awt.event.WindowEvent;

/**
 * @author bendem
 */
public class WindowIconifiedEvent extends InternalWindowEvent {

    public WindowIconifiedEvent(WindowEvent event) {
        super(event);
    }

}
