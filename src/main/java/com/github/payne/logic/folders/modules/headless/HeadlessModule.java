package com.github.payne.logic.folders.modules.headless;

import com.github.payne.generator.input.GeneratorConfigs;
import com.github.payne.generator.output.vfs.AppendableTree;
import com.github.payne.logic.folders.modules.GdxModule;
import com.github.payne.logic.folders.modules.headless.files.HeadlessBuildGradleFile;
import com.github.payne.logic.folders.root.BuildGradleFile;
import com.github.payne.logic.templates.GdxTemplate;

public class HeadlessModule extends GdxModule {

    public HeadlessModule(String folderName) {
        super(folderName);
    }

    @Override
    protected BuildGradleFile getBuildGradleFile(GeneratorConfigs input) {
        return new HeadlessBuildGradleFile(input);
    }

    @Override
    protected void customize(GeneratorConfigs input, AppendableTree vfs) {
        // Nothing to do
    }

    @Override
    protected void applyTemplate(GeneratorConfigs input, AppendableTree vfs, GdxTemplate template) {
        template.addHeadlessLauncher(input, vfs, corePackage, folderName);
    }
}
