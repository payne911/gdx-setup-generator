package com.github.payne.logic.modules.lwjgl3;

import com.github.payne.generator.input.GeneratorConfigs;
import com.github.payne.generator.output.vfs.AppendableTree;
import com.github.payne.logic.modules.GdxModule;
import com.github.payne.logic.modules.lwjgl3.files.Lwjgl3BuildGradleFile;
import com.github.payne.logic.root.BuildGradleFile;
import com.github.payne.logic.templates.GdxTemplate;

public class Lwjgl3Module extends GdxModule {

    public Lwjgl3Module(String folderName) {
        super(folderName);
    }

    @Override
    protected BuildGradleFile getBuildGradleFile(GeneratorConfigs input) {
        return new Lwjgl3BuildGradleFile(input);
    }

    @Override
    protected void customize(GeneratorConfigs input, AppendableTree vfs) {
        copyIcons(vfs);
    }

    @Override
    protected void applyTemplate(GeneratorConfigs input, AppendableTree vfs, GdxTemplate template) {
        template.addLwjgl3Launcher(input, vfs, corePackage, folderName);
    }
}
