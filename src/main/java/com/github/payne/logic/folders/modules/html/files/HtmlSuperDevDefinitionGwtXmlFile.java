package com.github.payne.logic.folders.modules.html.files;

import com.github.payne.generator.input.GeneratorConfigs;
import com.github.payne.logic.folders.DynamicFile;

public class HtmlSuperDevDefinitionGwtXmlFile extends DynamicFile {

    public HtmlSuperDevDefinitionGwtXmlFile(GeneratorConfigs input) {
        super("GdxDefinitionSuperdev.gwt.xml",
                "generator/dynamic/modules/html/gwt-definition-superDev.txt",
                input);
    }

    @Override
    protected void assignKeys() {
        // nothing to do
    }
}
