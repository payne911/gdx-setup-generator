package com.github.payne.logic.files.modules.headless;

import com.github.payne.generator.input.GeneratorConfigs;
import com.github.payne.generator.output.vfs.AppendableTree;
import com.github.payne.logic.files.modules.GdxModule;
import com.github.payne.logic.files.modules.headless.files.HeadlessBuildGradleFile;
import com.github.payne.logic.files.root.BuildGradleFile;

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
}
