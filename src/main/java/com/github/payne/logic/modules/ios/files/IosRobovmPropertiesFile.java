package com.github.payne.logic.modules.ios.files;

import com.github.payne.generator.input.GeneratorConfigs;
import com.github.payne.logic.root.DynamicFile;

public class IosRobovmPropertiesFile extends DynamicFile {

    public IosRobovmPropertiesFile(final GeneratorConfigs input) {
        super("robovm.properties", "generator/dynamic/modules/ios/robovm-properties.txt", input);
    }

    @Override
    protected void assignOtherKeys() {
        // Nothing to do
    }
}
