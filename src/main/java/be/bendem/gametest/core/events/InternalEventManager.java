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
public class InternalEventManager<E> {

    private final Map<Class<? extends E>, List<Callback<? extends E>>> events;

    public InternalEventManager() {
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

    private static InternalEventManager<InternalEvent> instance;

    public static void setInstance(InternalEventManager<InternalEvent> instance) {
        // TODO Change this to a constructor or something
        Validate.notNull("Can't set null instance", instance);
        if(InternalEventManager.instance != null) {
            throw new IllegalStateException("Instance has already be set");
        }
        InternalEventManager.instance = instance;
    }

    public static InternalEventManager<InternalEvent> getInstance() {
        if(instance == null) {
            throw new IllegalStateException("Instance has not been set");
        }
        return instance;
    }

}
