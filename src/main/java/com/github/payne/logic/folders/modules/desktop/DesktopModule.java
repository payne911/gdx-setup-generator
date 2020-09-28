package com.github.payne.logic.folders.modules.desktop;

import com.github.payne.generator.input.GeneratorConfigs;
import com.github.payne.generator.output.vfs.AppendableTree;
import com.github.payne.logic.folders.modules.GdxModule;
import com.github.payne.logic.folders.modules.desktop.files.DesktopBuildGradleFile;
import com.github.payne.logic.folders.root.BuildGradleFile;
import com.github.payne.logic.templates.base.GdxTemplate;

public class DesktopModule extends GdxModule {

    public DesktopModule(String folderName) {
        super(folderName);
    }

    @Override
    protected BuildGradleFile getBuildGradleFile(GeneratorConfigs input) {
        return new DesktopBuildGradleFile(input);
    }

    @Override
    protected void customize(GeneratorConfigs input, AppendableTree vfs) {
        copyIcons(vfs);
    }

    @Override
    protected void applyTemplate(GeneratorConfigs input, AppendableTree vfs, GdxTemplate template) {
        template.addDesktopLauncher(input, vfs, corePackage, folderName);
    }
}
