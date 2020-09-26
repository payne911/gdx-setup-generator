package com.github.payne.logic.root.files;

import com.github.payne.generator.input.GeneratorConfigs;
import com.github.payne.generator.input.model.enums.Language;
import com.github.payne.generator.input.model.enums.Platform;
import com.github.payne.logic.DynamicFile;
import com.github.payne.utils.StringUtils;
import com.github.payne.utils.VersionUtils;

public class GradlePropertiesFile extends DynamicFile {

    private final StringBuilder sb = new StringBuilder();

    public static final String GDX_VERSION = "gdx"; // gets "Version" appended

    public GradlePropertiesFile(final GeneratorConfigs input) {
        super("gradle.properties", "generator/dynamic/gradle-properties.txt", input);
    }

    @Override
    protected void assignOtherKeys() {
        addVersions(input);
        assignKey("versionVariables", sb.toString());
    }

    private void addVersions(GeneratorConfigs input) {
        input.getLanguages().forEach(lang -> {
            if (lang.isSameLanguage(Language.JAVA)) {
                return;
            }
            addVersion(lang.getLanguage(), lang.getDefaultVersion());
        });
        input.getLibraries().forEach(lib ->
                addVersion(StringUtils.keepLetters(lib.getLibrary()), lib.getDefaultVersion()));
        input.getExtensions().forEach(ext ->
                addVersion(StringUtils.keepLetters(ext.getId()), ext.getPropertyVersion()));

        String gdxVersion = input.getLibGdxVersion();
        if (input.contains(Platform.HTML)) {
            addVersion("gwtFramework", VersionUtils.deduceGwtVersion(gdxVersion));
            addVersion("gwtPlugin", input.getGwtPluginVersion());
        }
        if (input.contains(Platform.ANDROID)) {
            addVersion("androidPlugin", input.getAndroidPluginVersion());
        }
        if (input.contains(Platform.IOS)) {
            addVersion("robovm", input.getRoboVmVersion());
        }
        addVersion(GDX_VERSION, gdxVersion);
    }

    private void addProperty(String name, String suffix, String value) {
        sb.append(name + suffix + "=" + value + "\n");
    }

    /**
     * Example: {@code addVersion("gdx", "1.9.11")}
     * <p>
     * Appends "{@code gdxVersion=1.9.11}" as a new line in the file.
     *
     * @param name  name of the property
     * @param value value of the property
     */
    private void addVersion(String name, String value) {
        addProperty(name, "Version", value);
    }
}
