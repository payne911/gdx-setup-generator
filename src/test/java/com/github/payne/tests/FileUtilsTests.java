package com.github.payne.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.github.payne.utils.FileUtils;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;

public class FileUtilsTests {

    @Test
    public void joinPath() {
        assertEquals("", FileUtils.joinPath(""));
        assertEquals("src", FileUtils.joinPath("src"));
        assertEquals("src/bob", FileUtils.joinPath("src", "bob"));
        assertEquals("src/bob/main", FileUtils.joinPath("src", "bob", "main"));
    }

    @Test
    public void isEmptyPath() {
        assertTrue(FileUtils.isEmptyPath(Arrays.asList()));
        assertTrue(FileUtils.isEmptyPath(Arrays.asList("")));
        assertTrue(FileUtils.isEmptyPath(Arrays.asList(" ")));
        assertTrue(FileUtils.isEmptyPath(null));

        assertFalse(FileUtils.isEmptyPath(Arrays.asList("src")));
        assertFalse(FileUtils.isEmptyPath(Arrays.asList("src", "main")));
        assertFalse(FileUtils.isEmptyPath(Arrays.asList("src", "main", "groovy")));
    }

    @Test
    public void readResourceFileAsString() {
        String actual = FileUtils
                .readResourceFileAsString(Arrays.asList("copy-res-test", "test.txt"));
        assertEquals("PieMenu ${replaced}", actual);
    }

    @Test
    public void replaceResourceFileContent_String() {
        String input = FileUtils
                .readResourceFileAsString(Arrays.asList("copy-res-test", "test.txt"));
        String result = FileUtils.replaceFileContent(input, "replaced", "is love");
        assertEquals("PieMenu is love", result);
    }

    @Test
    public void replaceResourceFileContent_Map() {
        String input = FileUtils
                .readResourceFileAsString(Arrays.asList("copy-res-test", "test2.txt"));

        final String END_VALUE = "that's it";
        final String LOVE_VALUE = "<3";

        Map<String, String> replacements = new HashMap<>();
        replacements.put("end", END_VALUE);
        replacements.put("love", LOVE_VALUE);

        String result = FileUtils.replaceFileContent(input, replacements);

        assertEquals("<3 TEttinger <3\r\nthat's it", result);
    }
}
