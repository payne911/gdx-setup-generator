package com.github.payne.utils;

import lombok.Data;

@Data
public class LibGdxVersion implements Comparable<LibGdxVersion> {

    private int major;
    private int minor;
    private int revision;

    public LibGdxVersion(String libgdxVersionString) {
        final String VERSION = groom(libgdxVersionString);

        String[] parts = VERSION.split("\\.");
        Integer[] ints = transform(parts);

        this.major = ints[0];
        this.minor = ints[1];
        this.revision = ints[2];
    }

    public boolean isOlderThan(String otherVersion) {
        LibGdxVersion other = new LibGdxVersion(otherVersion);
        int compare = this.compareTo(other);
        return compare < 0;
    }

    @Override
    public int compareTo(LibGdxVersion other) {
        if (major < other.major) {
            return -1;
        }
        if (major > other.major) {
            return 1;
        }

        if (minor < other.minor) {
            return -1;
        }
        if (minor > other.minor) {
            return 1;
        }

        if (revision < other.revision) {
            return -1;
        }
        if (revision > other.revision) {
            return 1;
        }

        return 0;
    }

    private String groom(String libgdxVersionString) {
        libgdxVersionString = libgdxVersionString.trim();
        final String SNAPSHOT = "-SNAPSHOT";
        if (libgdxVersionString.endsWith(SNAPSHOT)) {
            int trimIndex = libgdxVersionString.lastIndexOf(SNAPSHOT);
            libgdxVersionString = libgdxVersionString.substring(0, trimIndex);
        }
        return libgdxVersionString;
    }

    private Integer[] transform(String[] input) throws IllegalArgumentException {
        final int MAGIC_NUMBER = 3;
        if (input.length != MAGIC_NUMBER) {
            throw new IllegalArgumentException("There should be 3 digits in the libGDX version.");
        }

        Integer[] ints = new Integer[MAGIC_NUMBER];
        for (int i = 0; i < MAGIC_NUMBER; i++) {
            final String VERSION_STRING = input[i];
            boolean digit = VERSION_STRING.matches("\\d");
            if (!digit) {
                throw new IllegalArgumentException(
                        "One of the numbers in the libGDX version is not a digit.");
            }
            ints[i] = Integer.valueOf(VERSION_STRING);
        }

        return ints;
    }
}
