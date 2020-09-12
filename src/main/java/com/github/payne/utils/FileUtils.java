package com.github.payne.utils;


import java.util.Arrays;
import java.util.List;

public class FileUtils {

    public static String FILE_SEPARATOR = "/";
    public static String TAB = "\t";
    public static String NEW_LINE = "\n";


    public static String joinPath(String... folders) {
        return Arrays.stream(folders).reduce((u, v) -> u + FileUtils.FILE_SEPARATOR + v).orElse("");
    }

    public static boolean isEmptyPath(List<String> path) {
        return path == null || path.isEmpty() || (path.size() == 1 && path.get(0).isBlank());
    }
}
