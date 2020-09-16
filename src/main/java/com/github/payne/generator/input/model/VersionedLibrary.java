package com.github.payne.generator.input.model;

import com.github.payne.utils.Pair;

public class VersionedLibrary {

    private Pair<String, String> library;

    public VersionedLibrary(String library, String version) {
        this.library = new Pair<>(library, version);
    }

    public String getLibrary() {
        return library.first;
    }

    public String getDefaultVersion() {
        return library.second;
    }
}
