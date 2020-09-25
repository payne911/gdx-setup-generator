package com.github.payne.logic.files.root.files;

import com.github.payne.generator.input.GeneratorConfigs;
import com.github.payne.generator.input.model.enums.Language;
import com.github.payne.generator.input.model.enums.Platform;
import com.github.payne.logic.files.root.BuildGradleFile;
import java.util.stream.Collectors;

public class RootBuildGradleFile extends BuildGradleFile {

    public RootBuildGradleFile(final GeneratorConfigs input) {
        super("generator/dynamic/root-build-gradle.txt", input);
    }

    @Override
    protected void assignOtherKeys() {
        injectBuildDependencies(input);
        injectPotentialAndroidProject(input);
        injectPlugins(input);
    }

    private void injectPotentialAndroidProject(GeneratorConfigs input) {
        assignKey("android", input.contains(Platform.ANDROID)
                ? " - project(':android')"
                : "");
    }

    private void injectPlugins(GeneratorConfigs input) {
        assignKey("plugins", String.join("\n", input.getLanguages().stream()
                .map(lang -> {
                    String pluginName = lang.getLanguage();
                    if (lang.isSameLanguage(Language.JAVA)) {
                        pluginName += "-library";
                    }
                    return "\tapply plugin: '" + pluginName + "'";
                })
                .collect(Collectors.toUnmodifiableList())));
    }

    private void injectBuildDependencies(GeneratorConfigs input) {
        if (input.contains(Language.KOTLIN)) {
            addBuildDependency("org.jetbrains.kotlin:kotlin-gradle-plugin:\\$kotlinVersion");
        }
        if (input.contains(Platform.ANDROID)) {
            addBuildDependency("com.android.tools.build:gradle:\\$androidPluginVersion");
        }
        if (input.contains(Platform.HTML)) {
            addBuildDependency("org.wisepersist:gwt-gradle-plugin:\\$gwtPluginVersion");
        }
        if (input.contains(Platform.IOS)) {
            addBuildDependency("com.mobidevelop.robovm:robovm-gradle-plugin:\\$robovmVersion");
        }
        assignKey("buildDependencies", joinDependencies(buildDeps, "classpath", "\t\t"));
    }
}
