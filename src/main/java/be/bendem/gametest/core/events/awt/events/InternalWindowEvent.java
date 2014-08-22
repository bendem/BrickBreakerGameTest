package be.bendem.gametest.core.events.awt.events;

import be.bendem.gametest.core.events.InternalEvent;

import java.awt.event.WindowEvent;

/**
 * @author bendem
 */
public class InternalWindowEvent extends InternalEvent {

    public InternalWindowEvent(WindowEvent event) {
        super(event.getSource());
    }

}
