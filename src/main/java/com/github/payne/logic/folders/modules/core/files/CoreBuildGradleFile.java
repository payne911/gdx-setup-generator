package com.github.payne.logic.folders.modules.core.files;

import com.github.payne.generator.input.GeneratorConfigs;
import com.github.payne.generator.input.model.GdxThirdParty.State;
import com.github.payne.logic.folders.root.BuildGradleFile;

public class CoreBuildGradleFile extends BuildGradleFile {

    public CoreBuildGradleFile(final GeneratorConfigs input) {
        super("generator/dynamic/modules/core/build-gradle.txt", input);
    }

    @Override
    protected void assignKeys() {
        addSharedProjectDependency("api");
        gdx();
        addJvmLanguagesDependencies();
        addThirdPartiesToModule(dependencies, State::getCoreDependencies,
                "dependencies", "api");
        // todo: "${reflectedClasses}"
    }

    private void gdx() {
        addDependency("com.badlogicgames.gdx:gdx:\\$gdxVersion");
    }
}
