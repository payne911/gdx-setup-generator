package com.github.payne.logic.files.modules.html.files;

import com.github.payne.generator.input.GeneratorConfigs;
import com.github.payne.logic.files.root.DynamicFile;

public class HtmlDefinitionGwtXmlFile extends DynamicFile {

    public HtmlDefinitionGwtXmlFile(GeneratorConfigs input) {
        super("GdxDefinition.gwt.xml", "generator/dynamic/modules/html/gwt-definition.txt", input);
    }

    @Override
    protected void assignOtherKeys() {
        // todo: "sortedInherits" key
    }
}
