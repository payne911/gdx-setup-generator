package com.github.payne.logic.modules.headless.files;

import com.github.payne.generator.input.GeneratorConfigs;
import com.github.payne.logic.root.BuildGradleFile;

public class HeadlessBuildGradleFile extends BuildGradleFile {

    public HeadlessBuildGradleFile(final GeneratorConfigs input) {
        super("generator/dynamic/modules/headless/build-gradle.txt", input);
    }

    @Override
    protected void assignOtherKeys() {
        // todo: "dependencies"
//        assignKey("dependencies", joinDependencies(dependencies, "api"));
    }
}
