package com.github.payne.logic.folders.modules.core.files;

import com.github.payne.generator.input.GeneratorConfigs;
import com.github.payne.logic.folders.DynamicFile;

public class CoreGwtXmlFile extends DynamicFile {

    public CoreGwtXmlFile(GeneratorConfigs input) {
        super(input.getMainClass() + ".gwt.xml",
                "generator/dynamic/modules/core/gwt-definition.txt", input);
    }

    @Override
    protected void assignKeys() {
        // todo: "reflectedClasses" key
    }
}
