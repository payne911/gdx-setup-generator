package com.github.payne.logic.modules.shared.files;

import com.github.payne.generator.input.GeneratorConfigs;
import com.github.payne.logic.root.BuildGradleFile;

public class SharedBuildGradleFile extends BuildGradleFile {

    public SharedBuildGradleFile(final GeneratorConfigs input) {
        super("generator/dynamic/modules/shared/build-gradle.txt", input);
    }

    @Override
    protected void assignOtherKeys() {
        addJvmLanguagesDependencies();

        assignKey("dependencies", joinDependencies(dependencies));
    }
}
