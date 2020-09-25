package com.github.payne.logic.files.modules.desktop;

import com.github.payne.generator.input.GeneratorConfigs;
import com.github.payne.generator.output.vfs.AppendableTree;
import com.github.payne.logic.files.modules.GdxModule;
import com.github.payne.logic.files.modules.desktop.files.DesktopBuildGradleFile;
import com.github.payne.logic.files.root.BuildGradleFile;

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
        // todo: TEMPLATE
        copyIcons(vfs);
    }
}
