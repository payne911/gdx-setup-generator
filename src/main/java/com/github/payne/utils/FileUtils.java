package com.github.payne.utils;

import com.github.payne.generator.input.GeneratorConfigs;
import com.github.payne.utils.annotations.NotTested;
import com.github.payne.utils.exceptions.ResourceFileReadException;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Trying to avoid using {@code File.separator} for the use-case of a client-side OS not being the
 * same as the server to which it might be sending a request.
 */
public final class FileUtils {

    private static final Map<String, String> REPLACEMENTS = new HashMap<>();

    private FileUtils() {
    }

    public static String FILE_SEPARATOR = "/";


    public static String joinPath(String... paths) {
        return joinPath(Arrays.asList(paths));
    }

    public static List<String> splitPath(String path) {
        return Arrays.asList(path.split(FILE_SEPARATOR));
    }

    public static String joinPath(List<String> paths) {
        return String.join(FileUtils.FILE_SEPARATOR, paths);
    }

    public static boolean isEmptyPath(List<String> path) {
        return path == null || path.isEmpty() || (path.size() == 1 && path.get(0).isBlank());
    }

    /**
     * Duplicates the entry list to obtain a new list which has the specified string appended.
     *
     * @param source     original list which will not be modified, but rather cloned.
     * @param appendName element to be added at the end of the new list.
     * @return the newly-created list, with the extra element appended.
     */
    public static List<String> appendFilePath(List<String> source, String appendName) {
        String[] tmpPath = source.toArray(new String[source.size() + 1]);
        tmpPath[source.size()] = appendName;
        return Arrays.asList(tmpPath);
    }

    /**
     * @param srcPathFromRes For a file "{@code src/main/resources/foo/bar.txt}", you want to use
     *                       "{@code Arrays.asList("foo", "bar.txt")}"
     * @return the raw byte content of the designated file
     * @throws ResourceFileReadException generally thrown due to the file path being invalid
     *                                   (pointing to a non-existing file)
     */
    public static byte[] readResourceFile(final List<String> srcPathFromRes)
            throws ResourceFileReadException {
        return readResourceFile(joinPath(srcPathFromRes));
    }

    /**
     * @param srcPathFromRes For a file "{@code src/main/resources/foo/bar.txt}", you want to use
     *                       "{@code foo/bar.txt}"
     * @return the raw byte content of the designated file
     * @throws ResourceFileReadException generally thrown due to the file path being invalid
     *                                   (pointing to a non-existing file)
     */
    public static byte[] readResourceFile(final String srcPathFromRes)
            throws ResourceFileReadException {
        try {
            // todo: close the InputStream
            return Thread.currentThread().getContextClassLoader()
                    .getResourceAsStream(srcPathFromRes).readAllBytes();
        } catch (NullPointerException npe) {
            throw new ResourceFileReadException(
                    "Input file name didn't lead to a resource file.", npe);
        } catch (IOException | OutOfMemoryError ex) {
            throw new ResourceFileReadException(
                    "Something went wrong while trying to read the desired file.", ex);
        }
    }

    /**
     * @param srcPathFromRes For a file "{@code src/main/resources/foo/bar.txt}", you want to use
     *                       "{@code Arrays.asList("foo", "bar.txt")}"
     * @return the content of the designated file as a String
     * @throws ResourceFileReadException generally thrown due to the file path being invalid
     *                                   (pointing to a non-existing file)
     */
    public static String readResourceFileAsString(final List<String> srcPathFromRes)
            throws ResourceFileReadException {
        return new String(readResourceFile(srcPathFromRes));
    }

    /**
     * @param srcPathFromRes For a file "{@code src/main/resources/foo/bar.txt}", you want to use
     *                       "{@code Arrays.asList("foo", "bar.txt")}"
     * @return the content of the designated file as a String
     * @throws ResourceFileReadException generally thrown due to the file path being invalid
     *                                   (pointing to a non-existing file)
     */
    public static String readResourceFileAsString(final String srcPathFromRes)
            throws ResourceFileReadException {
        return new String(readResourceFile(srcPathFromRes));
    }

    /**
     * Does not modify the file. Returns a new {@code String}.
     *
     * @param srcPathFromRes example: {@code Arrays.asList("generator", "dynamic", "readme.txt")}
     * @param replacements   the keys should be present in the designated file, surrounded by "${}"
     * @return the content of the file, with the keys replaced by the values of the provided map
     * @throws ResourceFileReadException generally thrown due to the file path being invalid
     *                                   (pointing to a non-existing file)
     * @see #keyPattern(String)
     */
    public static String replaceFileContent(final List<String> srcPathFromRes,
            Map<String, String> replacements) throws ResourceFileReadException {
        String initialFileContent = readResourceFileAsString(srcPathFromRes);
        return replaceStringContent(initialFileContent, replacements);
    }

