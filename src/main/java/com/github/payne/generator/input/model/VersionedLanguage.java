package com.github.payne.generator.input.model;

import com.github.payne.generator.input.model.enums.Language;
import com.github.payne.utils.Pair;

public class VersionedLanguage {

    private Pair<Language, String> language;

    public VersionedLanguage(Language language) {
        this.language = new Pair<>(language, language.getDefaultVersion());
    }

    public VersionedLanguage(Language language, String version) {
        this.language = new Pair<>(language, version);
    }

    public String getLanguage() {
        return language.first.getString();
    }

    public String getDefaultVersion() {
        return language.second;
    }

    public boolean isSameLanguage(Language other) {
        return getLanguage().equals(other.getString());
    }
}
