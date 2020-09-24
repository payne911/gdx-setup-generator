package com.github.payne.logic.modules.html.files;

import com.github.payne.generator.input.GeneratorConfigs;
import com.github.payne.logic.root.BuildGradleFile;

public class HtmlBuildGradleFile extends BuildGradleFile {

    public HtmlBuildGradleFile(final GeneratorConfigs input) {
        super("generator/dynamic/modules/html/build-gradle.txt", input);
    }

    @Override
    protected void assignOtherKeys() {
        // todo: add Third-Parties
//        assignKey("dependencies", joinDependencies(dependencies, "api"));
    }
}
