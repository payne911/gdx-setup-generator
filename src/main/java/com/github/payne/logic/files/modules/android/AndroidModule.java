package com.github.payne.logic.files.modules.android;

import com.github.payne.generator.input.GeneratorConfigs;
import com.github.payne.generator.output.vfs.AppendableTree;
import com.github.payne.logic.files.modules.GdxModule;
import com.github.payne.logic.files.modules.android.files.AndroidBuildGradleFile;
import com.github.payne.logic.files.root.BuildGradleFile;

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
