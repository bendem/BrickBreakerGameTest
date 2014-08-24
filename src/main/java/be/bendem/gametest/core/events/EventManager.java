package be.bendem.gametest.core.events;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;

/**
 * @author bendem
 */
public class EventManager<E> {

    private final Map<Class<? extends E>, List<InternalCallback<? extends E>>> events;

    public EventManager() {
        // TODO See if something else might not be better
        events = new ConcurrentHashMap<>();
    }

    public <T extends E> PredicateProvider<T> register(Callback<T> callback, Class<T> eventType) {
        List<InternalCallback<? extends E>> callbackList = events.get(eventType);
        if(callbackList == null) {
            // TODO See if something else might not be better (thread safe!)
            callbackList = Collections.synchronizedList(new LinkedList<>());
            events.put(eventType, callbackList);
        }
        PredicateProvider<T> predicateProvider = new PredicateProvider<>();
        InternalCallback<T> internalCallback = new InternalCallback<T>(callback, predicateProvider);
        callbackList.add(internalCallback);
        return predicateProvider;
    }

    @SuppressWarnings("unchecked")
    public <T extends E> void spawnEvent(T event) {
        events.entrySet().stream()
                .filter(entry -> entry.getKey().isAssignableFrom(event.getClass()))
                .forEach(entry -> entry.getValue().forEach(internalCallback -> ((InternalCallback<T>) internalCallback).call(event)));
    }

    public class PredicateProvider<T extends E> {
        private List<Predicate<T>> predicates;

        public PredicateProvider() {
            predicates = new LinkedList<>();
        }

        public PredicateProvider<T> filter(Predicate<T> predicate) {
            predicates.add(predicate);
            return this;
        }

        private List<Predicate<T>> getPredicates() {
            return predicates;
        }
    }

    private class InternalCallback<T extends E> {
        private final Callback<T> callback;
        private final PredicateProvider<T> predicateProvider;

        private InternalCallback(Callback<T> callback, PredicateProvider<T> predicateProvider) {
            this.callback = callback;
            this.predicateProvider = predicateProvider;
        }

        public void call(T event) {
            if(shouldCall(event)) {
                callback.call(event);
            }
        }

        private boolean shouldCall(T event) {
            return predicateProvider.getPredicates().size() == 0
                || !predicateProvider.getPredicates().stream()
                    .map(predicate -> predicate.test(event))
                    .anyMatch(value -> !value);
        }
    }

}
