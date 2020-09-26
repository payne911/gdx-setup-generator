package com.github.payne.logic.templates;

import com.github.payne.generator.input.GeneratorConfigs;
import com.github.payne.logic.DynamicFile;

public class LauncherFile extends DynamicFile {

    public LauncherFile(String fileName, String resourcePath, GeneratorConfigs input) {
        super(fileName, resourcePath, input);
    }

    @Override
    protected void assignOtherKeys() {

    }
}
