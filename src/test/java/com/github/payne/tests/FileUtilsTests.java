package com.github.payne.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.github.payne.utils.FileUtils;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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
    public void appendFilePath() {
        final String INIT = "init";
        List<String> initial = Arrays.asList(INIT);

        final String APPEND = "append";
        List<String> output1 = FileUtils.appendFilePath(initial, APPEND + "1");
        List<String> output2 = FileUtils.appendFilePath(output1, APPEND + "2");

        assertEquals(2, output1.size());
        assertEquals(INIT, output1.get(0));
        assertEquals(APPEND + "1", output1.get(1));

        assertEquals(3, output2.size());
        assertEquals(INIT, output2.get(0));
        assertEquals(APPEND + "1", output2.get(1));
        assertEquals(APPEND + "2", output2.get(2));

        assertEquals("Input list should not be modified", 1, initial.size());
        assertEquals("Input list should not be modified", INIT, initial.get(0));
    }

    @Test
    public void readResourceFileAsString() {
        String actual = FileUtils
                .readResourceFileAsString(Arrays.asList("copy-res-test", "test.txt"));
        assertEquals("PieMenu ${replaced}", actual);
    }

    @Test
    public void replaceStringContent_String() {
        String result = FileUtils
                .replaceStringContent("PieMenu ${replaced}", "replaced", "is love");
        assertEquals("PieMenu is love", result);
    }

    @Test
    public void replaceStringContent_Map() {
        String input = "${love} TEttinger ${love}\n${end}";

        final String END_VALUE = "that's it";
        final String LOVE_VALUE = "<3";

        Map<String, String> replacements = new HashMap<>();
        replacements.put("end", END_VALUE);
        replacements.put("love", LOVE_VALUE);

        String result = FileUtils.replaceStringContent(input, replacements);

        assertEquals("<3 TEttinger <3\nthat's it", result);
    }

    @Test
    public void replaceFileContent() {
        List<String> resPath = Arrays.asList("copy-res-test", "test2.txt");

        final String END_VALUE = "that's it";
        final String LOVE_VALUE = "<3";

        Map<String, String> replacements = new HashMap<>();
        replacements.put("end", END_VALUE);
        replacements.put("love", LOVE_VALUE);

        String result = FileUtils.replaceFileContent(resPath, replacements);

        // todo: shouldn't depend on LineFeeds (had to add '\r')
        assertEquals("<3 TEttinger <3\r\nthat's it", result);
    }
}
