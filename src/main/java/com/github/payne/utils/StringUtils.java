package com.github.payne.utils;

import java.util.Collection;
import java.util.Objects;

public final class StringUtils {

    private StringUtils() {
    }

    public static String join(Collection<String> elements, String prefix, String delimiter,
            String postfix) {
        return prefix + String.join(delimiter, elements) + postfix;
    }

    /**
     * @param current    current value of a String.
     * @param defaultsTo the default value to be used if that String is {@code null}.
     * @return the default value only if the current String is {@code null}.
     */
    public static String defaultValue(String current, String defaultsTo) {
        if (Objects.isNull(current)) {
            return defaultsTo;
        }
        return current;
    }

    public static String keepAlphaNumerics(String input) {
        return input.replaceAll("[^A-Za-z0-9]+", "");
    }
}
