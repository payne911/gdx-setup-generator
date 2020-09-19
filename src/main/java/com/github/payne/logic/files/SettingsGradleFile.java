package com.github.payne.logic.files;

import com.github.payne.generator.input.GeneratorConfigs;
import com.github.payne.logic.files.abstracts.GeneratedFile;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SettingsGradleFile extends GeneratedFile {

    private final List<String> modules = new ArrayList<>();

    public SettingsGradleFile(GeneratorConfigs input) {
        super("settings.gradle");

        input.getPlatforms().forEach(platform -> modules.add(platform.getValue()));
        modules.sort(Comparator.naturalOrder()); // sorting to help with predictable tests
    }

    public String getContent() {
        return "include " + String.join(", ", modules);
    }
}
