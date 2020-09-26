package com.github.payne.logic.modules.desktop;

import com.github.payne.generator.input.GeneratorConfigs;
import com.github.payne.generator.output.vfs.AppendableTree;
import com.github.payne.logic.modules.GdxModule;
import com.github.payne.logic.modules.desktop.files.DesktopBuildGradleFile;
import com.github.payne.logic.root.BuildGradleFile;
import com.github.payne.logic.templates.GdxTemplate;

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
