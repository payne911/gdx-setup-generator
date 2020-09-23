package com.github.payne.logic.modules.core;

import com.github.payne.generator.input.GeneratorConfigs;
import com.github.payne.logic.files.abstracts.BuildGradleFile;

public class CoreBuildGradleFile extends BuildGradleFile {

    public CoreBuildGradleFile(final GeneratorConfigs input) {
        super("generator/dynamic/modules/core/build-gradle.txt", input);
    }

    @Override
    protected void assignKeys() {
        addDependency("com.badlogicgames.gdx:gdx:\\$gdxVersion");
        // todo: add JVM Languages
        // todo: add Third-Parties

        assignKey("dependencies", joinDependencies(dependencies, "api"));
    }
}
