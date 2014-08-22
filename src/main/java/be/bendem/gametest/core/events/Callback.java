package be.bendem.gametest.core.events;

/**
 * @author bendem
 */
public interface Callback<T extends InternalEvent> {

    public void call(T event);

}
