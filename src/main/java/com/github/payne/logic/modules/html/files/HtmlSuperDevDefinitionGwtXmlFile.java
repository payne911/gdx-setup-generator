package com.github.payne.logic.modules.html.files;

import com.github.payne.generator.input.GeneratorConfigs;
import com.github.payne.logic.root.DynamicFile;

public class HtmlSuperDevDefinitionGwtXmlFile extends DynamicFile {

    public HtmlSuperDevDefinitionGwtXmlFile(GeneratorConfigs input) {
        super("Shared.gwt.xml", "generator/dynamic/modules/html/gwt-definition-superDev.txt",
                input);
    }

    @Override
    protected void assignOtherKeys() {
        // nothing to do
    }
}
