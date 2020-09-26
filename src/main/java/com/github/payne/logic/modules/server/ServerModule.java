package com.github.payne.logic.modules.server;

import com.github.payne.generator.input.GeneratorConfigs;
import com.github.payne.generator.output.vfs.AppendableTree;
import com.github.payne.logic.modules.GdxModule;
import com.github.payne.logic.modules.server.files.ServerBuildGradleFile;
import com.github.payne.logic.root.BuildGradleFile;
import com.github.payne.logic.templates.GdxTemplate;

public class ServerModule extends GdxModule {

    public ServerModule(String folderName) {
        super(folderName);
    }

    @Override
    protected BuildGradleFile getBuildGradleFile(GeneratorConfigs input) {
        return new ServerBuildGradleFile(input);
    }

    @Override
    protected void customize(GeneratorConfigs input, AppendableTree vfs) {
        // Nothing to do
    }

    @Override
    protected void applyTemplate(GeneratorConfigs input, AppendableTree vfs, GdxTemplate template) {
        template.addServerLauncher(input, vfs, corePackage, folderName);
    }
}
