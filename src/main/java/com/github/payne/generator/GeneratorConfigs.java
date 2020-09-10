package com.github.payne.generator;

import com.github.payne.generator.model.VersionedLanguage;
import com.github.payne.generator.model.enums.AddOn;
import com.github.payne.generator.model.enums.Extension;
import com.github.payne.generator.model.enums.Platform;
import com.github.payne.generator.model.enums.Template;
import com.github.payne.utils.Pair;
import java.util.HashSet;
import java.util.Set;
import lombok.Data;

@Data
//@Builder
public class GeneratorConfigs {

    private String projectName;
    private String corePackage;
    private String mainClass;
    private String androidSdkPath;

    private Template template = Template.APPLICATION_LISTENER;

    private Set<Platform> platforms = new HashSet<>();
    private Set<AddOn> addOns = new HashSet<>();
    private Set<Extension> extensions = new HashSet<>();
    private Set<VersionedLanguage> languages = new HashSet<>();
    private Set<Pair<String, String>> libraries = new HashSet<>();

    /* Advanced configuration. */
    private String libGdxVersion = "1.9.11";
    private String applicationVersion = "0.0.1-SNAPSHOT";
    private String javaVersion = "8.0";
    private String serverJavaVersion = "8.0";
    private String desktopJavaVersion = "8.0";
    private String androidVersion = "4.0.0";
    private String gwtPluginVersion = "1.0.13";
    private String roboVmVersion = "2.3.9";
    private String postGenerationGradleTaskCommand;
    private Integer targetAndroidApi = 29;

    {
        platforms.add(Platform.CORE); // 'CORE' is always included
    }
}
