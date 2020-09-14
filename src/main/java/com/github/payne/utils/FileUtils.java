package com.github.payne.utils;

import java.util.Arrays;
import java.util.List;

/**
 * Trying to avoid using {@code File.separator} for the use-case of a client-side OS not being the
 * same as the server to which it might be sending a request.
 */
public class FileUtils {

    public static String FILE_SEPARATOR = "/";
    public static String TAB = "\t";
    public static String NEW_LINE = "\n";


    public static String joinPath(String... paths) {
        return joinPath(Arrays.asList(paths));
    }

    public static String joinPath(List<String> paths) {
        return String.join(FileUtils.FILE_SEPARATOR, paths); // todo: maybe not GWT friendly?
//        return Arrays.asList(paths).stream().collect(Collectors.joining(FileUtils.FILE_SEPARATOR));
//        return Arrays.stream(paths).reduce((u, v) -> u + FileUtils.FILE_SEPARATOR + v).orElse("");
    }

    public static boolean isEmptyPath(List<String> path) {
        return path == null || path.isEmpty() || (path.size() == 1 && path.get(0).isBlank());
    }
}
