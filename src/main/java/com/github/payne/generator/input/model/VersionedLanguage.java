package com.github.payne.generator.input.model;

import com.github.payne.generator.input.model.enums.Language;
import com.github.payne.utils.Pair;
import lombok.Getter;

public class VersionedLanguage {

    @Getter
    private Pair<Language, String> language;

    public VersionedLanguage(Language language) {
        this.language = new Pair<>(language, language.getDefaultVersion());
    }

    public VersionedLanguage(Language language, String version) {
        this.language = new Pair<>(language, version);
    }
}
