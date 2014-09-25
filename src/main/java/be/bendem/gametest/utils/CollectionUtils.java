package be.bendem.gametest.utils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author bendem
 */
public class CollectionUtils {

    @SafeVarargs
    public static <T> Set<T> setOfNotNull(T...objects) {
        return Arrays.stream(objects).filter(obj -> obj != null).collect(Collectors.toSet());
    }

}
