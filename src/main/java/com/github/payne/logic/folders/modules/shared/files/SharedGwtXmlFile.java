package com.github.payne.logic.folders.modules.shared.files;

import com.github.payne.generator.input.GeneratorConfigs;
import com.github.payne.logic.folders.DynamicFile;

public class SharedGwtXmlFile extends DynamicFile {

    public SharedGwtXmlFile(GeneratorConfigs input) {
        super("Shared.gwt.xml", "generator/dynamic/modules/shared/gwt-definition.txt", input);
    }

    @Override
    protected void assignKeys() {
        // nothing to do here
    }
}
