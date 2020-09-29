package com.github.payne.logic.folders.modules.html.files;

import com.github.payne.generator.input.GeneratorConfigs;
import com.github.payne.generator.input.model.GdxThirdParty.State;
import com.github.payne.generator.input.model.enums.Platform;
import com.github.payne.logic.folders.root.BuildGradleFile;

public class HtmlBuildGradleFile extends BuildGradleFile {

    public HtmlBuildGradleFile(final GeneratorConfigs input) {
        super("generator/dynamic/modules/html/build-gradle.txt", input);
    }

    @Override
    protected void assignKeys() {
        sharedSourceSets();

        addThirdPartiesToModule(dependencies, State::getGwtDependencies,
                "dependencies", "implementation");
        // todo: gwt inherits?
    }

    private void sharedSourceSets() {
        final String replacement = input.contains(Platform.SHARED)
                ? "sourceSets.main.compileClasspath += files(project(':shared').sourceSets.main.allJava.srcDirs)"
                : "";
        assignKey("sharedSourceSets", replacement);
    }
}
