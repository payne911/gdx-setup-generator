package com.github.payne.logic.modules.core;

import com.github.payne.generator.annotations.NotImplemented;
import com.github.payne.generator.input.GeneratorConfigs;
import com.github.payne.generator.output.vfs.AppendableTree;
import com.github.payne.generator.output.vfs.FileNode;
import com.github.payne.logic.files.abstracts.BuildGradleFile;
import com.github.payne.logic.modules.GdxModule;

@NotImplemented
public class CoreModule extends GdxModule {

    public CoreModule(String folderName) {
        super(folderName);
    }

    @Override
    protected BuildGradleFile getBuildGradleFile(GeneratorConfigs input) {
        return new CoreBuildGradleFile(input);
    }

    @Override
    protected void customize(GeneratorConfigs input, AppendableTree vfs) {
        inputPackage.addChild(new FileNode(input.getMainClass(), "class foo{ psvm() }".getBytes()));
    }
}
