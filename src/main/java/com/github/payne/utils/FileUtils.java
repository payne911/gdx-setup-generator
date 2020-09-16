package com.github.payne.utils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import lombok.SneakyThrows;

/**
 * Trying to avoid using {@code File.separator} for the use-case of a client-side OS not being the
 * same as the server to which it might be sending a request.
 */
public final class FileUtils {

    private FileUtils() {
    }

    public static String FILE_SEPARATOR = "/";


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

    @SneakyThrows // todo: don't forget to remove
    public static byte[] readResourceFile(final List<String> srcPathFromRes) {
        String srcPath = joinPath(srcPathFromRes);
        return Thread.currentThread().getContextClassLoader().getResourceAsStream(srcPath)
                .readAllBytes();
    }

    public static String readResourceFileAsString(final List<String> srcPathFromRes) {
        return new String(readResourceFile(srcPathFromRes));
    }

    public static String replaceFileContent(String initial, String key, String replacement) {
        return initial.replaceAll(keyPattern(key), replacement);
    }

    private static String keyPattern(String key) {
        return "\\$\\{" + key + "\\}";
    }

    public static String replaceFileContent(String initial, Map<String, String> replacements) {
        String result = initial;
        for (Entry<String, String> entry : replacements.entrySet()) {
            result = replaceFileContent(result, entry.getKey(), entry.getValue());
        }
        return result;
    }
}
