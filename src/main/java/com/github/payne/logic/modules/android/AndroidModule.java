package com.github.payne.logic.modules.android;

import com.github.payne.generator.input.GeneratorConfigs;
import com.github.payne.generator.output.vfs.AppendableTree;
import com.github.payne.logic.modules.GdxModule;
import com.github.payne.logic.modules.android.files.AndroidBuildGradleFile;
import com.github.payne.logic.root.BuildGradleFile;

public class AndroidModule extends GdxModule {

    public AndroidModule(String folderName) {
        super(folderName);
    }

    @Override
    protected BuildGradleFile getBuildGradleFile(GeneratorConfigs input) {
        return new AndroidBuildGradleFile(input);
    }

    @Override
    protected void customize(GeneratorConfigs input, AppendableTree vfs) {
        // todo
    }
}
