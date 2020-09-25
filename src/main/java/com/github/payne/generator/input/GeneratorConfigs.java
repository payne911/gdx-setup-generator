package com.github.payne.generator.input;

import com.github.payne.generator.input.model.VersionedLanguage;
import com.github.payne.generator.input.model.VersionedLibrary;
import com.github.payne.generator.input.model.enums.AddOn;
import com.github.payne.generator.input.model.enums.Extension;
import com.github.payne.generator.input.model.enums.Language;
import com.github.payne.generator.input.model.enums.Platform;
import com.github.payne.generator.input.model.enums.Template;
import java.util.HashSet;
import java.util.Set;
import lombok.Data;

@Data
//@Builder
public class GeneratorConfigs {

    private String projectName = "MyGdxProjectName";
    private String corePackage = "com.gdx.game";
    private String mainClass = "MainClass";
    private String assetsFolderName = "assets";
    private String androidSdkPath;

    private Template template = Template.CLASSIC;

    private Set<AddOn> addOns = new HashSet<>();
    private Set<Platform> platforms = new HashSet<>();
    private Set<Extension> extensions = new HashSet<>();
    private Set<VersionedLanguage> languages = new HashSet<>();
    private Set<VersionedLibrary> libraries = new HashSet<>();

    /* Advanced configuration. */
    private String libGdxVersion = "1.9.11";
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
