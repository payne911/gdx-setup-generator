package com.github.payne.logic.templates;

import com.github.payne.generator.input.GeneratorConfigs;
import com.github.payne.generator.output.vfs.AppendableTree;

public class ClassicTemplate extends GdxTemplate {

    public ClassicTemplate(String readmeDescription) {
        super("Project template includes simple launchers and an `ApplicationAdapter` extension that draws BadLogic logo.");
    }


    @Override
    public String getApplicationListenerContent(GeneratorConfigs input, AppendableTree vfs) {
        return "TEST";
    }
}
