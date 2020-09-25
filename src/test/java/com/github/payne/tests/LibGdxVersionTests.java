package com.github.payne.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.github.payne.utils.LibGdxVersion;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;

public class LibGdxVersionTests {

    private LibGdxVersion version(String input) {
        return new LibGdxVersion(input);
    }

    @Test
    public void isOlderThan_major() {
        final String FIRST = "2.8.3";
        final String SECOND = "1.9.9";
        assertFalse(version(FIRST).isOlderThan(SECOND));
        assertTrue(version(SECOND).isOlderThan(FIRST));
    }

    @Test
    public void isOlderThan_minor() {
        final String FIRST = "1.9.3";
        final String SECOND = "1.6.11";
        assertFalse(version(FIRST).isOlderThan(SECOND));
        assertTrue(version(SECOND).isOlderThan(FIRST));
    }

    @Test
    public void isOlderThan_revision() {
        final String FIRST = "1.0.3";
        final String SECOND = "1.0.0";
        assertFalse(version(FIRST).isOlderThan(SECOND));
        assertTrue(version(SECOND).isOlderThan(FIRST));
    }

    @Test
    public void isOlderThan_equal() {
        final String FIRST = "0.0.0";
        final String SECOND = "0.0.0";
        assertFalse(version(FIRST).isOlderThan(SECOND));
        assertFalse(version(SECOND).isOlderThan(FIRST));
    }

    @Test
    public void isOlderThan_SNAPSHOT() {
        final String FIRST = "1.0.3-SNAPSHOT";
        final String SECOND = "1.0.0";
        assertFalse(version(FIRST).isOlderThan(SECOND));
        assertTrue(version(SECOND).isOlderThan(FIRST));
    }

    @Test
    public void isOlderThan_trim() {
        final String FIRST = "1.0.3 ";
        final String SECOND = "1.0.0   ";
        assertFalse(version(FIRST).isOlderThan(SECOND));
        assertTrue(version(SECOND).isOlderThan(FIRST));
    }

    @Test
    public void isOlderThan_throws() {
        List<String> errors = Arrays
                .asList("1", ".", "1.", "1.1", "1.1.", ".1.1.1", "1.3.4a", "2.a", "a.a.a", "1.2.a");
        int throwCounter = 0;
        for (String erroneous : errors) {
            try {
                version(erroneous);
            } catch (Exception e) {
                throwCounter++;
            }
        }
        assertEquals(errors.size(), throwCounter);
    }
}
