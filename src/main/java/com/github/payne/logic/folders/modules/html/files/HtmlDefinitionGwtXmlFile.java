package com.github.payne.logic.folders.modules.html.files;

import com.github.payne.generator.input.GeneratorConfigs;
import com.github.payne.logic.folders.DynamicFile;

public class HtmlDefinitionGwtXmlFile extends DynamicFile {

    public HtmlDefinitionGwtXmlFile(GeneratorConfigs input) {
        super("GdxDefinition.gwt.xml", "generator/dynamic/modules/html/gwt-definition.txt", input);
    }

    @Override
    protected void assignKeys() {
        // todo: "${sortedInherits}"
    }
}
