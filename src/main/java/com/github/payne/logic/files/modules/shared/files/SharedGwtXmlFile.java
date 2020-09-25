package com.github.payne.logic.files.modules.shared.files;

import com.github.payne.generator.input.GeneratorConfigs;
import com.github.payne.logic.files.root.DynamicFile;

public class SharedGwtXmlFile extends DynamicFile {

    public SharedGwtXmlFile(GeneratorConfigs input) {
        super("Shared.gwt.xml", "generator/dynamic/modules/shared/gwt.txt", input);
    }

    @Override
    protected void assignOtherKeys() {
        // nothing to do here
    }
}
