package com.github.payne.logic.modules.ios.files;

import com.github.payne.generator.input.GeneratorConfigs;
import com.github.payne.logic.root.BuildGradleFile;

public class IosRobovmXmlFile extends BuildGradleFile {

    public IosRobovmXmlFile(final GeneratorConfigs input) {
        super("generator/dynamic/modules/ios/robovm-xml.txt", input);
    }

    @Override
    protected void assignOtherKeys() {
        // Nothing to do
    }
}
