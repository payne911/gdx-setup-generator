package com.github.payne.logic.files.modules.core;

import com.github.payne.generator.input.GeneratorConfigs;
import com.github.payne.generator.input.model.enums.Platform;
import com.github.payne.generator.output.vfs.AppendableTree;
import com.github.payne.generator.output.vfs.FileNode;
import com.github.payne.logic.files.modules.GdxModule;
import com.github.payne.logic.files.modules.core.files.CoreBuildGradleFile;
import com.github.payne.logic.files.modules.core.files.CoreGwtXmlFile;
import com.github.payne.logic.files.root.BuildGradleFile;
import com.github.payne.logic.files.root.DynamicFile;

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
        if (input.contains(Platform.HTML)) {
            DynamicFile gwtXmlFile = new CoreGwtXmlFile(input);
            inputPackage.addChild(gwtXmlFile.createFile());
        }
        // todo: replace below File with Template stuff!
        inputPackage.addChild(new FileNode(input.getMainClass(), "class foo{ psvm() }".getBytes()));
    }
}
