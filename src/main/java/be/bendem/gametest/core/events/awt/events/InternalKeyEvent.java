package be.bendem.gametest.core.events.awt.events;

import be.bendem.gametest.core.events.InternalEvent;

import java.awt.event.KeyEvent;

/**
 * @author bendem
 */
public class InternalKeyEvent extends InternalEvent {

    public InternalKeyEvent(KeyEvent event) {
        super(event.getSource());
    }

}
