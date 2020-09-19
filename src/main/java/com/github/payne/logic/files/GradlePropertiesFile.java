package com.github.payne.logic.files;

import com.github.payne.generator.annotations.DynamicFile;
import com.github.payne.generator.input.GeneratorConfigs;
import com.github.payne.generator.input.model.enums.Language;
import com.github.payne.generator.input.model.enums.Platform;
import com.github.payne.logic.files.abstracts.GeneratedFile;
import com.github.payne.utils.FileUtils;
import com.github.payne.utils.StringUtils;
import java.util.Arrays;
import java.util.List;

@DynamicFile("generator/dynamic/gradle-properties.txt")
public class GradlePropertiesFile extends GeneratedFile {

    private final StringBuilder sb = new StringBuilder();

    public static final String GDX_VERSION = "gdx"; // gets "Version" appended

    public GradlePropertiesFile(GeneratorConfigs input) {
        super("gradle.properties");

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
            addVersion("gwtFramework", input.getGwtPluginVersion());
            addVersion("gwtPlugin", findGwtVersion(gdxVersion));
        }
        if (input.contains(Platform.ANDROID)) {
            addVersion("androidPlugin", input.getAndroidPluginVersion());
        }
        if (input.contains(Platform.IOS)) {
            addVersion("robovm", input.getRoboVmVersion());
        }
        addVersion(GDX_VERSION, gdxVersion);
    }

    public String getContent() {
        List<String> resPath = Arrays.asList("generator", "dynamic", "gradle-properties.txt");
        return FileUtils.replaceFileContent(resPath, "versionVariables", sb.toString());
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

    private String findGwtVersion(String gdxVersion) {
        String gwtVersion;
        if (gdxVersion.length() == 5 && gdxVersion.charAt(4) != '9') {
            if (gdxVersion.charAt(4) < '5') {
                gwtVersion = "2.6.1";
            } else {
                gwtVersion = "2.8.0";
            }
        } else {
            gwtVersion = "2.8.2";
        }
        return gwtVersion;
    }
}
