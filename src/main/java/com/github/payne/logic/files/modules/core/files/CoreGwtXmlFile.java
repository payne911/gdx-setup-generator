package com.github.payne.logic.files.modules.core.files;

import com.github.payne.generator.input.GeneratorConfigs;
import com.github.payne.logic.files.root.DynamicFile;

public class CoreGwtXmlFile extends DynamicFile {

    public CoreGwtXmlFile(GeneratorConfigs input) {
        super(input.getMainClass() + ".gwt.xml", "generator/dynamic/modules/core/gwt.txt", input);
    }

    @Override
    protected void assignOtherKeys() {
        // todo: "reflectedClasses" key
    }
}
