package com.github.payne.logic.folders.modules.ios.files;

import com.github.payne.generator.input.GeneratorConfigs;
import com.github.payne.logic.folders.DynamicFile;

public class IosRobovmPropertiesFile extends DynamicFile {

    public IosRobovmPropertiesFile(final GeneratorConfigs input) {
        super("robovm.properties", "generator/dynamic/modules/ios/robovm-properties.txt", input);
    }

    @Override
    protected void assignKeys() {
        // Nothing to do
    }
}
