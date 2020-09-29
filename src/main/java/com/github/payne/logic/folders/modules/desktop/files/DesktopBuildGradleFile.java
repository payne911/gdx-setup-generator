package com.github.payne.logic.folders.modules.desktop.files;

import com.github.payne.generator.input.GeneratorConfigs;
import com.github.payne.generator.input.model.GdxThirdParty.State;
import com.github.payne.logic.folders.root.BuildGradleFile;

public class DesktopBuildGradleFile extends BuildGradleFile {

    public DesktopBuildGradleFile(final GeneratorConfigs input) {
        super("generator/dynamic/modules/desktop/build-gradle.txt", input);
    }

    @Override
    protected void assignKeys() {
        addThirdPartiesToModule(dependencies, State::getDesktopDependencies,
                "dependencies", "implementation");
    }
}
