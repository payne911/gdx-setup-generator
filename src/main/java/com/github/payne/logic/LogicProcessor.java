package com.github.payne.logic;

import com.github.payne.generator.annotations.NotImplemented;
import com.github.payne.generator.annotations.NotTested;
import com.github.payne.generator.input.GeneratorConfigs;
import com.github.payne.generator.input.model.enums.AddOn;
import com.github.payne.generator.input.model.enums.Platform;
import com.github.payne.generator.output.GeneratedProject;
import com.github.payne.generator.output.vfs.FileNode;
import com.github.payne.generator.output.vfs.SavableVfs;
import java.util.Arrays;

@NotTested
@NotImplemented
public class LogicProcessor {

    private final GeneratorConfigs input;
    private final GeneratedProject project; // used to inject Error Messages, if any
    private final SavableVfs vfs;
    private final FileNode root;

    public static final String ASSETS_FOLDER = "assets";

    public LogicProcessor(GeneratorConfigs input, GeneratedProject project) {
        this.input = input;
        this.project = project;
        this.vfs = project.getVirtualFileSystem();
        this.root = vfs.getRoot();
    }

    public void applyInputs() {
        addBasicFiles();
        addSkinAssets();
        addLibraries();
        applyTemplate();
        input.getPlatforms().forEach(this::addPlatform);
        addJvmLanguagesSupport();
        addReadmeFile();
        saveProperties();
        saveFiles();
    }

    public void addBasicFiles() {
        vfs.copyFileToRoot(Arrays.asList("generator", "static", "gitignore"), ".gitignore");
        root.addChild(new FileNode(ASSETS_FOLDER)); // there's always the main assets folder
    }

    public void addSkinAssets() {
        if (input.contains(AddOn.GUI_ASSETS)) {
            vfs.copyFolder(Arrays.asList("generator", "static", "assets"),
                    Arrays.asList(ASSETS_FOLDER), false);
        }
    }

    public void addLibraries() {
    }

    public void applyTemplate() {
    }

    public void addPlatform(Platform platform) {
        System.out.println("Applying " + platform);
        System.out.println("Applying " + platform.ordinal());
        System.out.println("Applying " + platform.name());
        System.out.println("Applying " + platform.getValue());
        System.out.println("Applying " + platform.toString());
        System.out.println("=============================");
    }

    public void addJvmLanguagesSupport() {
    }

    public void addReadmeFile() {
    }

    public void saveProperties() {
    }

    public void saveFiles() {
    }
}
