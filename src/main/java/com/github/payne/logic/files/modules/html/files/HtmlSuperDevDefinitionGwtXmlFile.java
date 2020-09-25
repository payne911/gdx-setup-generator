package com.github.payne.logic.files.modules.html.files;

import com.github.payne.generator.input.GeneratorConfigs;
import com.github.payne.logic.files.root.DynamicFile;

public class HtmlSuperDevDefinitionGwtXmlFile extends DynamicFile {

    public HtmlSuperDevDefinitionGwtXmlFile(GeneratorConfigs input) {
        super("GdxDefinitionSuperdev.gwt.xml",
                "generator/dynamic/modules/html/gwt-definition-superDev.txt",
                input);
    }

    @Override
    protected void assignOtherKeys() {
        // nothing to do
    }
}
