package com.github.payne.logic.modules.shared;

import com.github.payne.generator.annotations.NotImplemented;
import com.github.payne.generator.input.GeneratorConfigs;
import com.github.payne.generator.input.model.enums.Platform;
import com.github.payne.generator.output.vfs.AppendableTree;
import com.github.payne.generator.output.vfs.FileNode;
import com.github.payne.logic.files.abstracts.BuildGradleFile;
import com.github.payne.logic.modules.GdxModule;
import com.github.payne.utils.FileUtils;

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
            vfs.addToParent(inputPackage, new FileNode("Shared.gwt.xml",
                    FileUtils.readResourceFile("generator/static/shared/shared.gwt.xml.txt")));
        }
    }
}
