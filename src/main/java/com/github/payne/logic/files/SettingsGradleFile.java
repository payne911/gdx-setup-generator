package com.github.payne.logic.files;

import com.github.payne.generator.annotations.DynamicFile;
import com.github.payne.generator.input.GeneratorConfigs;
import com.github.payne.logic.files.abstracts.GeneratedFile;
import com.github.payne.utils.FileUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@DynamicFile("generator/dynamic/settings-gradle.txt")
public class SettingsGradleFile extends GeneratedFile {

    private final List<String> modules = new ArrayList<>();

    public SettingsGradleFile(GeneratorConfigs input) {
        super("settings.gradle");

        input.getPlatforms().forEach(platform -> modules.add(platform.getValue()));
        modules.sort(Comparator.naturalOrder()); // sorting to help with predictable tests
    }

    public String getContent() {
        List<String> resPath = Arrays.asList("generator", "dynamic", "settings-gradle.txt");
        return FileUtils.replaceFileContent(resPath, "modules", String.join(", ", modules));
    }
}
