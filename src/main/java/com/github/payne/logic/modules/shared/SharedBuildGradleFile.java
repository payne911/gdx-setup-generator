package com.github.payne.logic.modules.shared;

import com.github.payne.generator.input.GeneratorConfigs;
import com.github.payne.logic.files.abstracts.BuildGradleFile;

public class SharedBuildGradleFile extends BuildGradleFile {

    public SharedBuildGradleFile(final GeneratorConfigs input) {
        super("generator/dynamic/modules/shared/build-gradle.txt", input);
    }

    @Override
    protected void assignKeys() {
        addJvmLanguagesDependencies();

        assignKey("dependencies", joinDependencies(dependencies));
    }
}
