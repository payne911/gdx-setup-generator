package com.github.payne.logic.modules.server;

import com.github.payne.generator.input.GeneratorConfigs;
import com.github.payne.logic.files.abstracts.BuildGradleFile;

public class ServerBuildGradleFile extends BuildGradleFile {

    public ServerBuildGradleFile(final GeneratorConfigs input) {
        super("generator/dynamic/modules/server/build-gradle.txt", input);
    }

    @Override
    protected void assignKeys() {
        addSharedProjectDependency();

        assignKey("dependencies", joinDependencies(dependencies, "api"));
    }
}
