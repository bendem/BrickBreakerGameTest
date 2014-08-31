package be.bendem.gametest.core.events.awt;

import be.bendem.gametest.core.events.EventManager;
import be.bendem.gametest.core.events.InternalEvent;
import be.bendem.gametest.core.events.awt.events.*;

import java.awt.AWTEvent;
import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author bendem
 */
public class AwtEventAdapter {

    private static final Set<Class<? extends AWTEvent>> ACCEPTED_AWT_EVENT_CLASSES;
    static {
        Set<Class<? extends AWTEvent>> classes = new HashSet<>();
        classes.add(WindowEvent.class);
        classes.add(KeyEvent.class);
        classes.add(MouseEvent.class);
        ACCEPTED_AWT_EVENT_CLASSES = Collections.unmodifiableSet(classes);
    }

    private final EventManager<InternalEvent> eventManager;

    public AwtEventAdapter(EventManager<InternalEvent> eventManager) {
        this.eventManager = eventManager;
    }

    public void registerAllEvents(Frame frame) {
        ACCEPTED_AWT_EVENT_CLASSES.forEach(item -> register(item, frame));
    }

    public <T extends AWTEvent> void register(Class<T> eventType, Frame frame) {
        if(!ACCEPTED_AWT_EVENT_CLASSES.contains(eventType)) {
            // In the end, everything inside here should disapear (all awt events need handleing)
            throw new IllegalArgumentException(eventType.getName() + " is not handled");
        }

        if(eventType == WindowEvent.class) {
            frame.addWindowListener(new WindowsHandler());
        } else if(eventType == MouseEvent.class) {
            frame.addMouseListener(new MouseHandler());
        } else if(eventType == KeyEvent.class) {
            frame.addKeyListener(new KeyHandler());
        }
    }

    private class WindowsHandler implements WindowListener {
        @Override
        public void windowOpened(WindowEvent e) {
            eventManager.spawnEvent(new WindowOpenedEvent(e));
        }
        public void windowClosing(WindowEvent e) {
            eventManager.spawnEvent(new WindowClosingEvent(e));
        }
        @Override
        public void windowClosed(WindowEvent e) {
            eventManager.spawnEvent(new WindowClosedEvent(e));
        }
        @Override
        public void windowIconified(WindowEvent e) {
            eventManager.spawnEvent(new WindowIconifiedEvent(e));
        }
        @Override
        public void windowDeiconified(WindowEvent e) {
            eventManager.spawnEvent(new WindowDeiconifiedEvent(e));
        }
        @Override
        public void windowActivated(WindowEvent e) {
            eventManager.spawnEvent(new WindowActivatedEvent(e));
        }
        @Override
        public void windowDeactivated(WindowEvent e) {
            eventManager.spawnEvent(new WindowDeactivatedEvent(e));
        }
    }

    private class MouseHandler implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            eventManager.spawnEvent(new MouseClickedEvent(e));
        }
        @Override
        public void mousePressed(MouseEvent e) {
            eventManager.spawnEvent(new MousePressedEvent(e));
        }
        @Override
        public void mouseReleased(MouseEvent e) {
            eventManager.spawnEvent(new MouseReleasedEvent(e));
        }
        @Override
        public void mouseEntered(MouseEvent e) {
            eventManager.spawnEvent(new MouseEnteredEvent(e));
        }
        @Override
        public void mouseExited(MouseEvent e) {
            eventManager.spawnEvent(new MouseExitedEvent(e));
        }
    }

    private class KeyHandler implements KeyListener {
        @Override
        public void keyTyped(KeyEvent e) {
            eventManager.spawnEvent(new KeyTypedEvent(e));
        }
        @Override
        public void keyPressed(KeyEvent e) {
            eventManager.spawnEvent(new KeyPressedEvent(e));
        }
        @Override
        public void keyReleased(KeyEvent e) {
            eventManager.spawnEvent(new KeyReleasedEvent(e));
        }
    }

}
