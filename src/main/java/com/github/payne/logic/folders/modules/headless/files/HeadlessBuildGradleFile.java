package com.github.payne.logic.folders.modules.headless.files;

import com.github.payne.generator.input.GeneratorConfigs;
import com.github.payne.logic.folders.root.BuildGradleFile;

public class HeadlessBuildGradleFile extends BuildGradleFile {

    public HeadlessBuildGradleFile(final GeneratorConfigs input) {
        super("generator/dynamic/modules/headless/build-gradle.txt", input);
    }

    @Override
    protected void assignKeys() {
        // nothing to do
    }
}
