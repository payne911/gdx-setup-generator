package com.github.payne.logic.modules.server.files;

import com.github.payne.generator.input.GeneratorConfigs;
import com.github.payne.logic.root.BuildGradleFile;

public class ServerBuildGradleFile extends BuildGradleFile {

    public ServerBuildGradleFile(final GeneratorConfigs input) {
        super("generator/dynamic/modules/server/build-gradle.txt", input);
    }

    @Override
    protected void assignOtherKeys() {
        addSharedProjectDependency("implementation");

        assignKey("dependencies", joinDependencies(dependencies));
    }
}
