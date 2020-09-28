package com.github.payne.logic.modules.ios.files;

import com.github.payne.generator.input.GeneratorConfigs;
import com.github.payne.logic.root.BuildGradleFile;

public class IosBuildGradleFile extends BuildGradleFile {

    public IosBuildGradleFile(final GeneratorConfigs input) {
        super("generator/dynamic/modules/ios/build-gradle.txt", input);
    }

    @Override
    protected void assignKeys() {
        // todo: "dependencies"
//        assignKey("dependencies", joinDependencies(dependencies, "api"));
    }
}