    /**
     * Does not modify the file. Returns a new {@code String}.
     *
     * @param srcPathFromRes example: "{@code generator/dynamic/readme.txt}"
     * @param replacements   the keys should be present in the designated file, surrounded by "${}"
     * @return the content of the file, with the keys replaced by the values of the provided map
     * @throws ResourceFileReadException generally thrown due to the file path being invalid
     *                                   (pointing to a non-existing file)
     * @see #keyPattern(String)
     */
    public static String replaceFileContent(final String srcPathFromRes,
            Map<String, String> replacements) throws ResourceFileReadException {
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
     * @throws ResourceFileReadException generally thrown due to the file path being invalid
     *                                   (pointing to a non-existing file)
     * @see #keyPattern(String)
     */
    public static String replaceFileContent(final List<String> srcPathFromRes,
            String key, String replacement) throws ResourceFileReadException {
        String initialFileContent = readResourceFileAsString(srcPathFromRes);
        return replaceStringContent(initialFileContent, key, replacement);
    }

    /**
     * Does not modify the file. Returns a new {@code String}.
     * <p>
     * If you have multiple keys to replace, use {@link #replaceFileContent(String, Map)} instead.
     *
     * @param srcPathFromRes example: {@code Arrays.asList("generator", "dynamic", "readme.txt")}
     * @param key            should be present in the input {@code String} surrounded by "${}"
     * @param replacement    the {@code key} will be replaced by this {@code String}
     * @return the content of the file, with the keys replaced by the values of the provided map
     * @throws ResourceFileReadException generally thrown due to the file path being invalid
     *                                   (pointing to a non-existing file)
     * @see #keyPattern(String)
     */
    public static String replaceFileContent(final String srcPathFromRes,
            String key, String replacement) throws ResourceFileReadException {
        String initialFileContent = readResourceFileAsString(srcPathFromRes);
        return replaceStringContent(initialFileContent, key, replacement);
    }

    /**
     * Does not modify the {@code String} input. Returns a new {@code String}.
     *
     * @param initial      the input
     * @param replacements the keys should be present in the designated file, surrounded by "${}"
     * @return the content of the input, with the keys replaced by the values of the provided map
     * @throws ResourceFileReadException generally thrown due to the file path being invalid
     *                                   (pointing to a non-existing file)
     * @see #keyPattern(String)
     */
    public static String replaceStringContent(String initial, Map<String, String> replacements)
            throws ResourceFileReadException {
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
     * @throws ResourceFileReadException generally thrown due to the file path being invalid
     *                                   (pointing to a non-existing file)
     * @see #keyPattern(String)
     */
    public static String replaceStringContent(String initial, String key, String replacement)
            throws ResourceFileReadException {
        return initial.replaceAll(keyPattern(key), replacement);
    }

    /**
     * Assigns the "common keys" which can be extracted automatically from the {@link
     * GeneratorConfigs}.
     * <p>
     * Currently automatically injected:
     * <ul>
     *      <li>{@code ${gwtVersion}}</li>
     *      <li>{@code ${corePackage}}</li>
     *      <li>{@code ${mainClass}}</li>
     *      <li>{@code ${assetsFolderName}}</li>
     *      <li>{@code ${targetAndroidApi}}</li>
     *      <li>{@code ${serverJavaVersion}}</li>
     *      <li>{@code ${desktopJavaVersion}}</li>
     *      <li>{@code ${androidSdkPath}}</li>
     *      <li>{@code ${javaVersion}}</li>
     *      <li>{@code ${appVersion}}</li>
     *      <li>{@code ${projectName}}</li>
     * </ul>
     *
     * @param initialText a String containing keys to be replaced.
     * @return the text with all the pre-defined keys replaced.
     */
    @NotTested
    public static String injectConfigs(String initialText, GeneratorConfigs input) {
        if (REPLACEMENTS.isEmpty()) {
            assignKey("gwtVersion", VersionUtils.deduceGwtVersion(input.getLibGdxVersion()));
            assignKey("corePackage", input.getCorePackage());
            assignKey("mainClass", input.getMainClass());
            assignKey("assetsFolderName", input.getAssetsFolderName());
            assignKey("targetAndroidApi", input.getTargetAndroidApi().toString());
            assignKey("serverJavaVersion", input.getServerJavaVersion());
            assignKey("desktopJavaVersion", input.getDesktopJavaVersion());
            assignKey("androidSdkPath", input.getAndroidSdkPath());
            assignKey("javaVersion", input.getJavaVersion());
            assignKey("appVersion", input.getApplicationVersion());
            assignKey("projectName", input.getProjectName());
        }

        return replaceStringContent(initialText, REPLACEMENTS);
    }

    /**
     * Just a cleaner way to assign a key and a replacement value.
     */
    private static void assignKey(String key, String replacement) {
        REPLACEMENTS.put(key, replacement);
    }

    private static String keyPattern(String key) {
        return "\\$\\{" + key + "\\}";
    }
}
