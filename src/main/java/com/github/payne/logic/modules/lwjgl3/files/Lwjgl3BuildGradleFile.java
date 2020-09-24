package com.github.payne.logic.modules.lwjgl3.files;

import com.github.payne.generator.input.GeneratorConfigs;
import com.github.payne.logic.root.BuildGradleFile;

public class Lwjgl3BuildGradleFile extends BuildGradleFile {

    public Lwjgl3BuildGradleFile(final GeneratorConfigs input) {
        super("generator/dynamic/modules/lwjgl3/build-gradle.txt", input);
    }

    @Override
    protected void assignOtherKeys() {
        // todo: "dependencies"
//        assignKey("dependencies", joinDependencies(dependencies, "api"));
    }
}
