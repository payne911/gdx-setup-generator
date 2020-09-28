package com.github.payne.logic.folders.modules.shared;

import com.github.payne.generator.input.GeneratorConfigs;
import com.github.payne.generator.input.model.enums.Platform;
import com.github.payne.generator.output.vfs.AppendableTree;
import com.github.payne.logic.folders.DynamicFile;
import com.github.payne.logic.folders.modules.GdxModule;
import com.github.payne.logic.folders.modules.shared.files.SharedBuildGradleFile;
import com.github.payne.logic.folders.modules.shared.files.SharedGwtXmlFile;
import com.github.payne.logic.folders.root.BuildGradleFile;
import com.github.payne.logic.templates.base.GdxTemplate;

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
            corePackage.addChild(gwtXmlFile.createFile());
        }
    }

    @Override
    protected void applyTemplate(GeneratorConfigs input, AppendableTree vfs, GdxTemplate template) {
        // no templates
    }
}
