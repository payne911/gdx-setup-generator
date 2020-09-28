package com.github.payne.logic.folders.modules.android;

import com.github.payne.generator.input.GeneratorConfigs;
import com.github.payne.generator.output.vfs.AppendableTree;
import com.github.payne.logic.folders.modules.GdxModule;
import com.github.payne.logic.folders.modules.android.files.AndroidBuildGradleFile;
import com.github.payne.logic.folders.root.BuildGradleFile;
import com.github.payne.logic.templates.GdxTemplate;

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

    @Override
    protected void applyTemplate(GeneratorConfigs input, AppendableTree vfs, GdxTemplate template) {
        template.addAndroidLauncher(input, vfs, corePackage, folderName);
    }
}
