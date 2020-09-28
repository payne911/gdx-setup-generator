package com.github.payne.tests;

import static org.junit.Assert.assertEquals;

import com.github.payne.generator.input.GeneratorConfigs;
import com.github.payne.logic.DynamicFile;
import org.junit.Test;

public class DynamicFileTests {

    public static class VeryDynamicFile extends DynamicFile {

        private final String replacement;

        public VeryDynamicFile(String resourcePath, GeneratorConfigs input, String replacement) {
            super("file.name", resourcePath, input);
            this.replacement = replacement;
        }

        @Override
        protected void assignKeys() {
            assignKey("something-something", replacement);
        }
    }

    @Test
    public void getContent() {
        GeneratorConfigs configs = new GeneratorConfigs();

        final String projectName = "PROJECT";
        configs.setProjectName(projectName);

        final String assetsFolderName = "ASSETS";
        configs.setAssetsFolderName(assetsFolderName);

        final String gwtPluginVersion = "1.0.0"; // will be ignored
        configs.setGwtPluginVersion(gwtPluginVersion);

        final String gwtVersion = "2.8.2"; // comes from VersionUtils#deduceGwtVersion
        configs.setLibGdxVersion("1.9.11");

        final String corePackage = "foo.bar";
        configs.setCorePackage(corePackage);

        final String mainClass = "MainClass";
        configs.setMainClass(mainClass);

        final String androidSdkPath = "D:/ick";
        configs.setAndroidSdkPath(androidSdkPath);

        final String appVersion = "0.0.1-SNAPSHOT";
        configs.setApplicationVersion(appVersion);

        final String javaVersion = "9.1";
        configs.setJavaVersion(javaVersion);

        final String desktopJavaVersion = "9.2";
        configs.setDesktopJavaVersion(desktopJavaVersion);

        final String serverJavaVersion = "9.3";
        configs.setServerJavaVersion(serverJavaVersion);

        final int targetAndroidApi = 9001;
        configs.setTargetAndroidApi(targetAndroidApi);

        final String replacement = "Such wow! Much impress!";
        DynamicFile dynamicFile = new VeryDynamicFile("dynamic/a-file.txt", configs, replacement);

        // todo: shouldn't depend on LineFeeds (had to add '\r')
        final String content = dynamicFile.getContent();
        final String EXPECTED = "Automatically injected:\r\n"
                + String.format("+ %s;\r\n", gwtVersion) // not testing the VersionUtils method
                + String.format("+ %s;\r\n", corePackage)
                + String.format("+ %s;\r\n", mainClass)
                + String.format("+ %s;\r\n", assetsFolderName)
                + String.format("+ %s;\r\n", targetAndroidApi)
                + String.format("+ %s;\r\n", javaVersion)
                + String.format("+ %s;\r\n", desktopJavaVersion)
                + String.format("+ %s;\r\n", serverJavaVersion)
                + String.format("+ %s;\r\n", androidSdkPath)
                + String.format("+ %s;\r\n", appVersion)
                + String.format("+ %s;\r\n", projectName)
                + String.format("+ duplicated %s;\r\n", gwtVersion)
                + "\r\n"
                + "Shouldn't be injected:\r\n"
                + "- ${project};\r\n"
                + "- ${Name};\r\n"
                + "- ${app.Version};\r\n"
                + "- ${gwt Version};\r\n"
                + "- ${corePackage };\r\n"
                + "- ${ corePackage};\r\n"
                + "- ${core Package};\r\n"
                + "- ${javaVersionjavaVersion};\r\n"
                + "- ${java}${version};\r\n"
                + "\r\n"
                + "Manually:\r\n"
                + String.format("- %s;\r\n", replacement)
                + String.format("- %s;", replacement);
        assertEquals(EXPECTED, content);
    }
}
