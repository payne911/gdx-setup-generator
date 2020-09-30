package com.github.payne.logic.templates.base;

import com.github.payne.generator.input.GeneratorConfigs;
import com.github.payne.generator.output.vfs.AppendableTree;
import com.github.payne.generator.output.vfs.FileNode;
import com.github.payne.utils.FileUtils;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;

/**
 * Represents an ensemble of Launcher classes.
 * <p>
 * For flexibility purposes, some parameters are provided in the methods even if they aren't used.
 * That allows for overriding the general behavior.
 * <p>
 * For example, if you wanted to add a certain file after the ApplicationListener is created, you
 * can simply override {@link #addApplicationListener(GeneratorConfigs, AppendableTree, FileNode)}
 * and then do your stuff right after a call to {@code super}.
 */
@Data
public abstract class GdxTemplate {

    private final String readmeDescription;

    /* Sizes are kept as strings so you can set the sizes to static values, for example: MainClass.WIDTH. */
    @Getter(AccessLevel.PROTECTED)
    private final String width = "640";
    @Getter(AccessLevel.PROTECTED)
    private final String height = "480";

    protected String getAndroidLauncherContent() {
        return FileUtils.readResourceFileAsString(
                "generator/dynamic/templates/launchers/android.txt");
    }

    public void addAndroidLauncher(GeneratorConfigs input, AppendableTree vfs,
            FileNode corePackage, String moduleName) {
        appendLauncher(corePackage, moduleName, "AndroidLauncher.java",
                getAndroidLauncherContent(), input);
    }

    protected String getDesktopLauncherContent() {
        return FileUtils.readResourceFileAsString(
                "generator/dynamic/templates/launchers/desktop.txt");
    }

    public void addDesktopLauncher(GeneratorConfigs input, AppendableTree vfs,
            FileNode corePackage, String moduleName) {
        appendLauncher(corePackage, moduleName, "DesktopLauncher.java",
                getDesktopLauncherContent(), input);
    }

    protected String getLwjgl3LauncherContent() {
        return FileUtils.readResourceFileAsString(
                "generator/dynamic/templates/launchers/lwjgl3.txt");
    }

    public void addLwjgl3Launcher(GeneratorConfigs input, AppendableTree vfs,
            FileNode corePackage, String moduleName) {
        appendLauncher(corePackage, moduleName, "Lwjgl3Launcher.java",
                getLwjgl3LauncherContent(), input);
    }

    protected String getGwtLauncherContent(boolean isBefore12) {
        final String PATH = isBefore12
                ? "generator/dynamic/templates/launchers/gwt_old.txt"
                : "generator/dynamic/templates/launchers/gwt.txt";
        return FileUtils.readResourceFileAsString(PATH);
    }

    public void addGwtLauncher(GeneratorConfigs input, AppendableTree vfs,
            FileNode corePackage, String moduleName) {
        appendLauncher(corePackage, moduleName, "GwtLauncher.java",
                getGwtLauncherContent(input.getLibGdxVersionObject().isOlderThan("1.9.12")), input);
    }

    protected String getHeadlessLauncherContent() {
        return FileUtils.readResourceFileAsString(
                "generator/dynamic/templates/launchers/headless.txt");
    }

    public void addHeadlessLauncher(GeneratorConfigs input, AppendableTree vfs,
            FileNode corePackage, String moduleName) {
        appendLauncher(corePackage, moduleName, "HeadlessLauncher.java",
                getHeadlessLauncherContent(), input);
    }

    protected String getIosLauncherContent() {
        return FileUtils
                .readResourceFileAsString("generator/dynamic/templates/launchers/ios.txt");
    }

    public void addIosLauncher(GeneratorConfigs input, AppendableTree vfs,
            FileNode corePackage, String moduleName) {
        appendLauncher(corePackage, moduleName, "IOSLauncher.java",
                getIosLauncherContent(), input);
    }

    protected String getServerLauncherContent() {
        return FileUtils.readResourceFileAsString(
                "generator/dynamic/templates/launchers/server.txt");
    }

    public void addServerLauncher(GeneratorConfigs input, AppendableTree vfs,
            FileNode corePackage, String moduleName) {
        appendLauncher(corePackage, moduleName, "ServerLauncher.java",
                getServerLauncherContent(), input);
    }

    protected final void appendLauncher(FileNode corePackage, String folderName,
            String launcherName, String launcherContent, GeneratorConfigs input) {
        FileNode appendedFolder = new FileNode(folderName); // appending the module-name folder
        corePackage.addChild(appendedFolder);

        launcherContent = FileUtils.injectConfigs(launcherContent, input); // replacing all keys
        FileNode file = new FileNode(launcherName, launcherContent.getBytes());
        appendedFolder.addChild(file);
    }

    /**
     * <b>Do not</b> create a file here: this is just to get the content of the file.
     * <p>
     * The created file's name will be based off the value of {@link GeneratorConfigs#getMainClass()}.
     * <p>
     * The content of this file can contain keys. The keys listed in {@link
     * FileUtils#injectConfigs(String, GeneratorConfigs)} will be automatically replaced.
     *
     * @return content of Java class implementing {@code ApplicationListener}.
     */
    protected abstract String getApplicationListenerContent(GeneratorConfigs input,
            AppendableTree vfs);

    public void addApplicationListener(GeneratorConfigs input, AppendableTree vfs,
            FileNode corePackage) {
        String tmpContent = getApplicationListenerContent(input, vfs);
        tmpContent = FileUtils.injectConfigs(tmpContent, input);
        final byte[] content = tmpContent.getBytes();
        FileNode file = new FileNode(input.getMainClass() + ".java", content);
        corePackage.addChild(file);
    }
}
