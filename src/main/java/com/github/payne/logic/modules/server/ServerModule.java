package com.github.payne.logic.modules.server;

import com.github.payne.generator.annotations.NotImplemented;
import com.github.payne.generator.input.GeneratorConfigs;
import com.github.payne.generator.output.vfs.AppendableTree;
import com.github.payne.logic.files.abstracts.BuildGradleFile;
import com.github.payne.logic.modules.GdxModule;

@NotImplemented
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
    }
}
