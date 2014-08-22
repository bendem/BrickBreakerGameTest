package be.bendem.gametest.core.events;

/**
 * @author bendem
 */
public interface Callback<T> {

    public void call(T event);

}
