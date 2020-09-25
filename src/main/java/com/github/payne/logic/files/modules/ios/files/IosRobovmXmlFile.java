package com.github.payne.logic.files.modules.ios.files;

import com.github.payne.generator.input.GeneratorConfigs;
import com.github.payne.logic.files.root.DynamicFile;

public class IosRobovmXmlFile extends DynamicFile {

    public IosRobovmXmlFile(final GeneratorConfigs input) {
        super("robovm.xml", "generator/dynamic/modules/ios/robovm-xml.txt", input);
    }

    @Override
    protected void assignOtherKeys() {
        // Nothing to do
    }
}
