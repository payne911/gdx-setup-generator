package com.github.payne.logic.folders.modules.lwjgl3.files;

import com.github.payne.generator.input.GeneratorConfigs;
import com.github.payne.generator.input.model.GdxThirdParty.State;
import com.github.payne.logic.folders.root.BuildGradleFile;

public class Lwjgl3BuildGradleFile extends BuildGradleFile {

    public Lwjgl3BuildGradleFile(final GeneratorConfigs input) {
        super("generator/dynamic/modules/lwjgl3/build-gradle.txt", input);
    }

    @Override
    protected void assignKeys() {
        addThirdPartiesToModule(dependencies, State::getDesktopDependencies,
                "dependencies", "implementation");
    }
}
