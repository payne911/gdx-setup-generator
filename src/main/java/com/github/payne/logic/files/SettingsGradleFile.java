package com.github.payne.logic.files;

import com.github.payne.generator.input.GeneratorConfigs;
import com.github.payne.logic.files.abstracts.DynamicFile;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SettingsGradleFile extends DynamicFile {

    private final List<String> modules = new ArrayList<>();

    public SettingsGradleFile(final GeneratorConfigs input) {
        super("settings.gradle", "generator/dynamic/settings-gradle.txt", input);
    }

    @Override
    protected void assignKeys() {
        input.getPlatforms().forEach(platform -> modules.add(platform.getValue()));
        modules.sort(Comparator.naturalOrder()); // sorting to help with predictable tests
        assignKey("modules", String.join(", ", modules));
    }
}
