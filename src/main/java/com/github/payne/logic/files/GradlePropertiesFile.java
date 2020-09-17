package com.github.payne.logic.files;

import com.github.payne.generator.input.GeneratorConfigs;
import com.github.payne.generator.input.model.enums.Language;
import com.github.payne.generator.input.model.enums.Platform;
import com.github.payne.logic.files.abstracts.GeneratedFile;
import com.github.payne.utils.StringUtils;

public class GradlePropertiesFile extends GeneratedFile {

    private final StringBuilder sb = new StringBuilder();

    public static final String GDX_VERSION = "gdx"; // gets "Version" appended

    public GradlePropertiesFile(GeneratorConfigs input) {
        super("gradle.properties");

        addProperty("org.gradle.daemon",
                "false"); // was having very bad memory usage with daemon+Android
        addProperty("org.gradle.jvmargs",
                "-Xms512M -Xmx4G -XX:MaxPermSize=1G -XX:MaxMetaspaceSize=1G");
        addProperty("org.gradle.configureondemand", "false");

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
        return sb.toString();
    }

    private void addProperty(String name, String value) {
        sb.append(name + "=" + value);
    }

    private void addVersion(String name, String value) {
        sb.append(name + "Version=" + value);
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
