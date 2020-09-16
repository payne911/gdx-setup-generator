package com.github.payne.logic.files;

import com.github.payne.generator.annotations.DynamicFile;
import com.github.payne.generator.input.GeneratorConfigs;
import com.github.payne.generator.input.model.enums.Language;
import com.github.payne.generator.input.model.enums.Platform;
import com.github.payne.utils.FileUtils;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@DynamicFile("generator/dynamic/root-build-gradle.txt")
public class RootGradleFile extends GradleFile {

    @Override
    public String getContent(GeneratorConfigs input) {
        Map<String, String> replacements = new HashMap<>();
        replacements
                .put("buildDependencies", joinDependencies(dependencies, "classpath", "\t\t"));
        replacements.put("android", input.contains(Platform.ANDROID)
                ? " - project(':android')"
                : "");
        replacements.put("plugins", String.join("\n", input.getLanguages().stream()
                .map(lang -> {
                    String pluginName = lang.getLanguage().getValue();
                    if (lang.isSameLanguage(Language.JAVA)) {
                        pluginName += "-library";
                    }
                    return "\tapply plugin: '" + pluginName + "'";
                })
                .collect(Collectors.toUnmodifiableList())));
        replacements.put("javaVersion", input.getJavaVersion());
        replacements.put("appVersion", input.getApplicationVersion());
        replacements.put("projectName", input.getProjectName());

        List<String> resPath = Arrays.asList("generator", "dynamic", "root-build-gradle.txt");
        String initialFile = FileUtils.readResourceFileAsString(resPath);
        return FileUtils.replaceFileContent(initialFile, replacements);
    }
}
