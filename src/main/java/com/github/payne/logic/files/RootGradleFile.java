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

    private final Map<String, String> replacements = new HashMap<>();

    public RootGradleFile(GeneratorConfigs input) {
        super();

        if (input.contains(Language.KOTLIN)) {
            buildDependencies.add("\"org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion\"");
        }
        if (input.contains(Platform.ANDROID)) {
            buildDependencies.add("\"com.android.tools.build:gradle:$androidPluginVersion\"");
        }
        if (input.contains(Platform.HTML)) {
            buildDependencies.add("\"org.wisepersist:gwt-gradle-plugin:$gwtPluginVersion\"");
        }
        if (input.contains(Platform.IOS)) {
            buildDependencies.add("\"com.mobidevelop.robovm:robovm-gradle-plugin:$robovmVersion\"");
        }

        replacements.put("buildDependencies",
                joinDependencies(getBuildDependencies(), "classpath", "\t\t"));
        replacements.put("android", input.contains(Platform.ANDROID)
                ? " - project(':android')"
                : "");
        replacements.put("plugins", String.join("\n", input.getLanguages().stream()
                .map(lang -> {
                    String pluginName = lang.getLanguage();
                    if (lang.isSameLanguage(Language.JAVA)) {
                        pluginName += "-library";
                    }
                    return "\tapply plugin: '" + pluginName + "'";
                })
                .collect(Collectors.toUnmodifiableList())));
        replacements.put("javaVersion", input.getJavaVersion());
        replacements.put("appVersion", input.getApplicationVersion());
        replacements.put("projectName", input.getProjectName());
    }

    @Override
    public String getContent() {
        List<String> resPath = Arrays.asList("generator", "dynamic", "root-build-gradle.txt");
        String initialFile = FileUtils.readResourceFileAsString(resPath);
        return FileUtils.replaceFileContent(initialFile, replacements);
    }
}
