package com.github.payne.logic.folders.modules.core;

import com.github.payne.generator.input.GeneratorConfigs;
import com.github.payne.generator.input.model.enums.Platform;
import com.github.payne.generator.output.vfs.AppendableTree;
import com.github.payne.logic.folders.DynamicFile;
import com.github.payne.logic.folders.modules.GdxModule;
import com.github.payne.logic.folders.modules.core.files.CoreBuildGradleFile;
import com.github.payne.logic.folders.modules.core.files.CoreGwtXmlFile;
import com.github.payne.logic.folders.root.BuildGradleFile;
import com.github.payne.logic.templates.GdxTemplate;

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
            corePackage.addChild(gwtXmlFile.createFile());
        }
    }

    @Override
    protected void applyTemplate(GeneratorConfigs input, AppendableTree vfs, GdxTemplate template) {
        template.addApplicationListener(input, vfs, corePackage);
    }
}
