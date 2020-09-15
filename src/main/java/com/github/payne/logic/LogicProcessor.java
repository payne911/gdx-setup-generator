package com.github.payne.logic;

import com.github.payne.generator.annotations.NotImplemented;
import com.github.payne.generator.annotations.NotTested;
import com.github.payne.generator.input.GeneratorConfigs;
import com.github.payne.generator.output.GeneratedProject;
import com.github.payne.generator.output.vfs.FileNode;
import com.github.payne.generator.output.vfs.SavableVfs;

@NotTested
@NotImplemented
public class LogicProcessor {

    private final GeneratorConfigs input;
    private final GeneratedProject project; // used to inject Error Messages, if any
    private final SavableVfs vfs;
    private final FileNode root;

    public LogicProcessor(GeneratorConfigs input, GeneratedProject project) {
        this.input = input;
        this.project = project;
        this.vfs = project.getVirtualFileSystem();
        this.root = vfs.getRoot();
    }

    public void applyInputs() {
        addBasicFiles();
        addJvmLanguagesSupport();
        addExtensions();
        applyTemplate();
        addPlatforms();
        addSkinAssets();
        addReadmeFile();
        saveProperties();
        saveFiles();
    }

    public void addBasicFiles() {
    }

    public void addJvmLanguagesSupport() {
    }

    public void addExtensions() {
    }

    public void applyTemplate() {
    }

    public void addPlatforms() {
    }

    public void addSkinAssets() {
    }

    public void addReadmeFile() {
    }

    public void saveProperties() {
    }

    public void saveFiles() {
    }
}
