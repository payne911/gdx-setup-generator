package com.github.payne.logic;

import com.github.payne.generator.input.GeneratorConfigs;
import com.github.payne.generator.input.model.enums.AddOn;
import com.github.payne.generator.input.model.enums.Platform;
import com.github.payne.generator.output.GeneratedProject;
import com.github.payne.generator.output.vfs.AppendableTree;
import com.github.payne.generator.output.vfs.FileNode;
import com.github.payne.logic.folders.DynamicFile;
import com.github.payne.logic.folders.modules.GdxModule;
import com.github.payne.logic.folders.root.files.GradlePropertiesFile;
import com.github.payne.logic.folders.root.files.LocalPropertiesFile;
import com.github.payne.logic.folders.root.files.ReadMeFile;
import com.github.payne.logic.folders.root.files.RootBuildGradleFile;
import com.github.payne.logic.folders.root.files.SettingsGradleFile;
import com.github.payne.utils.EnumMapper;
import com.github.payne.utils.FileUtils;
import com.github.payne.utils.annotations.NotTested;
import java.util.Arrays;

@NotTested
public class LogicProcessor {

    private final GeneratorConfigs input;
    private final GeneratedProject project; // used to inject Error Messages, if any
    private final AppendableTree vfs;
    private final FileNode root;

    public LogicProcessor(GeneratorConfigs input, GeneratedProject project) {
        this.input = input;
        this.project = project;
        this.vfs = project.getVirtualFileSystem();
        this.root = vfs.getRoot();
        FileUtils.clearCache();
    }

    public void applyInputs() {
        addRootFiles();
        addRootFolders();
    }

    public void addRootFiles() {
        addGitIgnore();
        addRootBuildGradle();
        addReadmeFile();
        addGradleProperties();
        addLocalProperties();
        addSettingsGradle();
    }

    public void addRootFolders() {
        addAssets();
        addGradleWrapper();
        addPlatforms();
    }

    public void addPlatforms() {
        for (Platform platform : input.getPlatforms()) {
            GdxModule module = EnumMapper.getModule(platform);
            module.generate(input, vfs);
        }
    }

    public void addGitIgnore() {
        vfs.copyFileToRoot("generator/static/gitignore", ".gitignore");
    }

    public void addRootBuildGradle() {
        DynamicFile rootBuildGradle = new RootBuildGradleFile(input);
        vfs.addToRoot(rootBuildGradle.createFile());
    }

    public void addGradleProperties() {
        DynamicFile gradleProperties = new GradlePropertiesFile(input);
        vfs.addToRoot(gradleProperties.createFile());
    }

    public void addLocalProperties() {
        if (input.contains(Platform.ANDROID)) {
            DynamicFile localProperties = new LocalPropertiesFile(input);
            vfs.addToRoot(localProperties.createFile());
        }
    }

    public void addAssets() {
        final String DEST_FOLDER = input.getAssetsFolderName();
        vfs.addToRoot(new FileNode(DEST_FOLDER));
        if (input.contains(AddOn.GUI_ASSETS)) {
            vfs.copyFolder("generator/static/assets", Arrays.asList(DEST_FOLDER), false);
        }
    }

    private void addGradleWrapper() {
        if (input.contains(AddOn.GRADLE_WRAPPER)) {
            vfs.copyFolderToRoot("generator/static/gradle", true);
            vfs.copyFolderToRoot("generator/static/gradlew", false);
        }
    }

    public void addReadmeFile() {
        if (input.contains(AddOn.README)) {
            DynamicFile readme = new ReadMeFile(input);
            vfs.addToRoot(readme.createFile());
        }
    }

    private void addSettingsGradle() {
        DynamicFile modules = new SettingsGradleFile(input);
        vfs.addToRoot(modules.createFile());
    }
}
