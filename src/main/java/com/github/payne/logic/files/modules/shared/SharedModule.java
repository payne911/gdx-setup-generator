package com.github.payne.logic.files.modules.shared;

import com.github.payne.generator.annotations.NotImplemented;
import com.github.payne.generator.input.GeneratorConfigs;
import com.github.payne.generator.input.model.enums.Platform;
import com.github.payne.generator.output.vfs.AppendableTree;
import com.github.payne.logic.files.modules.GdxModule;
import com.github.payne.logic.files.modules.shared.files.SharedBuildGradleFile;
import com.github.payne.logic.files.modules.shared.files.SharedGwtXmlFile;
import com.github.payne.logic.files.root.BuildGradleFile;
import com.github.payne.logic.files.root.DynamicFile;

@NotImplemented
public class SharedModule extends GdxModule {

    public SharedModule(String folderName) {
        super(folderName);
    }

    @Override
    protected BuildGradleFile getBuildGradleFile(GeneratorConfigs input) {
        return new SharedBuildGradleFile(input);
    }

    @Override
    protected void customize(GeneratorConfigs input, AppendableTree vfs) {
        if (input.contains(Platform.HTML)) {
            DynamicFile gwtXmlFile = new SharedGwtXmlFile(input);
            inputPackage.addChild(gwtXmlFile.createFile());
        }
    }
}
