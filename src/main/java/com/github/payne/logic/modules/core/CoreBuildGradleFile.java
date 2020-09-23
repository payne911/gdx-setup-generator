package com.github.payne.logic.modules.core;

import com.github.payne.generator.input.GeneratorConfigs;
import com.github.payne.logic.files.abstracts.BuildGradleFile;

public class CoreBuildGradleFile extends BuildGradleFile {

    public CoreBuildGradleFile(final GeneratorConfigs input) {
        super("generator/dynamic/modules/core/build-gradle.txt", input);
    }

    @Override
    protected void assignKeys() {
        addSharedProjectDependency();
        gdx();
        addJvmLanguagesDependencies();

        // todo: add Third-Parties

        assignKey("dependencies", joinDependencies(dependencies, "api"));
    }

    private void gdx() {
        addDependency("com.badlogicgames.gdx:gdx:\\$gdxVersion");
    }
}
