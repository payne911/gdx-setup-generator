package com.github.payne.generator.input;

import com.github.payne.generator.input.model.GdxThirdParty;
import com.github.payne.generator.input.model.LibGdxVersion;
import com.github.payne.generator.input.model.VersionedLanguage;
import com.github.payne.generator.input.model.VersionedLibrary;
import com.github.payne.generator.input.model.enums.AddOn;
import com.github.payne.generator.input.model.enums.Language;
import com.github.payne.generator.input.model.enums.LegacyExtension;
import com.github.payne.generator.input.model.enums.Platform;
import com.github.payne.generator.input.model.enums.Template;
import java.util.HashSet;
import java.util.Set;
import lombok.Data;

/**
 * The class containing all the selected options by a user. Once it is passed to the {@link
 * com.github.payne.logic.LogicProcessor}, it should be considered immutable.
 */
@Data
//@Builder
public class GeneratorConfigs {

    private String libGdxVersion = "1.9.11";
    private String projectName = "MyGdxProjectName";
    private String corePackage = "com.gdx.game";
    private String mainClass = "MainClass";
    private String assetsFolderName = "assets";
    private String androidSdkPath;

    private Template template = Template.CLASSIC;

    private Set<AddOn> addOns = new HashSet<>();
    private Set<Platform> platforms = new HashSet<>();
    private Set<VersionedLanguage> languages = new HashSet<>();

    /* GdxThirdParty is to be preferred as the new approach. The legacy way might just not be supported. */
    private Set<GdxThirdParty> jsonLibraries = new HashSet<>();
    @Deprecated
    private Set<VersionedLibrary> libraries = new HashSet<>();
    @Deprecated
    private Set<LegacyExtension> legacyExtensions = new HashSet<>();

    /* Advanced configuration. */
    private String applicationVersion = "0.0.1-SNAPSHOT";
    private String javaVersion = "8.0";
    private String serverJavaVersion = "8.0";
    private String desktopJavaVersion = "8.0";
    private String androidPluginVersion = "4.0.0";
    private String gwtPluginVersion = "1.0.13";
    private String roboVmVersion = "2.3.9";
    private String postGenerationTask;
    private Integer targetAndroidApi = 29;


    public String getJavaVersion() {
        return javaVersionPrefix(javaVersion);
    }

    public String getServerJavaVersion() {
        return javaVersionPrefix(serverJavaVersion);
    }

    public String getDesktopJavaVersion() {
        return javaVersionPrefix(desktopJavaVersion);
    }

    private String javaVersionPrefix(String javaVersion) {
        final String PREFIX = javaVersion.length() == 1 ? "1." : "";
        return PREFIX + javaVersion;
    }

    public LibGdxVersion getLibGdxVersionObject() {
        return new LibGdxVersion(libGdxVersion);
    }

    public boolean contains(AddOn addOn) {
        return addOns.contains(addOn);
    }

    public boolean contains(Platform platform) {
        return platforms.contains(platform);
    }

    public boolean contains(Language language) {
        for (VersionedLanguage lang : languages) {
            if (lang.isSameLanguage(language)) {
                return true;
            }
        }
        return false;
    }
}
