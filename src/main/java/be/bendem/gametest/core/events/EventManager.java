package be.bendem.gametest.core.events;

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

}
