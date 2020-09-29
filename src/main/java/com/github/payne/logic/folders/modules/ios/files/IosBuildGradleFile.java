package com.github.payne.logic.folders.modules.ios.files;

import com.github.payne.generator.input.GeneratorConfigs;
import com.github.payne.generator.input.model.GdxThirdParty.State;
import com.github.payne.logic.folders.root.BuildGradleFile;

public class IosBuildGradleFile extends BuildGradleFile {

    public IosBuildGradleFile(final GeneratorConfigs input) {
        super("generator/dynamic/modules/ios/build-gradle.txt", input);
    }

    @Override
    protected void assignKeys() {
        addThirdPartiesToModule(dependencies, State::getIOSDependencies,
                "dependencies", "implementation");
    }
}
