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
        return String.join(FileUtils.FILE_SEPARATOR, paths);
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

    /**
     * Does not modify the file. Returns a new {@code String}.
     *
     * @param srcPathFromRes example: {@code Arrays.asList("generator", "dynamic", "readme.txt")}
     * @param replacements   the keys should be present in the designated file, surrounded by "${}"
     * @return the content of the file, with the keys replaced by the values of the provided map
     * @see #keyPattern(String)
     */
    public static String replaceFileContent(final List<String> srcPathFromRes,
            Map<String, String> replacements) {
        String initialFileContent = readResourceFileAsString(srcPathFromRes);
        return replaceStringContent(initialFileContent, replacements);
    }

    /**
     * Does not modify the file. Returns a new {@code String}.
     * <p>
     * If you have multiple keys to replace, use {@link #replaceFileContent(List, Map)} instead.
     *
     * @param srcPathFromRes example: {@code Arrays.asList("generator", "dynamic", "readme.txt")}
     * @param key            should be present in the input {@code String} surrounded by "${}"
     * @param replacement    the {@code key} will be replaced by this {@code String}
     * @return the content of the file, with the keys replaced by the values of the provided map
     * @see #keyPattern(String)
     */
    public static String replaceFileContent(final List<String> srcPathFromRes,
            String key, String replacement) {
        String initialFileContent = readResourceFileAsString(srcPathFromRes);
        return replaceStringContent(initialFileContent, key, replacement);
    }

    /**
     * Does not modify the {@code String} input. Returns a new {@code String}.
     *
     * @param initial      the input
     * @param replacements the keys should be present in the designated file, surrounded by "${}"
     * @return the content of the input, with the keys replaced by the values of the provided map
     * @see #keyPattern(String)
     */
    public static String replaceStringContent(String initial, Map<String, String> replacements) {
        String result = initial;
        for (Entry<String, String> entry : replacements.entrySet()) {
            result = replaceStringContent(result, entry.getKey(), entry.getValue());
        }
        return result;
    }

    /**
     * To replace a single key's instances within a {@code String}.
     * <p>
     * If you plan on chaining this, make sure you use the returned {@code String}. However, in that
     * case, you might want to consider using {@link #replaceStringContent(String, Map)} instead.
     *
     * @param initial     the input
     * @param key         should be present in the input {@code String} surrounded by "${}"
     * @param replacement the {@code key} will be replaced by this {@code String}
     * @return the content of the input, with the keys replaced by the value provided
     * @see #keyPattern(String)
     */
    public static String replaceStringContent(String initial, String key, String replacement) {
        return initial.replaceAll(keyPattern(key), replacement);
    }

    private static String keyPattern(String key) {
        return "\\$\\{" + key + "\\}";
    }
}
