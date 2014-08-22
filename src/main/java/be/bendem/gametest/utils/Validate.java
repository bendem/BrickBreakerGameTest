package be.bendem.gametest.utils;

/**
 * @author bendem
 */
public class Validate {

    public static void notNull(Object o) {
        notNull(null, o);
    }

    public static void notNull(String message, Object o) {
        if(o == null) {
            throw new IllegalArgumentException(message);
        }
    }

}
