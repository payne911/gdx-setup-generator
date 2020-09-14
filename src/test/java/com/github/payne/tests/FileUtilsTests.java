package com.github.payne.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.github.payne.utils.FileUtils;
import java.util.Arrays;
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
}
