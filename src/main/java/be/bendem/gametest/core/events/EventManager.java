package be.bendem.gametest.core.events;

import be.bendem.gametest.utils.Validate;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author bendem
 */
public class EventManager<E> {

    private final Map<Class<? extends E>, List<Callback<? extends E>>> events;

    public EventManager() {
        // TODO See if something else might not be better
        events = new ConcurrentHashMap<>();
    }

    public <T extends E> void register(Callback<T> callback, Class<T> eventType) {
        List<Callback<? extends E>> callbackList = events.get(eventType);
        if(callbackList == null) {
            // TODO See if something else might not be better (thread safe!)
            callbackList = Collections.synchronizedList(new LinkedList<>());
            events.put(eventType, callbackList);
        }
        callbackList.add(callback);
    }

    @SuppressWarnings("unchecked")
    public <T extends InternalEvent> void spawnEvent(T event) {
        events.entrySet().stream()
                .filter((entry) -> entry.getKey().isAssignableFrom(event.getClass()))
                .forEach((entry) -> entry.getValue().forEach(callback -> ((Callback<T>) callback).call(event)));
    }

    private static EventManager<InternalEvent> instance;

    public static void setInstance(EventManager<InternalEvent> instance) {
        // TODO Change this to a constructor or something
        Validate.notNull("Can't set null instance", instance);
        if(EventManager.instance != null) {
            throw new IllegalStateException("Instance has already be set");
        }
        EventManager.instance = instance;
    }

    public static EventManager<InternalEvent> getInstance() {
        if(instance == null) {
            throw new IllegalStateException("Instance has not been set");
        }
        return instance;
    }

}
