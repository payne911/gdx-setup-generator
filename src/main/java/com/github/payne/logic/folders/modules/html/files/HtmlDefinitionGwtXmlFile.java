package com.github.payne.logic.folders.modules.html.files;

import com.github.payne.generator.input.GeneratorConfigs;
import com.github.payne.generator.input.model.enums.Platform;
import com.github.payne.logic.folders.DynamicFile;

public class HtmlDefinitionGwtXmlFile extends DynamicFile {

    public HtmlDefinitionGwtXmlFile(GeneratorConfigs input) {
        super("GdxDefinition.gwt.xml", "generator/dynamic/modules/html/gwt-definition.txt", input);
    }

    @Override
    protected void assignKeys() {
        // todo: if support 3rd parties, "sharedInherits" needs to be updated with pattern:
        // gwtInherits.joinToString(separator = "\n") { "\t<inherits name=\"$it\" />" }}
        assignKey("sharedInherits", input.contains(Platform.SHARED)
                ? "<inherits name=\"" + input.getCorePackage() + ".Shared\" />"
                : "");
    }
}
